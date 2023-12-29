package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day19.Aplenty
import zio.test.Assertion.equalTo
import zio.test.{ZIOSpecDefault, assert}

object Day19AplentySolutionSpec extends ZIOSpecDefault:

  def spec = suite("AplentySolutionSpec")(test("Aplenty solution") {
    for solution <- Aplenty.solution
    yield assert(solution)(equalTo((398527L, 0L)))
  })
