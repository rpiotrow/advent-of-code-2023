package io.github.rpiotrow.advent2023

import io.github.rpiotrow.advent2023.day01.Trebuchet
import io.github.rpiotrow.advent2023.day02.CubeConundrum
import io.github.rpiotrow.advent2023.day03.GearRatios
import io.github.rpiotrow.advent2023.day04.Scratchcards
import io.github.rpiotrow.advent2023.day05.IfYouGiveASeedAFertilizer
import io.github.rpiotrow.advent2023.day06.WaitForIt
import io.github.rpiotrow.advent2023.day07.CamelCards
import io.github.rpiotrow.advent2023.day08.HauntedWasteland
import io.github.rpiotrow.advent2023.day09.MirageMaintenance
import io.github.rpiotrow.advent2023.day10.PipeMaze
import io.github.rpiotrow.advent2023.day11.CosmicExpansion
import io.github.rpiotrow.advent2023.day14.ParabolicReflectorDish
import io.github.rpiotrow.advent2023.day15.LensLibrary
import io.github.rpiotrow.advent2023.day16.TheFloorWillBeLava
import io.github.rpiotrow.advent2023.day19.Aplenty
import zio.*
import zio.cli.ZIOCliDefault

type SolutionError  = String | java.io.IOException
type SolutionResult = Long | String
type Solution       = ZIO[Any, SolutionError, (SolutionResult, SolutionResult)]

private val days: Map[Int, Solution] =
  Map(
    1 -> Trebuchet.solution,
    2 -> CubeConundrum.solution,
    3 -> GearRatios.solution,
    4 -> Scratchcards.solution,
    5 -> IfYouGiveASeedAFertilizer.solution,
    6 -> WaitForIt.solution,
    7 -> CamelCards.solution,
    8 -> HauntedWasteland.solution,
    9 -> MirageMaintenance.solution,
    10 -> PipeMaze.solution,
    11 -> CosmicExpansion.solution,
    14 -> ParabolicReflectorDish.solution,
    15 -> LensLibrary.solution,
    16 -> TheFloorWillBeLava.solution,
    19 -> Aplenty.solution
  )

object Main extends ZIOCliDefault:

  val cliApp = CliConfig.cliApp {
    case Some(day) => solution(day.toInt)
    case None      => ZIO.foreach(days.keys)(solution)
  }

  private def solution(day: Int) =
    for
      _ <- Console.printLine(s"Day: $day")
      _ <- days.getOrElse(day, ZIO.fail("There is no such day!!!"))
    yield ()
