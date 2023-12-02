package io.github.rpiotrow.advent2023.day01

import scala.util.matching.Regex

case class DigitPositions(digit: Int, positions: Seq[Int])

object DigitPositions:

  private val numberPatterns = Seq(
    1 -> List("1".r),
    2 -> List("2".r),
    3 -> List("3".r),
    4 -> List("4".r),
    5 -> List("5".r),
    6 -> List("6".r),
    7 -> List("7".r),
    8 -> List("8".r),
    9 -> List("9".r)
  )

  private val numberAndNamePatterns = Seq(
    1 -> List("1".r, "one".r),
    2 -> List("2".r, "two".r),
    3 -> List("3".r, "three".r),
    4 -> List("4".r, "four".r),
    5 -> List("5".r, "five".r),
    6 -> List("6".r, "six".r),
    7 -> List("7".r, "seven".r),
    8 -> List("8".r, "eight".r),
    9 -> List("9".r, "nine".r)
  )

  def fromLineWithDigits(line: String): Seq[DigitPositions]         = fromLine(line, numberPatterns)

  private def fromLine(line: String, patterns: Seq[(Int, List[Regex])]): Seq[DigitPositions] =
    patterns.map { case (digit, patterns) =>
      DigitPositions(digit, patterns.flatMap(_.findAllMatchIn(line).map(_.start)))
    }

  def fromLineWithDigitsAndNames(line: String): Seq[DigitPositions] = fromLine(line, numberAndNamePatterns)

  extension (seq: Seq[DigitPositions])
    def firstDigit: Option[Int] = getDigit(Ordering[Int])
    def lastDigit: Option[Int]  = getDigit(Ordering[Int].reverse)
    private def getDigit(ordering: Ordering[Int]) =
      seq
        .flatMap { ds => ds.positions.map(position => (ds.digit -> position)) }
        .sortBy { case (_, position) => position }(ordering)
        .headOption
        .map { case (digit, _) => digit }
