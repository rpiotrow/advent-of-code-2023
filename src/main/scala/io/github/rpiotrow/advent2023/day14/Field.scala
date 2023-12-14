package io.github.rpiotrow.advent2023.day14

import zio.{IO, ZIO}

enum Field:
  case RoundedRock
  case CubeShapedRock
  case Empty

object Field:
  def fromChar(c: Char): IO[String, Field] =
    c match
      case 'O' => ZIO.succeed(RoundedRock)
      case '#' => ZIO.succeed(CubeShapedRock)
      case '.' => ZIO.succeed(Empty)
      case _   => ZIO.dieMessage(s"Unknown field '$c'")
