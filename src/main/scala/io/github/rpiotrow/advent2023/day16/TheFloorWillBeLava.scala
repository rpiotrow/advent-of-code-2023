package io.github.rpiotrow.advent2023.day16

import io.github.rpiotrow.advent2023.day16.Side.*
import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.printLine

object TheFloorWillBeLava:
  val solution: Solution =
    for
      contraption <- Contraption.parse(Input.readLines("day16.input"))
      marked00 = contraption.mark(Node(0, 0, Left)).map(node => (node.x, node.y)).size
      maxX   = contraption.maxX
      maxY   = contraption.maxy
      list = Range.inclusive(0, maxX).flatMap(x => List(Node(x, 0, Down), Node(x, maxY, Down))) ++
        Range.inclusive(0, maxY).flatMap(y => List(Node(0, y, Left), Node(maxX, y, Right)))
      // optimize me: this takes almost a minute 😱
      maxMarked = list.map(startNode => contraption.mark(startNode).map(node => (node.x, node.y)).size).max
      _ <- printLine(s"There are $marked00 tiles being energized when starting from (0,0)")
      _ <- printLine(s"There are $maxMarked tiles being energized when starting from any edge")
    yield (marked00, maxMarked)
