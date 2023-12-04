package io.github.rpiotrow.advent2023.day04

import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.{Chunk, ZIO}
import zio.Console.printLine

object Scratchcards:
  private val cards = Input
    .readLines("day04.input")
    .mapZIO(Scratchcard.fromLine)
  val solution: Solution =
    ZIO.scoped {
      cards.broadcast(2, 10).flatMap {
        case Chunk(streamCopy1, streamCopy2) =>
          for
            part1 <- streamCopy1
              .map(_.score)
              .runSum
              .fork
            part2 <- streamCopy2
              .runFold(Counter(Map.empty)) { (counter, card) =>
                counter.addCards(id = card.id, amount = counter.numberOfCards(card.id) + 1, length = card.winAmount)
              }
              .map(_.sum)
              .fork
            sum1 <- part1.join
            sum2 <- part2.join
            _ <- printLine(s"The Elf's pile of scratchcards is worth $sum1 points")
            _ <- printLine(s"I end up with $sum2 total scratchcards")
          yield (sum1, sum2)
        case _ => ZIO.dieMessage("impossible")
      }
    }
