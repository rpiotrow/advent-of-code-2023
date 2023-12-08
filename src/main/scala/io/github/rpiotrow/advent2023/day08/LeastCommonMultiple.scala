package io.github.rpiotrow.advent2023.day08

import scala.annotation.tailrec

object LeastCommonMultiple:
  def lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)
  @tailrec
  private def gcd(a: Long, b: Long): Long =
    if b == 0 then a else gcd(b, a % b)
