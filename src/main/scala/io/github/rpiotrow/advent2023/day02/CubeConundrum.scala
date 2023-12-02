package io.github.rpiotrow.advent2023.day02

import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.printLine
import zio.{Chunk, ZIO}

object CubeConundrum:

  val solution: Solution =
    val games = Input.readLines("day02.input").mapZIO(Game.fromLine)
    ZIO.scoped {
      games.broadcast(2, 10).flatMap {
        case Chunk(streamCopy1, streamCopy2) =>
          for
            part1 <- streamCopy1
              .filter {
                _.cubeSets.forall { cubeSet =>
                  cubeSet.red <= 12 && cubeSet.green <= 13 && cubeSet.blue <= 14
                }
              }
              .map(_.id)
              .runSum
              .fork
            part2 <- streamCopy2
              .map(_.cubeSets.minimalCubeSet().power())
              .runSum
              .fork
            sum1 <- part1.join
            sum2 <- part2.join
            _ <- printLine(s"The sum of IDs of possible games is $sum1")
            _ <- printLine(s"The sum of the power of cube sets with fewest number of cubes of each color is $sum2")
          yield (sum1, sum2)
        case _ => ZIO.dieMessage("impossible")
      }
    }
