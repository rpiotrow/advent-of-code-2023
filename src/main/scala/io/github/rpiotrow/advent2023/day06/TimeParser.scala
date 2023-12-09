package io.github.rpiotrow.advent2023.day06

import io.github.rpiotrow.advent2023.Input.parseLong
import zio.{IO, ZIO}

object TimeParser:
  def parseTimeList(line: String): IO[String, List[Long]] =
    line match
      case s"Time: $timesString" =>
        ZIO
          .foreach(timesString.trim.split(' ').filterNot(_.isBlank).map(_.trim))(t =>
            parseLong(t, _ => s"Cannot parse '$t' as time value'")
          )
          .map(_.toList)
      case _ => ZIO.dieMessage(s"Cannot parse times '$line'")

  def parseSingleTime(line: String): IO[String, Long] =
    line match
      case s"Time: $timeString" =>
        val digitsString = timeString.filter(_.isDigit)
        parseLong(digitsString, _ => s"Cannot parse '$digitsString' as time value'")
      case _ => ZIO.dieMessage(s"Cannot parse times '$line'")
