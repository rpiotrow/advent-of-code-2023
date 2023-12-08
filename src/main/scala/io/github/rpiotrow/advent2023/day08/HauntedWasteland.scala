package io.github.rpiotrow.advent2023.day08

import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.printLine
import zio.ZIO
import zio.stream.ZSink

object HauntedWasteland:
  val solution: Solution =
    ZIO.scoped {
      Input
        .readLines("day08.input")
        .peel(ZSink.head)
        .flatMap { case (firstLineOption, restOfStream) =>
          for
            navigation <- Navigation.parse(firstLineOption, restOfStream)
            count1     <- navigation.simulateSteps("AAA", _ == "ZZZ")
            nodesWithA = navigation.nodeMap.keys.filter(_.endsWith("A")).toList
            count2 <- ZIO
              .foreach(nodesWithA) { nodeId => navigation.simulateSteps(nodeId, _.endsWith("Z")) }
              .map(_.reduce(LeastCommonMultiple.lcm))
            _ <- printLine(s"$count1 steps are required to reach ZZZ")
            _ <- printLine(s"$count2 steps are required before you're only on nodes that end with Z")
          yield (count1, count2)
        }
    }
