
scalaVersion := "2.13.8"

name := "scala-dev-mooc-2023-03"
organization := "ru.otus"
version := "1.0"


libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"

libraryDependencies ++= Dependencies.zio
libraryDependencies ++= Dependencies.zioConfig
libraryDependencies ++= Dependencies.quill
libraryDependencies ++= Dependencies.testContainers
libraryDependencies += Dependencies.postgres
libraryDependencies += Dependencies.scalaTest
libraryDependencies ++= Dependencies.circe
libraryDependencies += Dependencies.liquibase
libraryDependencies += Dependencies.zioHttp
libraryDependencies += Dependencies.logback
libraryDependencies ++= Dependencies.cats
libraryDependencies ++= Dependencies.fs2
libraryDependencies ++= Dependencies.http4s
libraryDependencies ++= Dependencies.akkaContainers

scalacOptions += "-Ymacro-annotations"

libraryDependencies += "org.specs2"    %% "specs2-core" % "4.10.5" % Test
libraryDependencies += "org.typelevel" %% "cats-effect-testing-specs2" % "1.1.1" % Test

testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
