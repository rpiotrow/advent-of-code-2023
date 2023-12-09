package io.github.rpiotrow.advent2023.day06

import zio.IO
import zio.stream.ZStream

case class Race(time: Long, recordDistance: Long):
  def numberOfWaysToBeatTheRecord: IO[String, Long] =
    ZStream
      .range(1, time.toInt)
      .map(speed => speed.toLong * (time - speed.toLong))
      .filter(_ > recordDistance)
      .runCount
