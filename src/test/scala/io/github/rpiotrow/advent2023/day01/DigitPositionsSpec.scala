package io.github.rpiotrow.advent2023.day01

import zio.test.*
import zio.test.Assertion.*

object DigitPositionsSpec extends ZIOSpecDefault:
  def spec = suite("day01: DigitPositionsSpec")(
    test("two1nine is (2, 9)") {
      val digitPositions = DigitPositions.fromLineWithDigitsAndNames("two1nine")
      val pair = (digitPositions.firstDigit, digitPositions.lastDigit)
      assert(pair)(equalTo((Some(2),Some(9))))
    },
    test("7pqrstsixteen is (7,6)") {
      val digitPositions = DigitPositions.fromLineWithDigitsAndNames("7pqrstsixteen")
      val pair = (digitPositions.firstDigit, digitPositions.lastDigit)
      assert(pair)(equalTo((Some(7), Some(6))))
    }
  )
