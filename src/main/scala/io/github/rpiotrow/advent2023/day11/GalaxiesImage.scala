package io.github.rpiotrow.advent2023.day11

import zio.IO
import zio.stream.ZStream

import scala.annotation.tailrec

class GalaxiesImage(galaxies: List[Galaxy]):
  def shortestPathsLengthBetweenAll: Long = GalaxiesImage.shortestPathsLengthBetweenAll(0, galaxies)

  def expand(amount: Long): GalaxiesImage = this.expandHorizontally(amount, 0).expandVertically(amount, 0)

  @tailrec
  private def expandHorizontally(amount: Long, index: Long): GalaxiesImage =
    galaxies.find(_.x > index) match
      case Some(_) =>
        galaxies.find(_.x == index) match
          case Some(_) =>
            this.expandHorizontally(amount, index + 1)
          case None =>
            GalaxiesImage(galaxies.map { galaxy =>
              if galaxy.x > index then galaxy.copy(x = galaxy.x + amount) else galaxy
            }).expandHorizontally(amount, index + amount + 1)
      case None => this

  @tailrec
  private def expandVertically(amount: Long, index: Long): GalaxiesImage =
    galaxies.find(_.y > index) match
      case Some(_) =>
        galaxies.find(_.y == index) match
          case Some(_) =>
            this.expandVertically(amount, index + 1)
          case None =>
            GalaxiesImage(galaxies.map { galaxy =>
              if galaxy.y > index then galaxy.copy(y = galaxy.y + amount) else galaxy
            }).expandVertically(amount, index + amount + 1)
      case None => this

object GalaxiesImage:
  def parse(stream: ZStream[Any, String, String]): IO[String, GalaxiesImage] =
    stream.zipWithIndex
      .map { case (row, y) => row.zipWithIndex.collect { case ('#', x) => Galaxy(x, y) } }
      .runCollect
      .map(_.toList.flatten)
      .map(GalaxiesImage(_))

  @tailrec
  private def shortestPathsLengthBetweenAll(acc: Long, galaxies: List[Galaxy]): Long =
    galaxies match
      case Nil      => acc
      case _ :: Nil => acc
      case galaxy :: otherGalaxies =>
        shortestPathsLengthBetweenAll(
          acc + otherGalaxies.foldLeft(0L) { case (galaxyShortestPathsLength, otherGalaxy) =>
            galaxyShortestPathsLength + galaxy.distanceToOther(otherGalaxy)
          },
          otherGalaxies
        )
