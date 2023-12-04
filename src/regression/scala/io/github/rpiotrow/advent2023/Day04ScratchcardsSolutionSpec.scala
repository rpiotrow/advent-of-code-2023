package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day04.Scratchcards
import zio.test.Assertion.equalTo
import zio.test.{ZIOSpecDefault, assert}

object Day04ScratchcardsSolutionSpec extends ZIOSpecDefault:

  def spec = suite("ScratchcardsSolutionSpec")(test("Scratchcards solution") {
    for solution <- Scratchcards.solution
      yield assert(solution)(equalTo((23847L, 8570000L)))
  })

