package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day01.Trebuchet
import zio.test.*
import zio.test.Assertion.*

object Day01TreBuchetSolutionSpec extends ZIOSpecDefault:

  def spec = suite("TrebuchetSolutionSpec")(
    test("Trebuchet solution") {
      for solution <- Trebuchet.solution
      yield assert(solution)(equalTo((55123L, 55260L)))
    }
  )
