package io.github.rpiotrow.advent2023.day02

import io.github.rpiotrow.advent2023.Input.parseInt
import zio.{IO, ZIO}

case class Game(id: Int, cubeSets: Seq[CubeSet])
object Game:
  def fromLine(line: String): IO[String, Game] =
    line match
      case s"Game $idString: $cubeSets" =>
        for
          id       <- parseInt(idString, _ => s"Cannot parse id $idString")
          cubeSets <- ZIO.foreach(cubeSets.split(';'))(s => CubeSet.fromString(s.trim))
        yield Game(id, cubeSets)
      case _ => ZIO.dieMessage(s"Invalid input line $line")

case class CubeSet(red: Int = 0, green: Int = 0, blue: Int = 0):
  def power(): Long = red * green * blue

object CubeSet:
  val empty: CubeSet = CubeSet()
  def fromString(string: String): IO[String, CubeSet] =
    // we do not check if there are at most 3 items in the line and that they are all different colors
    ZIO.foldLeft(string.split(','))(CubeSet.empty) { (cubeSet, s) =>
      s.trim match
        case s"$amount $colorName" =>
          parseInt(amount, _ => s"Cannot parse amount of cubes in $amount").flatMap { amount =>
            colorName match
              case "red"   => ZIO.succeed(cubeSet.copy(red = amount))
              case "green" => ZIO.succeed(cubeSet.copy(green = amount))
              case "blue"  => ZIO.succeed(cubeSet.copy(blue = amount))
              case _       => ZIO.dieMessage(s"Unknown cube color name: $colorName")
          }
        case _ => ZIO.dieMessage(s"Invalid cube set $s")
    }

extension (cubeSets: Seq[CubeSet])
  def minimalCubeSet(): CubeSet =
    cubeSets.fold(CubeSet.empty) { (minimalCubeSet, cubeSet) =>
      minimalCubeSet.copy(
        red = Math.max(minimalCubeSet.red, cubeSet.red),
        green = Math.max(minimalCubeSet.green, cubeSet.green),
        blue = Math.max(minimalCubeSet.blue, cubeSet.blue)
      )
    }
