package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day10.PipeMaze
import zio.test.Assertion.equalTo
import zio.test.{ZIOSpecDefault, assert}

object Day10PipeMazeSolutionSpec extends ZIOSpecDefault:

  def spec = suite("PipeMazeSolutionSpec")(test("Pipe Maze solution") {
    for solution <- PipeMaze.solution
      yield assert(solution)(equalTo((7063L, 0L)))
  })
