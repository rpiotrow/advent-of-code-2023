package io.github.rpiotrow.advent2023.day19

import zio.stream.ZStream
import zio.{IO, ZIO}

class Workflow(val name: String, rules: List[Rule]):
  def process(part: Part): IO[String, RuleDecision] =
    ZStream
      .fromIterable(rules)
      .map(_.conforms(part))
      .collectSome
      .runHead
      .flatMap { e =>
        ZIO.fromOption(e).orElseFail(s"Workflow '$name' did not give decision for part $part")
      }

object Workflow:
  def parse(s: String): IO[String, Workflow] =
    s match
      case s"$name{$rulesString}" =>
        ZIO.foreach(rulesString.split(","))(Rule.parse).map { rules =>
          new Workflow(name, rules.toList)
        }
