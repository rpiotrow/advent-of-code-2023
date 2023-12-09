package io.github.rpiotrow.advent2023.day06

import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.printLine
import zio.{Chunk, IO, ZIO}

object WaitForIt:
  val solution: Solution =
    for
      inputLines <- Input.readLines("day06.input").runCollect
      part1 <- inputLines.asRacesList
        .flatMap(races => ZIO.collectAll(races.map(_.numberOfWaysToBeatTheRecord)))
        .map(_.product)
      part2 <- inputLines.asSingleRace
        .flatMap(_.numberOfWaysToBeatTheRecord)
      _ <- printLine(s"Product of possible ways of winning each races is $part1")
      _ <- printLine(s"Possible ways of winning single race is $part2")
    yield (part1, part2)

  extension (inputLines: Chunk[String])
    private def asRacesList: IO[String, List[Race]] =
      inputLines match
        case Chunk(timeLine, distanceLine) =>
          for
            times     <- TimeParser.parseTimeList(timeLine)
            distances <- DistanceParser.parseDistanceList(distanceLine)
          yield times.zip(distances).map { case (time, distance) => Race(time, distance) }
        case _ => ZIO.dieMessage("Invalid input")
    private def asSingleRace: IO[String, Race] =
      inputLines match
        case Chunk(timeLine, distanceLine) =>
          for
            time     <- TimeParser.parseSingleTime(timeLine)
            distance <- DistanceParser.parseSingleDistance(distanceLine)
          yield Race(time, distance)
        case _ => ZIO.dieMessage("Invalid input")
