package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day02.CubeConundrum
import zio.test.*
import zio.test.Assertion.*

object Day02CubeConundrumSolutionSpec extends ZIOSpecDefault:

  def spec = suite("CubeConundrumSolutionSpec")(test("Cube Conundrum solution") {
    for solution <- CubeConundrum.solution
    yield assert(solution)(equalTo((2776L, 68638L)))
  })
