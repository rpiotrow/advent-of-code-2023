package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day15.LensLibrary
import zio.test.Assertion.equalTo
import zio.test.{ZIOSpecDefault, assert}

object Day15LensLibrarySolutionSpec extends ZIOSpecDefault:

  def spec = suite("LensLibrarySolutionSpec")(test("Lens Library solution") {
    for solution <- LensLibrary.solution
    yield assert(solution)(equalTo((513172L, 0L)))
  })
