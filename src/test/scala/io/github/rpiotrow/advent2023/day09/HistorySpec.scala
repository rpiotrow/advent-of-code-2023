package io.github.rpiotrow.advent2023.day09

import zio.test.*
import zio.test.Assertion.*

object HistorySpec extends ZIOSpecDefault:

  def spec = suite("day09: Historypec")(
    test("next of 0 3 6 9 12 15 is 18") {
      for
        history      <- History.fromLine("0 3 6 9 12 15")
        extrapolated <- history.nextExtrapolatedValue
      yield assert(extrapolated)(equalTo(18L))
    },
    test("previous of 0 3 6 9 12 15 is -3") {
      for
        history      <- History.fromLine("0 3 6 9 12 15")
        extrapolated <- history.previousExtrapolatedValue
      yield assert(extrapolated)(equalTo(-3L))
    }
  )
