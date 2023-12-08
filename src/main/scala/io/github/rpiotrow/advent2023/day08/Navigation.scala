package io.github.rpiotrow.advent2023.day08

import io.github.rpiotrow.advent2023.day08.InstructionList.Instruction
import zio.{IO, ZIO}
import zio.stream.ZStream

case class Navigation(instructionList: InstructionList, nodeMap: Map[String, Node]):
  def simulateSteps(startNode: String, endCondition: String => Boolean): ZIO[Any, String, Long] =
    ZStream
      .range(0, Int.MaxValue)
      .mapAccumZIO(startNode) { case (nodeId, step) =>
        ZIO.fromOption(nodeMap.get(nodeId)).orElseFail("no $nodeId in map").map { node =>
          val newNode = instructionList.instruction(step) match
            case Instruction.Left  => node.leftNode
            case Instruction.Right => node.rightNode
          (newNode, (newNode, step))
        }
      }
      .filter { case (nodeId, _) => endCondition(nodeId) }
      .map { case (_, step) => (step + 1).toLong }
      .runHead
      .flatMap(r => ZIO.fromOption(r).orElseFail("No result?"))

object Navigation:
  def parse(firstLine: Option[String], stream: ZStream[Any, String, String]): IO[String, Navigation] =
    for
      firstLine       <- ZIO.fromOption(firstLine).orElseFail("No instruction list?")
      instructionList <- InstructionList.fromLine(firstLine)
      nodeMap         <- Node.makeNodeMap(stream)
    yield Navigation(instructionList, nodeMap)
