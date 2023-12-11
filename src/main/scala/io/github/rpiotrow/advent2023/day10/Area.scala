package io.github.rpiotrow.advent2023.day10

import io.github.rpiotrow.advent2023.day10.Area.Position
import io.github.rpiotrow.advent2023.day10.Direction.*
import zio.stream.ZStream
import zio.{IO, ZIO}

import scala.annotation.tailrec

class Area(tiles: Vector[Vector[Tile]]):
  private val startPosition: Position =
    val y = tiles.indexWhere(_.contains(Tile.S))
    val x = tiles(y).indexOf(Tile.S)
    Position(x, y)

  private def getTile(position: Position): Option[Tile] =
    if position.y >= 0 && position.y < tiles.length then
      val row = tiles(position.y)
      if position.x >= 0 && position.x < row.length then Some(row(position.x))
      else None
    else None

  def loopLength(startDirection: Direction): Option[Int] =
    loopLength(startPosition, startDirection, 1)

  @tailrec
  private def loopLength(position: Position, direction: Direction, length: Int): Option[Int] =
    val nextPosition = position.go(direction)
    if nextPosition == startPosition then Some(length)
    else
      getTile(nextPosition) match
        case Some(tile) =>
          tile.go(direction) match
            case Some(nextDirection) => loopLength(nextPosition, nextDirection, length + 1)
            case None                => None
        case None => None

object Area:
  case class Position(x: Int, y: Int):
    def go(direction: Direction): Position =
      direction match
        case North => this.copy(y = this.y - 1)
        case South => this.copy(y = this.y + 1)
        case West  => this.copy(x = this.x - 1)
        case East  => this.copy(x = this.x + 1)

  def parse(lines: ZStream[Any, String, String]): IO[String, Area] =
    lines
      .mapZIO { line =>
        ZIO.foreach(line)(Tile.fromChar).map(_.toVector)
      }
      .runCollect
      .map(chunks => Area(chunks.toVector))
