package io.github.rpiotrow.advent2023.day19

enum RuleDecision(val isFinal: Boolean):
  case Move(ruleName: String) extends RuleDecision(isFinal = false)
  case Accepted               extends RuleDecision(isFinal = true)
  case Rejected               extends RuleDecision(isFinal = true)

object RuleDecision:
  def fromString(s: String): RuleDecision =
    s match
      case "A"  => Accepted
      case "R"  => Rejected
      case name => Move(name)
