package io.github.rpiotrow.advent2023.day03

case class SchematicLine(previousLine: Option[Vector[Field]], line: Vector[Field], nextLine: Option[Vector[Field]]):

  def sumPartNumbersWithSymbol: Long =
    line.zipWithIndex
      .foldLeft(Accumulator.empty) { case (acc, (field, index)) =>
        field match
          case Field.Digit(d) =>
            acc.ongoingNumber match
              case Some(value) if acc.ongoingNumberAdjacentToSymbol.contains(true) =>
                acc.copy(ongoingNumber = Some(value * 10 + d))
              case Some(value) =>
                val adjacentToSymbol = linePositionNeighbours(index).contains(Field.Symbol)
                acc.copy(ongoingNumber = Some(value * 10 + d), ongoingNumberAdjacentToSymbol = Some(adjacentToSymbol))
              case None =>
                val adjacentToSymbol = linePositionNeighbours(index).contains(Field.Symbol)
                acc.copy(ongoingNumber = Some(d), ongoingNumberAdjacentToSymbol = Some(adjacentToSymbol))
          case _ =>
            acc.ongoingNumber match
              case Some(value) if acc.ongoingNumberAdjacentToSymbol.contains(true) =>
                Accumulator(None, None, value :: acc.numbers)
              case Some(_) =>
                Accumulator(None, None, acc.numbers)
              case None =>
                acc
      }
      .numbers
      .sum

  private case class Accumulator(ongoingNumber: Option[Long], ongoingNumberAdjacentToSymbol: Option[Boolean], numbers: List[Long])
  private object Accumulator:
    val empty: Accumulator = Accumulator(None, None, List.empty)

  private def linePositionNeighbours(position: Int): List[Field] =
    (rowNeighbours(position, previousLine) ++ rowNeighbours(position, line) ++ rowNeighbours(position, nextLine)).collect {
      case Some(e) => e
    }

  private def rowNeighbours(position: Int, fields: Vector[Field]): List[Option[Field]] =
    List(
      Option.when(position - 1 >= 0)(fields(position - 1)),
      Some(fields(position)),
      Option.when(position + 1 < fields.length)(fields(position + 1))
    )
  private def rowNeighbours(position: Int, fields: Option[Vector[Field]]): List[Option[Field]] =
    fields.map(line => rowNeighbours(position, line)).getOrElse(List.empty)
