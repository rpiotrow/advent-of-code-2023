package io.github.rpiotrow.advent2023.day09

import io.github.rpiotrow.advent2023.Input.parseInt
import zio.stream.ZStream
import zio.{IO, ZIO}

case class History(data: Seq[Int]):
  def nextExtrapolatedValue: IO[String, Int] =
    for
      differences <- History.differences(data)
      value       <- History.extrapolatedNext(data, differences)
    yield value

  def previousExtrapolatedValue: IO[String, Int] =
    for
      differences <- History.differences(data)
      value       <- History.extrapolatedPrevious(data, differences)
    yield value

object History:
  def fromLine(line: String): IO[String, History] =
    ZIO
      .foreach(line.split(' '))(s => parseInt(s, _ => s"Cannot parse '$s' as integer number"))
      .map(_.toSeq)
      .map(History.apply)

  def extrapolatedNext(data: Seq[Int], diffs: Seq[Int]): IO[String, Int] =
    if diffs.forall(_ == 0) then ZIO.fromOption(data.lastOption).orElseFail(s"Empty data!!!")
    else
      for
        nextDiffs     <- differences(diffs)
        extrapolated  <- extrapolatedNext(diffs, nextDiffs)
        lastDataValue <- ZIO.fromOption(data.lastOption).orElseFail(s"Empty data!!!")
      yield lastDataValue + extrapolated

  def extrapolatedPrevious(data: Seq[Int], diffs: Seq[Int]): IO[String, Int] =
    if diffs.forall(_ == 0) then ZIO.fromOption(data.headOption).orElseFail(s"Empty data!!!")
    else
      for
        nextDiffs      <- differences(diffs)
        extrapolated   <- extrapolatedPrevious(diffs, nextDiffs)
        firstDataValue <- ZIO.fromOption(data.headOption).orElseFail(s"Empty data!!!")
      yield firstDataValue - extrapolated

  def differences(seq: Seq[Int]): IO[String, Seq[Int]] =
    ZStream
      .fromIterable(seq)
      .zipWithPrevious
      .collect { case (Some(p), e) => (p, e) }
      .map { case (a, b) => b - a }
      .runCollect
      .map(_.toSeq)
