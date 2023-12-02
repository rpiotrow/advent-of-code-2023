package io.github.rpiotrow.advent2023

import zio.cli.*
import zio.cli.HelpDoc.Span.text

object CliConfig:

  private val dayFlag: Options[Option[BigInt]] =
    Options.integer("day").alias("d").optional ?? "Select single day, all days are run if not specified"

  private val aoc2023: Command[Option[BigInt]] = Command("advent-of-code-2023", dayFlag, Args.none)

  val cliApp = CliApp.make(
    name = "advent-of-code-2023",
    version = "0.0.1",
    summary = text("Run Advent of Code 2023 solutions for selected day or for all days"),
    command = aoc2023
  )
