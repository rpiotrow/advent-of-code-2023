package io.github.rpiotrow.advent2023.day16

import io.github.rpiotrow.advent2023.day16.Side.*
import zio.IO
import zio.stream.ZStream

import scala.annotation.tailrec

class Contraption(edges: Map[Node, List[Node]]):
  def mark(start: Node): Set[Node] = mark(List(start), Set.empty)
  def maxX = edges.map { case (node, _) => node.x }.max
  def maxy = edges.map { case (node, _) => node.y }.max
  @tailrec
  private def mark(nodes: List[Node], marked: Set[Node]): Set[Node] =
    nodes match
      case Nil => marked
      case n :: ns =>
        val newNodes = edges.get(n).toList.flatten.filterNot(marked.contains)
        mark(ns ++ newNodes, marked + n)

object Contraption:
  def parse(stream: ZStream[Any, String, String]): IO[String, Contraption] =
    stream.zipWithIndex
      .flatMap { case (line, y) =>
        val innerEdges = ZStream.fromIterable(line).zipWithIndex.flatMap {
          case ('.', x) =>
            ZStream.fromIterable(
              List(
                Node(x.toInt, y.toInt, Left)  -> Node(x.toInt, y.toInt, Right),
                Node(x.toInt, y.toInt, Right) -> Node(x.toInt, y.toInt, Left),
                Node(x.toInt, y.toInt, Up)    -> Node(x.toInt, y.toInt, Down),
                Node(x.toInt, y.toInt, Down)  -> Node(x.toInt, y.toInt, Up)
              )
            )
          case ('-', x) =>
            ZStream.fromIterable(
              List(
                Node(x.toInt, y.toInt, Left)  -> Node(x.toInt, y.toInt, Right),
                Node(x.toInt, y.toInt, Right) -> Node(x.toInt, y.toInt, Left),
                Node(x.toInt, y.toInt, Up)    -> Node(x.toInt, y.toInt, Left),
                Node(x.toInt, y.toInt, Up)    -> Node(x.toInt, y.toInt, Right),
                Node(x.toInt, y.toInt, Down)  -> Node(x.toInt, y.toInt, Left),
                Node(x.toInt, y.toInt, Down)  -> Node(x.toInt, y.toInt, Right)
              )
            )
          case ('|', x) =>
            ZStream.fromIterable(
              List(
                Node(x.toInt, y.toInt, Left)  -> Node(x.toInt, y.toInt, Up),
                Node(x.toInt, y.toInt, Left)  -> Node(x.toInt, y.toInt, Down),
                Node(x.toInt, y.toInt, Right) -> Node(x.toInt, y.toInt, Up),
                Node(x.toInt, y.toInt, Right) -> Node(x.toInt, y.toInt, Down),
                Node(x.toInt, y.toInt, Up)    -> Node(x.toInt, y.toInt, Down),
                Node(x.toInt, y.toInt, Down)  -> Node(x.toInt, y.toInt, Up)
              )
            )
          case ('/', x) =>
            ZStream.fromIterable(
              List(
                Node(x.toInt, y.toInt, Left)  -> Node(x.toInt, y.toInt, Up),
                Node(x.toInt, y.toInt, Right) -> Node(x.toInt, y.toInt, Down),
                Node(x.toInt, y.toInt, Up)    -> Node(x.toInt, y.toInt, Left),
                Node(x.toInt, y.toInt, Down)  -> Node(x.toInt, y.toInt, Right)
              )
            )
          case ('\\', x) =>
            ZStream.fromIterable(
              List(
                Node(x.toInt, y.toInt, Left)  -> Node(x.toInt, y.toInt, Down),
                Node(x.toInt, y.toInt, Right) -> Node(x.toInt, y.toInt, Up),
                Node(x.toInt, y.toInt, Up)    -> Node(x.toInt, y.toInt, Right),
                Node(x.toInt, y.toInt, Down)  -> Node(x.toInt, y.toInt, Left)
              )
            )
        }
        val horizontalEdges = ZStream.range(0, line.length).zipWithNext.flatMap {
          case (x1, Some(x2)) =>
            ZStream.fromIterable(
              List(Node(x1, y.toInt, Right) -> Node(x2, y.toInt, Left), Node(x2, y.toInt, Left) -> Node(x1, y.toInt, Right))
            )
          case (_, None) => ZStream.empty
        }
        val verticalEdges =
          if y > 0 then
            ZStream.range(0, line.length).flatMap { x =>
              ZStream.fromIterable(
                List(Node(x, y.toInt, Up) -> Node(x, (y - 1L).toInt, Down), Node(x, (y - 1L).toInt, Down) -> Node(x, y.toInt, Up))
              )
            }
          else ZStream.empty
        innerEdges ++ horizontalEdges ++ verticalEdges
      }
      .runCollect
      .map(_.toList.groupMap(_._1)(_._2))
      .map(Contraption(_))
