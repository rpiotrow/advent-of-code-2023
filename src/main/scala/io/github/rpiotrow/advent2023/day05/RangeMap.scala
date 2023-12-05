package io.github.rpiotrow.advent2023.day05

import io.github.rpiotrow.advent2023.Input.parseLong
import zio.{IO, ZIO}

class RangeMap(entries: Seq[RangeMapEntry]):
  def map(source: Long): Long =
    entries.map(_.map(source)).collectFirst { case Some(t) => t }.getOrElse(source)

object RangeMap:
  def fromStrings(strings: List[String]): IO[String, RangeMap] =
    strings match
      case Nil       => ZIO.dieMessage("Range map with no name!")
      case _ :: maps => ZIO.foreach(maps)(RangeMapEntry.fromString).map(l => RangeMap(l))

class RangeMapEntry(destinationRangeStart: Long, sourceRangeStart: Long, rangeLength: Long):
  def map(source: Long): Option[Long] =
    Option.when(source >= sourceRangeStart && source < sourceRangeStart + rangeLength)(
      destinationRangeStart + (source - sourceRangeStart)
    )
object RangeMapEntry:
  def fromString(s: String): IO[String, RangeMapEntry] =
    s match
      case s"$destinationRangeStartString $sourceRangeStartString $rangeLengthString" =>
        for
          destinationRangeStart <- parseLong(
            destinationRangeStartString,
            _ => s"Cannot parse '$destinationRangeStartString' as destination range start"
          )
          sourceRangeStart <- parseLong(
            sourceRangeStartString,
            _ => s"Cannot parse '$sourceRangeStartString' as source range start"
          )
          rangeLength <- parseLong(rangeLengthString, _ => s"Cannot parse '$rangeLengthString' as range length")
        yield RangeMapEntry(destinationRangeStart, sourceRangeStart, rangeLength)
      case _ => ZIO.dieMessage(s"Cannot parse RangeMapEntry from '$s'")
