package io.github.rpiotrow.advent2023.day14

import io.github.rpiotrow.advent2023.day14.Field.*
import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.printLine
import zio.ZIO

object ParabolicReflectorDish:
  val solution: Solution =
    Input
      .readLines("day14.input")
      .runFoldZIO(Platform(Vector.empty)) { case (platform, line) =>
        ZIO.foreach(line)(Field.fromChar).map { parsedRow =>
          platform.withNewRowAndRoundRocksMovedNorth(parsedRow.toVector)
        }
      }
      .tap(_.print)
      .map(_.calculateLoad)
      .flatMap { load =>
        for _ <- printLine(s"The total load on the north support beams is $load")
        yield (load, 0L)
      }
