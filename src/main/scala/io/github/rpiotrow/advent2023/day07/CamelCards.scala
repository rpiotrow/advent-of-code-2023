package io.github.rpiotrow.advent2023.day07

import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.printLine
import zio.ZIO

object CamelCards:
  val solution: Solution =
    Input
      .readLines("day07.input")
      .mapZIO(Hand.fromLine)
      .runCollect
      .map(_.toList)
      .flatMap { handList =>
        ZIO.foreach(handList)(_.withJokers).map { withJokers =>
          (handList.sortAndSumWinnings, withJokers.sortAndSumWinnings)
        }
      }
      .flatMap { (sum1, sum2) =>
        for
          _ <- printLine(s"The total winnings is $sum1")
          _ <- printLine(s"The total winnings with jokers is $sum2")
        yield (sum1, sum2)
      }
