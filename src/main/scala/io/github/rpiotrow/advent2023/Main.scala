package io.github.rpiotrow.advent2023

import zio.*
import zio.cli.ZIOCliDefault

type SolutionError = String | java.io.IOException
type SolutionResult = Long | String
type Solution = ZIO[Any, SolutionError, (SolutionResult, SolutionResult)]

private val days: Map[Int, Solution] = Map()

object Main extends ZIOCliDefault:

  private def solution(day: Int) =
    for
      _ <- Console.printLine(s"Day: $day")
      _ <- days.getOrElse(day, ZIO.fail("There is no such day!!!"))
    yield ()

  val cliApp = CliConfig.cliApp {
    case Some(day) => solution(day.toInt)
    case None      => ZIO.foreach(days.keys)(solution)
  }
