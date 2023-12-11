package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day11.CosmicExpansion
import zio.test.Assertion.equalTo
import zio.test.{ZIOSpecDefault, assert}

object Day11CosmicExpansionSolutionSpec extends ZIOSpecDefault:

  def spec = suite("CosmicExpansionSolutionSpec")(test("Cosmic Expansion solution") {
    for solution <- CosmicExpansion.solution
    yield assert(solution)(equalTo((9609130L, 702152204842L)))
  })
