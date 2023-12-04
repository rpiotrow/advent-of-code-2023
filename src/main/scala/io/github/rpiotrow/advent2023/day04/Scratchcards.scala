package io.github.rpiotrow.advent2023.day04

import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.printLine

object Scratchcards:
  val solution: Solution =
    Input
      .readLines("day04.input")
      .mapZIO(Scratchcard.fromLine)
      .map(_.score)
      .runSum
      .flatMap { sum =>
        for _ <- printLine(s"The Elf's pile of scratchcards is worth $sum points")
        yield (sum, 0L)
      }
