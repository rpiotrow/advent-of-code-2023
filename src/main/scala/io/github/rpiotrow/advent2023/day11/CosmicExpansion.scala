package io.github.rpiotrow.advent2023.day11

import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.printLine

object CosmicExpansion:
  val solution: Solution =
    for
      image <- GalaxiesImage.parse(Input.readLines("day11.input"))
      lengths1 = image.expand(1).shortestPathsLengthBetweenAll
      lengths2 = image.expand(999999).shortestPathsLengthBetweenAll
      _ <- printLine(s"The sum of all lengths is $lengths1")
      _ <- printLine(s"The sum of all lengths for older galaxies is $lengths2")
    yield (lengths1, lengths2)
