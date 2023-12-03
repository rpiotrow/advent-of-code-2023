package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day03.GearRatios
import zio.test.*
import zio.test.Assertion.*

object Day03GearRatiosSolutionSpec extends ZIOSpecDefault:

  def spec = suite("GearRatiosSolutionSpec")(test("Gear Ratios solution") {
    for solution <- GearRatios.solution
    yield assert(solution)(equalTo((520019L, 0L)))
  })
