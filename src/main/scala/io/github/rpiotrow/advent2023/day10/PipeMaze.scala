package io.github.rpiotrow.advent2023.day10

import io.github.rpiotrow.advent2023.day10.Direction.*
import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.printLine
import zio.ZIO
import zio.stream.ZStream

object PipeMaze:
  val solution: Solution =
    for
      area <- Area.parse(Input.readLines("day10.input"))
      loopLength <- ZStream
        .fromIterable(Direction.values)
        .map(area.loopLength)
        .collectSome
        .runHead
        .flatMap(ZIO.fromOption)
        .orElseFail(s"No loop found!!!")
      steps = loopLength / 2
      _ <- printLine(
        s"There is $steps along the loop to get from the starting position to the point farthest from the starting position"
      )
    yield (steps, 0L)
