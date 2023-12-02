import Dependencies.*
import TestDependencies.*

ThisBuild / scalaVersion := "3.3.1"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / organization := "io.github.rpiotrow"

val RegressionConfig = config("regression") extend IntegrationTest

lazy val root = project
  .in(file("."))
  .configs(RegressionConfig)
  .settings(
    name := "advent-of-code-2023",
    libraryDependencies ++= Seq(zio, zioCli, zioStreams, zioTest, zioTestSbt),
    inConfig(RegressionConfig)(Defaults.testSettings),
    RegressionConfig / classpathConfiguration := Test
  )

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
