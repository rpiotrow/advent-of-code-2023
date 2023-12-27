package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day07.CamelCards
import zio.test.Assertion.equalTo
import zio.test.{ZIOSpecDefault, assert}

object Day07CamelCardsSolutionSpec extends ZIOSpecDefault:

  def spec = suite("CamelCardsSolutionSpec")(test("Camel Cards solution") {
    for solution <- CamelCards.solution
    yield assert(solution)(equalTo((248836197L, 251195607L)))
  })
