package io.github.rpiotrow.advent2023.day04

class Counter(map: Map[Int, Int]):
  def numberOfCards(id: Int): Int = map.getOrElse(id, 0)

  def addCards(id: Int, amount: Int, length: Int): Counter =
    val idList = Range.inclusive(id + 1, id + length).toList
    val newMap = idList.foldLeft(map.updatedWith(id) { count => Some(count.getOrElse(0) + 1) }) { (map, id) =>
      map.updatedWith(id) { count => Some(count.getOrElse(0) + amount) }
    }
    Counter(newMap)

  def sum: Long = map.values.sum
