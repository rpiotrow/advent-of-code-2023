package io.github.rpiotrow.advent2023.day02

import io.github.rpiotrow.advent2023.day02.CubeSetSpec.{suite, test}
import zio.test.*
import zio.test.Assertion.*

object GameSpec extends ZIOSpecDefault:

  def spec = suite("day02: GemeSpec")(
    test("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green") {
      val game = Game.fromLine("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green")
      assertZIO(game)(equalTo(Game(1, Seq(CubeSet(blue = 3, red = 4), CubeSet(red = 1, green = 2, blue = 6), CubeSet(green = 2)))))
    }
  )
