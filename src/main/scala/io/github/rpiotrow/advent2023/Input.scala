package io.github.rpiotrow.advent2023

import zio.{IO, ZIO}
import zio.stream.{ZPipeline, ZStream}

import java.io.IOException

object Input:
  def readLines(inputFileName: String): ZStream[Any, String, String] =
    readStrings(inputFileName).via(ZPipeline.splitLines)

  def readStrings(inputFileName: String): ZStream[Any, String, String] =
    val inputStream    = Option(this.getClass.getClassLoader.getResourceAsStream(inputFileName))
    val zioInputStream = ZIO.fromOption(inputStream).orElseFail(new IOException("input file not found"))
    ZStream
      .fromInputStreamZIO(zioInputStream)
      .via(ZPipeline.utf8Decode)
      .mapError(_.getMessage)

  def parseInt(s: String): IO[String, Int] =
    ZIO.attempt(s.toInt).mapError(ex => s"Cannot parse '$s' as Int: ${ex.getMessage}")

  def parseInt(s: String, errorMessage: Throwable => String): IO[String, Int] =
    ZIO.attempt(s.toInt).mapError(errorMessage)

  def parseLong(s: String, errorMessage: Throwable => String): IO[String, Long] =
    ZIO.attempt(s.toLong).mapError(errorMessage)
