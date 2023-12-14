package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day14.ParabolicReflectorDish
import zio.test.Assertion.equalTo
import zio.test.{ZIOSpecDefault, assert}

object Day14ParabolicReflectorSolutionSpec extends ZIOSpecDefault:

  def spec = suite("ParabolicReflectorDishSolutionSpec")(test("Parabolic Reflector Dish solution") {
    for solution <- ParabolicReflectorDish.solution
    yield assert(solution)(equalTo((111979L, 0L)))
  })
