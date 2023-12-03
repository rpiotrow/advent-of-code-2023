package io.github.rpiotrow.advent2023.day03

import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.printLine

object GearRatios:
  val solution: Solution =
    Input
      .readLines("day03.input")
      .map(Field.fromLine)
      .zipWithPreviousAndNext
      .map(SchematicLine.apply)
      .map(_.sumPartNumbersWithSymbol)
      .runSum
      .flatMap { sum =>
        for _ <- printLine(s"The sum of all of the part numbers in the engine schematic is $sum")
        yield (sum, 0L)
      }
