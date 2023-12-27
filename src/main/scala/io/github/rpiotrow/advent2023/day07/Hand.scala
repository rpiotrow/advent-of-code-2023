package io.github.rpiotrow.advent2023.day07

import io.github.rpiotrow.advent2023.Input.parseInt
import io.github.rpiotrow.advent2023.day07.Card.*
import zio.{IO, ZIO}

case class Hand(cards: (Card, Card, Card, Card, Card), bid: Long):
  def withJokers: IO[String, Hand] =
    Hand.fromCards(
      cardList.map {
        case 🂻   => 🃏
        case card => card
      },
      bid
    )
  def handType: HandType =
    cardList.groupBy(identity).map { case (c, list) => c -> list.length }.toList.sorted match
      case (_, 5) :: Nil => HandType.FiveKind

      case (🃏, 4) :: (_, 1) :: Nil => HandType.FiveKind
      case (_, 4) :: (🃏, 1) :: Nil => HandType.FiveKind
      case (_, 4) :: (_, 1) :: Nil  => HandType.FourKind

      case (🃏, 3) :: (_, 2) :: Nil => HandType.FiveKind
      case (_, 3) :: (🃏, 2) :: Nil => HandType.FiveKind
      case (_, 3) :: (_, 2) :: Nil  => HandType.FullHouse

      case (🃏, 3) :: (_, 1) :: (_, 1) :: Nil => HandType.FourKind
      case (_, 3) :: (_, 1) :: (🃏, 1) :: Nil => HandType.FourKind
      case (_, 3) :: (_, 1) :: (_, 1) :: Nil  => HandType.ThreeKind

      case (_, 2) :: (_, 2) :: (🃏, 1) :: Nil => HandType.FullHouse
      case (_, 2) :: (🃏, 2) :: (_, 1) :: Nil => HandType.FourKind
      case (_, 2) :: (_, 2) :: (_, 1) :: Nil  => HandType.TwoPair

      case (🃏, 2) :: (_, 1) :: (_, 1) :: (_, 1) :: Nil => HandType.ThreeKind
      case (_, 2) :: (_, 1) :: (_, 1) :: (🃏, 1) :: Nil => HandType.ThreeKind
      case (_, 2) :: (_, 1) :: (_, 1) :: (_, 1) :: Nil  => HandType.OnePair

      case (_, 1) :: (_, 1) :: (_, 1) :: (_, 1) :: (🃏, 1) :: Nil => HandType.OnePair
      case (_, 1) :: (_, 1) :: (_, 1) :: (_, 1) :: (_, 1) :: Nil  => HandType.HighCard
      case s                                                      => throw new RuntimeException(s"That is not possible: $s")
  private given Ordering[(Card, Int)] = Ordering.by[(Card, Int), Int](_._2).reverse.orElseBy(_._1)(Ordering[Card].reverse)
  private def cardList: Seq[Card]     = List(cards._1, cards._2, cards._3, cards._4, cards._5)

object Hand:
  def fromLine(line: String): IO[String, Hand] =
    line match
      case s"$handString $bidString" =>
        for
          cards <- ZIO.foreach(handString)(Card.fromChar)
          bid   <- parseInt(bidString)
          hand  <- fromCards(cards.toList, bid)
        yield hand
      case _ => ZIO.dieMessage(s"Cannot parse hand and bid $line")
  extension (hands: List[Hand])
    def sortAndSumWinnings: Long =
      hands.sorted.zipWithIndex.map { case (hand, index) => hand.bid * (index + 1) }.sum
  private def fromCards(cards: Seq[Card], bid: Long): IO[String, Hand] =
    cards match
      case c1 :: c2 :: c3 :: c4 :: c5 :: Nil =>
        ZIO.succeed(new Hand((c1, c2, c3, c4, c5), bid))
      case _ =>
        ZIO.dieMessage(s"Hand needs 5 cards (got '$cards' instead)!")
  private given Ordering[Hand] = Ordering.by[Hand, HandType](_.handType).orElseBy(_.cards)
