package io.github.rpiotrow.advent2023.day19

import io.github.rpiotrow.advent2023.day19.RuleDecision.*
import io.github.rpiotrow.advent2023.{Input, Solution}
import zio.Console.printLine
import zio.{Chunk, ZIO}

object Aplenty:
  val solution: Solution =
    Input
      .readLines("day19.input")
      .split(_.isEmpty)
      .runCollect
      .map(_.toList)
      .flatMap {
        case workflowsChunk :: partsChunk :: Nil =>
          for
            workflows <- ZIO.foreach(workflowsChunk.toList)(Workflow.parse)
            parts     <- ZIO.foreach(partsChunk.toList)(Part.parse)
          yield (workflows.map(w => w.name -> w).toMap, parts)
        case _ => ZIO.dieMessage("Invalid input")
      }
      .flatMap { case (workflowsMap, parts) =>
        for
          sum <- ZIO
            .foreach(parts) { part =>
              ZIO
                .iterate(RuleDecision.Move("in"))(r => !r.isFinal) {
                  case Move(name) =>
                    ZIO
                      .fromOption(workflowsMap.get(name))
                      .orElseFail(s"No '$name' workflow")
                      .flatMap(_.process(part))
                  case _ => ZIO.dieMessage("Final!!!")
                }
                .map(finalDecision => (part, finalDecision))
            }
            .map {
              _.collect { case (part, RuleDecision.Accepted) => part }
                .map(_.ratingsSum)
                .sum
            }
          _ <- printLine(s"Sum of ratings numbers for all accepted parts is $sum")
        yield (sum, 0L)
      }
