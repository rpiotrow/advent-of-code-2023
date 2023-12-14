package io.github.rpiotrow.advent2023.day14

import io.github.rpiotrow.advent2023.day14.{Platform, Field}
import io.github.rpiotrow.advent2023.day14.Field.*
import zio.test.*
import zio.test.Assertion.*

object PlatformSpec extends ZIOSpecDefault:

  def spec = suite("day14: PlatformSpec")(test("load of part of the sample is 15") {
    val platform: Platform = Platform(
      Vector(
        Vector(CubeShapedRock, Empty, Empty, Empty, Empty, CubeShapedRock, Empty, Empty, Empty, Empty),
        Vector(CubeShapedRock, Empty, Empty, Empty, Empty, CubeShapedRock, CubeShapedRock, CubeShapedRock, Empty, Empty),
        Vector(Empty, Empty, RoundedRock, Empty, Empty, Empty, Empty, Empty, Empty, Empty),
        Vector(Empty, Empty, RoundedRock, Empty, Empty, CubeShapedRock, Empty, RoundedRock, Empty, RoundedRock)
      )
    )
    assert(platform.calculateLoad)(equalTo(15L))
  })
