package io.github.rpiotrow.advent2023.day19

import zio.{IO, ZIO}

enum PartCategory:
  case ExtremelyCoolLooking
  case Musical
  case Aerodynamic
  case Shiny

object PartCategory:
  def fromString(s: String): IO[String, PartCategory] =
    s match
      case "x" => ZIO.succeed(ExtremelyCoolLooking)
      case "m" => ZIO.succeed(Musical)
      case "a" => ZIO.succeed(Aerodynamic)
      case "s" => ZIO.succeed(Shiny)
      case _   => ZIO.dieMessage(s"Unknown part category: $s")
