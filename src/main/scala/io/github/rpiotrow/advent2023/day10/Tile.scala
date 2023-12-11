package io.github.rpiotrow.advent2023.day10

import io.github.rpiotrow.advent2023.day10.Direction.*
import zio.{IO, ZIO}

enum Tile:
  case ┃ extends Tile
  case ━ extends Tile
  case ┗ extends Tile
  case ┛ extends Tile
  case ┓ extends Tile
  case ┏ extends Tile
  case ░ extends Tile
  case S extends Tile

  def go(direction: Direction): Option[Direction] =
    (this, direction) match
      case (┃, North) => Some(North)
      case (┃, South) => Some(South)
      case (┃, _)     => None
      case (━, West)  => Some(West)
      case (━, East)  => Some(East)
      case (━, _)     => None
      case (┗, South) => Some(East)
      case (┗, West)  => Some(North)
      case (┗, _)     => None
      case (┛, South) => Some(West)
      case (┛, East)  => Some(North)
      case (┛, _)     => None
      case (┓, East)  => Some(South)
      case (┓, North) => Some(West)
      case (┓, _)     => None
      case (┏, North) => Some(East)
      case (┏, West)  => Some(South)
      case (┏, _)     => None
      case (░, _)     => None
      case (S, _)     => None

object Tile:
  def fromChar(c: Char): IO[String, Tile] =
    c match
      case '|' => ZIO.succeed(┃)
      case '-' => ZIO.succeed(━)
      case 'L' => ZIO.succeed(┗)
      case 'J' => ZIO.succeed(┛)
      case '7' => ZIO.succeed(┓)
      case 'F' => ZIO.succeed(┏)
      case '.' => ZIO.succeed(░)
      case 'S' => ZIO.succeed(S)
      case c   => ZIO.dieMessage(s"Unknown tile '$c'")
