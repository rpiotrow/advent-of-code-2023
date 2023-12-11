package io.github.rpiotrow.advent2023.day10

import io.github.rpiotrow.advent2023.day10.Tile.*
import zio.test.*
import zio.test.Assertion.*

object AreaSpec extends ZIOSpecDefault:

  def spec = suite("day10: AreaSpec")(
    test("sample area has loop starting from south of length 8") {
      val area: Area = Area(Vector(
        Vector(░,░,░,░,░),
        Vector(░,S,━,┓,░),
        Vector(░,┃,░,┃,░),
        Vector(░,┗,━,┛,░),
        Vector(░,░,░,░,░)
      ))
      val toSouth = area.loopLength(Direction.South)
      assert(toSouth)(equalTo(Some(8L)))
    }
  )
