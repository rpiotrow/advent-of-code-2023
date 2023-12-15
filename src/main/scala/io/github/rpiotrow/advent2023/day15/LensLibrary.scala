package io.github.rpiotrow.advent2023.day15

import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.printLine
import zio.stream.ZStream

object LensLibrary:

  val solution: Solution =
    Input
      .readLines("day15.input")
      .flatMap { line => ZStream.fromIterable(line.split(",")) }
      .map(Hash.valueFor)
      .runSum
      .flatMap { sum =>
        for _ <- printLine(s"The sum of the results is $sum")
        yield (sum, 0L)
      }
