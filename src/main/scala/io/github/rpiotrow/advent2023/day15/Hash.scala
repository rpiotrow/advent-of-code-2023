package io.github.rpiotrow.advent2023.day15

object Hash:
  def valueFor(s: String): Int =
    s.foldLeft(0) { case (acc, char) => ((acc + char) * 17) % 256 }
