package io.github.rpiotrow.advent2023.day01

import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.{Console, ZIO}

object Trebuchet:

  val solution: Solution =
    Input
      .readLines("day01.input")
      .map { line =>
        val digitsPositions1 = DigitPositions.fromLineWithDigits(line)
        val digitsPositions2 = DigitPositions.fromLineWithDigitsAndNames(line)
        (digitsPositions1.firstDigit, digitsPositions1.lastDigit, digitsPositions2.firstDigit, digitsPositions2.lastDigit)
      }
      .collectZIO {
        case (Some(a), Some(b), Some(c), Some(d)) =>
          for
            value1 <- ZIO.attempt(s"$a$b".toInt).orElseFail(s"Cannot convert $a$b to Int")
            value2 <- ZIO.attempt(s"$c$d".toInt).orElseFail(s"Cannot convert $c$d to Int")
          yield (value1, value2)
        case _ => ZIO.dieMessage("Invalid input!")
      }
      .runFold((0, 0)) { (sums, values) =>
        val (sum1, sum2) = sums
        val (value1, value2) = values
        (sum1 + value1, sum2 + value2)
      }
      .flatMap { (sum1, sum2) =>
        for
          _ <- Console.printLine(s"The sum of all of the calibration values is $sum1")
          _ <- Console.printLine(s"The sum of all of the calibration values with digit names is $sum2")
        yield (sum1, sum2)
      }
