package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day16.TheFloorWillBeLava
import zio.test.Assertion.equalTo
import zio.test.{ZIOSpecDefault, assert}

object Day16TheFloorWillBeLavaSolutionSpec extends ZIOSpecDefault:

  def spec = suite("TheFloorWillBeLavaSolutionSpec")(test("The Floor Will Be Lava solution") {
    for solution <- TheFloorWillBeLava.solution
    yield assert(solution)(equalTo((8323L, 8491L)))
  })
