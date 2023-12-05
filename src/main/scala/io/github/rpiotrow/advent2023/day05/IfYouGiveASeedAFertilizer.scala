package io.github.rpiotrow.advent2023.day05

import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.printLine
import zio.ZIO
import zio.stream.ZSink

object IfYouGiveASeedAFertilizer:

  val solution: Solution =
    ZIO.scoped {
      Input
        .readLines("day05.input")
        .split(_.isBlank)
        .map(_.toList)
        .peel(ZSink.head)
        .flatMap { case (seedsLine, mapsStream) =>
          seedsLine match
            case Some(seedsString :: Nil) => Almanac.parse(seedsString, mapsStream)
            case e                        => ZIO.dieMessage(s"Invalid seeds: $e")
        }
        .flatMap { almanac =>
          ZIO.fromOption(almanac.lowestLocation).orElseFail("Cannot find lowest location!")
        }
        .flatMap { lowestLocation =>
          for _ <- printLine(s"The lowest location number that corresponds to any of the initial seed numbers is $lowestLocation")
          yield (lowestLocation, 0L)
        }
    }
