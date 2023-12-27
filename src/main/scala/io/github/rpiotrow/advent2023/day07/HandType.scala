package io.github.rpiotrow.advent2023.day07

enum HandType:
  case HighCard
  case OnePair
  case TwoPair
  case ThreeKind
  case FullHouse
  case FourKind
  case FiveKind

object HandType:
  given Ordering[HandType] = Ordering.by[HandType, Int](_.ordinal)
