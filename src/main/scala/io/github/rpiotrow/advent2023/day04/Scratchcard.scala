package io.github.rpiotrow.advent2023.day04

import io.github.rpiotrow.advent2023.Input.parseInt
import zio.{IO, ZIO}

class Scratchcard(val id: Int, winningNumbers: Seq[Int], ownedNumbers: Seq[Int]):
  def winAmount: Int = ownedNumbers.count(winningNumbers.contains)
  def score: Long =
    val exponent = winAmount
    if exponent == 0 then 0 else Math.pow(2, exponent-1).toLong

object Scratchcard:
  def fromLine(line: String): IO[String, Scratchcard] =
    line match
      case s"Card $idString: $winningNumbersString | $ownedNumbersString" =>
        for
          id             <- parseInt(idString.trim, _ => s"Cannot parse card id '${idString.trim}'")
          winningNumbers <- parseList(winningNumbersString)
          ownedNumbers   <- parseList(ownedNumbersString)
        yield Scratchcard(id, winningNumbers, ownedNumbers)
      case _ => ZIO.dieMessage(s"Cannot parse line $line")

  private def parseList(string: String): IO[String, Seq[Int]] =
    val strings = string.split(' ').map(_.trim).filterNot(_.isBlank)
    ZIO.foreach(strings)(s => parseInt(s, _ => s"Cannot parse number '$s'")).map(_.toSeq)
