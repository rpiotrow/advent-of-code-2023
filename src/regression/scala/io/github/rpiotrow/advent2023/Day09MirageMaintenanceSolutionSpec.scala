package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day09.MirageMaintenance
import zio.test.Assertion.equalTo
import zio.test.{ZIOSpecDefault, assert}

object Day09MirageMaintenanceSolutionSpec extends ZIOSpecDefault:

  def spec = suite("MirageMaintenanceSolutionSpec")(test("Mirage Maintenance solution") {
    for solution <- MirageMaintenance.solution
    yield assert(solution)(equalTo((1789635132L, 913L)))
  })
