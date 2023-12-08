package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day08.HauntedWasteland
import zio.test.Assertion.equalTo
import zio.test.{ZIOSpecDefault, assert}

object Day08HauntedWastelandSolutionSpec extends ZIOSpecDefault:

  def spec = suite("HauntedWastelandSolutionSpec")(test("Haunted Wasteland solution") {
    for solution <- HauntedWasteland.solution
    yield assert(solution)(equalTo((21251L, 11678319315857L)))
  })
