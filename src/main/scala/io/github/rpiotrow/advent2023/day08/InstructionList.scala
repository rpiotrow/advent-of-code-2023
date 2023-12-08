package io.github.rpiotrow.advent2023.day08

import io.github.rpiotrow.advent2023.day08.InstructionList.Instruction
import zio.{IO, ZIO}

class InstructionList(instructions: Vector[Instruction]):
  def instruction(index: Int): Instruction = instructions(index % instructions.length)

object InstructionList:
  enum Instruction:
    case Left, Right

  def fromLine(line: String): IO[String, InstructionList] =
    ZIO
      .foreach(line) {
        case 'L' => ZIO.succeed(Instruction.Left)
        case 'R' => ZIO.succeed(Instruction.Right)
        case e   => ZIO.dieMessage(s"'$e' is not a valid instruction")
      }
      .map(l => InstructionList(l.toVector))
