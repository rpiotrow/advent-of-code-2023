package io.github.rpiotrow.advent2023.day19

import io.github.rpiotrow.advent2023.Input.parseInt
import io.github.rpiotrow.advent2023.day19.Rule.ConditionRelation
import io.github.rpiotrow.advent2023.day19.RuleDecision.*
import zio.{IO, ZIO}

enum Rule:
  case Condition(category: PartCategory, relation: ConditionRelation, value: Int, ruleDecision: RuleDecision)
  case Direct(ruleDecision: RuleDecision)

  def conforms(part: Part): Option[RuleDecision] =
    this match
      case Rule.Condition(category, relation, value, ruleDecision) =>
        category match
          case PartCategory.ExtremelyCoolLooking =>
            relation match
              case ConditionRelation.LessThen => Option.when(part.extremelyCoolLooking < value)(ruleDecision)
              case ConditionRelation.MoreThan => Option.when(part.extremelyCoolLooking > value)(ruleDecision)
          case PartCategory.Musical =>
            relation match
              case ConditionRelation.LessThen => Option.when(part.musical < value)(ruleDecision)
              case ConditionRelation.MoreThan => Option.when(part.musical > value)(ruleDecision)
          case PartCategory.Aerodynamic =>
            relation match
              case ConditionRelation.LessThen => Option.when(part.aerodynamic < value)(ruleDecision)
              case ConditionRelation.MoreThan => Option.when(part.aerodynamic > value)(ruleDecision)
          case PartCategory.Shiny =>
            relation match
              case ConditionRelation.LessThen => Option.when(part.shiny < value)(ruleDecision)
              case ConditionRelation.MoreThan => Option.when(part.shiny > value)(ruleDecision)
      case Rule.Direct(ruleDecision) => Some(ruleDecision)

object Rule:
  private val pattern = "([xmas])([><])([0-9]+):([ARa-z]+)".r

  enum ConditionRelation:
    case LessThen, MoreThan
  object ConditionRelation:
    def fromString(s: String): IO[String, ConditionRelation] =
      s match
        case "<" => ZIO.succeed(LessThen)
        case ">" => ZIO.succeed(MoreThan)
        case _   => ZIO.dieMessage(s"Unknown relation $s")

  def parse(s: String): IO[String, Rule] =
    s match
      case "A" => ZIO.succeed(Direct(Accepted))
      case "R" => ZIO.succeed(Direct(Rejected))
      case pattern(categoryString, relationString, valueString, ruleDecisionString) =>
        for
          category <- PartCategory.fromString(categoryString)
          relation <- ConditionRelation.fromString(relationString)
          value    <- parseInt(valueString)
          ruleDecision = RuleDecision.fromString(ruleDecisionString)
        yield Rule.Condition(category, relation, value, ruleDecision)
      case name => ZIO.succeed(Direct(Move(name)))
