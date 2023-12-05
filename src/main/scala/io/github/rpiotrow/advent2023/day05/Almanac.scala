package io.github.rpiotrow.advent2023.day05

import io.github.rpiotrow.advent2023.Input.parseLong
import zio.{IO, ZIO}
import zio.stream.ZStream

case class Almanac(seeds: Seq[Long], maps: Seq[RangeMap]):
  def lowestLocation: Option[Long] =
    seeds.map(translateSeed).minOption
  private def translateSeed(seed: Long): Long =
    maps.foldLeft(seed) { case (seed, map) => map.map(seed) }

object Almanac:
  def parse(seedsLine: String, mapsStream: ZStream[Any, String, List[String]]): IO[String, Almanac] =
    for
      seeds <- parseSeeds(seedsLine)
      maps  <- mapsStream.mapZIO(RangeMap.fromStrings).runCollect.map(_.toList)
    yield Almanac(seeds, maps)

  private def parseSeeds(s: String) =
    s match
      case s"seeds: $seedsString" =>
        ZIO.foreach(seedsString.split(' '))(s => parseLong(s, _ => s"Cannot parse '$s' as seed"))
      case e => ZIO.dieMessage(s"Cannot parse '$e' as seed list")
