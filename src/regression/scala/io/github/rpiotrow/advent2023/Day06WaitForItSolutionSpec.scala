package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day06.WaitForIt
import zio.test.Assertion.equalTo
import zio.test.{ZIOSpecDefault, assert}

object Day06WaitForItSolutionSpec extends ZIOSpecDefault:

  def spec = suite("WaitForItSolutionSpec")(test("Wait For It solution") {
    for solution <- WaitForIt.solution
      yield assert(solution)(equalTo((2269432L, 35865985L)))
  })
