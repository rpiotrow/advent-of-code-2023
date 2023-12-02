package io.github.rpiotrow.advent2023.day02

import zio.test.*
import zio.test.Assertion.*

object CubeSetSpec extends ZIOSpecDefault:

  def spec = suite("day02: CubeSetSpec")(
    test("5 blue, 4 red") {
      val cubeSets = CubeSet.fromString("5 blue, 4 red")
      assertZIO(cubeSets)(equalTo(CubeSet(blue = 5, red = 4)))
    },
    test("1 green, 3 red, 6 blue") {
      val cubeSets = CubeSet.fromString("1 green, 3 red, 6 blue")
      assertZIO(cubeSets)(equalTo(CubeSet(green = 1, red = 3, blue = 6)))
    },
    test("power of 4 red, 2 green, and 6 blue is 48") {
      val cubeSet = CubeSet(red = 4, green = 2, blue = 6)
      assert(cubeSet.power())(equalTo(48L))
    },
    test("minimal cube set of '3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green' is '4 red, 2 green, and 6 blue'") {
      val cubeSets = Seq(CubeSet(blue = 3, red = 4), CubeSet(red = 1, green = 2, blue = 6), CubeSet(green = 2))
      assert(cubeSets.minimalCubeSet())(equalTo(CubeSet(red = 4, green = 2, blue = 6)))
    }
  )
