package io.github.rpiotrow.advent2023.day07

import zio.{IO, ZIO}

enum Card:
  case 🂲 extends Card
  case 🂳 extends Card
  case 🂴 extends Card
  case 🂵 extends Card
  case 🂶 extends Card
  case 🂷 extends Card
  case 🂸 extends Card
  case 🂹 extends Card
  case 🂺 extends Card
  case 🂻 extends Card
  case 🂽 extends Card
  case 🂾 extends Card
  case 🂱 extends Card

object Card:
  def fromChar(c: Char): IO[String, Card] =
    c match
      case 'A' => ZIO.succeed(🂱)
      case 'K' => ZIO.succeed(🂾)
      case 'Q' => ZIO.succeed(🂽)
      case 'J' => ZIO.succeed(🂻)
      case 'T' => ZIO.succeed(🂺)
      case '9' => ZIO.succeed(🂹)
      case '8' => ZIO.succeed(🂸)
      case '7' => ZIO.succeed(🂷)
      case '6' => ZIO.succeed(🂶)
      case '5' => ZIO.succeed(🂵)
      case '4' => ZIO.succeed(🂴)
      case '3' => ZIO.succeed(🂳)
      case '2' => ZIO.succeed(🂲)
      case _   => ZIO.dieMessage(s"Unknown card $c")
  given Ordering[Card] = Ordering.by[Card, Int](_.ordinal)
