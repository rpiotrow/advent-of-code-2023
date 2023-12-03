package io.github.rpiotrow.advent2023.day03

import zio.test.*
import zio.test.Assertion.*

object SchematicLineSpec extends ZIOSpecDefault:

  // 467..114..
  private val firstLine = Vector(
    Field.Digit(4),
    Field.Digit(6),
    Field.Digit(7),
    Field.Blank,
    Field.Blank,
    Field.Digit(1),
    Field.Digit(1),
    Field.Digit(4),
    Field.Blank,
    Field.Blank
  )
  // ...*......
  private val secondLine = Vector(
    Field.Blank,
    Field.Blank,
    Field.Blank,
    Field.Symbol,
    Field.Blank,
    Field.Blank,
    Field.Blank,
    Field.Blank,
    Field.Blank,
    Field.Blank
  )

  def spec = suite("day03: SchematicLineSpec")(
    test("PartNumbersWithSymbol 1") {
      val schematicLine = SchematicLine(None, firstLine, Some(secondLine))
      assert(schematicLine.sumPartNumbersWithSymbol)(equalTo(467))
    },
    test("PartNumbersWithSymbol 2") {
      val schematicLine = SchematicLine(Some(firstLine), secondLine, None)
      assert(schematicLine.sumPartNumbersWithSymbol)(equalTo(0))
    }
  )
