package io.github.rpiotrow.advent2023.day11

case class Galaxy(x: Long, y: Long):
  def distanceToOther(galaxy: Galaxy): Long =
    Math.abs(this.x - galaxy.x) + Math.abs(this.y - galaxy.y)
