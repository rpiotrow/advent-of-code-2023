package io.github.rpiotrow.advent2023.day03

enum Field:
  case Blank         extends Field
  case Digit(i: Int) extends Field
  case Symbol        extends Field
  case EndOfLine     extends Field
object Field:
  def fromLine(line: String): Vector[Field] =
    line.map(Field.fromChar).appended(Field.EndOfLine).toVector

  private def fromChar(c: Char): Field =
    c match
      case '.'            => Field.Blank
      case c if c.isDigit => Field.Digit(c - '0')
      case _              => Field.Symbol
