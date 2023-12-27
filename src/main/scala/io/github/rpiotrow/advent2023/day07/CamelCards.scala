package io.github.rpiotrow.advent2023.day07

import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.printLine

object CamelCards:
  val solution: Solution =
    Input
      .readLines("day07.input")
      .mapZIO(Hand.fromLine)
      .runCollect
      .map(_.toList.sorted.zipWithIndex)
      .map(_.map { case (hand, index) => hand.bid * (index + 1) }.sum)
      .flatMap { case sum =>
        for _ <- printLine(s"The total winnings is $sum")
        yield (sum, 0L)
      }
