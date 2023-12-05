package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day05.IfYouGiveASeedAFertilizer
import zio.test.Assertion.equalTo
import zio.test.{ZIOSpecDefault, assert}

object Day05IfYouGiveASeedAFertilizerSolutionSpec extends ZIOSpecDefault:

  def spec = suite("IfYouGiveASeedAFertilizerSolutionSpec")(test("If You Give A Seed A Fertilizer solution") {
    for solution <- IfYouGiveASeedAFertilizer.solution
    yield assert(solution)(equalTo((309796150L, 0L)))
  })
