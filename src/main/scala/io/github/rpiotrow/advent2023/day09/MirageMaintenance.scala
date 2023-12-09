package io.github.rpiotrow.advent2023.day09

import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.ConsoleLive.printLine
import zio.{Chunk, ZIO}

object MirageMaintenance:
  private val histories = Input
    .readLines("day09.input")
    .mapZIO(History.fromLine)
  val solution: Solution =
    ZIO.scoped {
      histories.broadcast(2, 10).flatMap {
        case Chunk(streamCopy1, streamCopy2) =>
          for
            part1 <- streamCopy1
              .mapZIO(_.nextExtrapolatedValue)
              .runSum
              .fork
            part2 <- streamCopy2
              .mapZIO(_.previousExtrapolatedValue)
              .runSum
              .fork
            sum1 <- part1.join
            sum2 <- part2.join
            _    <- printLine(s"The sum of next extrapolated values is $sum1")
            _    <- printLine(s"The sum of previous extrapolated values is $sum2")
          yield (sum1, sum2)
        case _ => ZIO.dieMessage("impossible")
      }
    }
