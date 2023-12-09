package io.github.rpiotrow.advent2023.day06

import io.github.rpiotrow.advent2023.Input.parseLong
import zio.{IO, ZIO}

object DistanceParser:
  def parseDistanceList(line: String): IO[String, List[Long]] =
    line match
      case s"Distance: $distancesString" =>
        ZIO
          .foreach(distancesString.trim.split(' ').filterNot(_.isBlank).map(_.trim))(d =>
            parseLong(d, _ => s"Cannot parse '$d' as record distance value'")
          )
          .map(_.toList)
      case _ => ZIO.dieMessage(s"Cannot parse distance '$line'")

  def parseSingleDistance(line: String): IO[String, Long] =
    line match
      case s"Distance: $distanceString" =>
        val digitsString = distanceString.filter(_.isDigit)
        parseLong(digitsString, _ => s"Cannot parse '$digitsString' as record distance value'")
      case _ => ZIO.dieMessage(s"Cannot parse distance '$line'")
