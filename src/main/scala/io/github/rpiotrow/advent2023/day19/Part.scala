package io.github.rpiotrow.advent2023.day19

import io.github.rpiotrow.advent2023.Input.parseInt
import zio.IO

case class Part(extremelyCoolLooking: Int, musical: Int, aerodynamic: Int, shiny: Int):
  def ratingsSum: Int = extremelyCoolLooking + musical + aerodynamic + shiny

object Part:
  def parse(s: String): IO[String, Part] =
    s match
      case s"{x=$extremelyCoolLookingString,m=$musicalString,a=$aerodynamicString,s=$shinyString}" =>
        for
          extremelyCoolLooking <- parseInt(extremelyCoolLookingString)
          musical              <- parseInt(musicalString)
          aerodynamic          <- parseInt(aerodynamicString)
          shiny                <- parseInt(shinyString)
        yield Part(extremelyCoolLooking, musical, aerodynamic, shiny)
