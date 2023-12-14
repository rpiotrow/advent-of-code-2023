package io.github.rpiotrow.advent2023.day14

import io.github.rpiotrow.advent2023.day14.Field.{CubeShapedRock, Empty, RoundedRock}
import zio.Console.printLine
import zio.{IO, ZIO}

import java.io.IOException

case class Platform(data: Vector[Vector[Field]]):
  def print: IO[IOException, Vector[Unit]] = ZIO.foreach(data.reverse) { row =>
    printLine(row.map {
      case RoundedRock    => 'O'
      case CubeShapedRock => '#'
      case Empty          => '.'
    }.mkString)
  }

  def withNewRowAndRoundRocksMovedNorth(originalRow: Vector[Field]): Platform =
    val (newRow, newPlatform) = originalRow.zipWithIndex.foldLeft((originalRow, this)) {
      case ((rowAcc, platformAcc), (field, x)) =>
        if field == RoundedRock then
          val maxMove = platformAcc.maxMove(x)
          if maxMove >= 0 then (rowAcc.updated(x, Empty), platformAcc.withRoundedRockAt(x, maxMove))
          else (rowAcc, platformAcc)
        else (rowAcc, platformAcc)
    }
    Platform(newPlatform.data.prepended(newRow))

  def withRoundedRockAt(x: Int, y: Int): Platform =
    val row    = data(y)
    val newRow = row.updated(x, RoundedRock)
    Platform(data.updated(y, newRow))

  def maxMove(x: Int): Int = maxMove(x, -1)
  private def maxMove(x: Int, y: Int): Int =
    if y + 1 == data.length then y
    else if data(y + 1)(x) != Empty then y
    else maxMove(x, y + 1)

  def calculateLoad: Long =
    data.zipWithIndex.map { case (row, index) => row.count(_ == RoundedRock) * (index + 1) }.sum
