package io.github.rpiotrow.advent2023.day08

import zio.{IO, ZIO}
import zio.stream.ZStream

case class Node(id: String, leftNode: String, rightNode: String)

object Node:
  def makeNodeMap(stream: ZStream[Any, String, String]): IO[String, Map[String, Node]] =
    stream
      .filterNot(_.isBlank)
      .mapZIO(Node.fromLine)
      .map(node => node.id -> node)
      .runCollect
      .map(_.toMap)
  private def fromLine(line: String): IO[String, Node] =
    line match
      case s"$id = ($left, $right)" => ZIO.succeed(Node(id, left, right))
      case _                        => ZIO.dieMessage(s"Cannot parse '$line' as Node")
