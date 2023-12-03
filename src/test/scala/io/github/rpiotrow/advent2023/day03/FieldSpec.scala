package io.github.rpiotrow.advent2023.day03

import zio.test.*
import zio.test.Assertion.*

object FieldSpec extends ZIOSpecDefault:

  def spec = suite("day03: FieldSpec") {
    test("..+.58.") {
      val fields = Field.fromLine("..+.58.")
      val expected =
        Vector(Field.Blank, Field.Blank, Field.Symbol, Field.Blank, Field.Digit(5), Field.Digit(8), Field.Blank, Field.EndOfLine)
      assert(fields)(equalTo(expected))
    }
  }
