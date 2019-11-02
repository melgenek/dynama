name := "dynama"

version := "0.1"

scalaVersion := "2.12.8"

val mapper = project
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "software.amazon.awssdk" % "dynamodb" % "2.7.5",
      "com.gu" %% "scanamo" % "1.0.0-M8",

      "com.typesafe.slick" %% "slick" % "3.3.2",
      "org.scalatest" %% "scalatest" % "3.0.8" % Test
    )
  )

val example = project.dependsOn(mapper)
  .settings(
    libraryDependencies ++= Seq(
      "org.slf4j" % "slf4j-simple" % "1.7.26"
    )
  )

val root = project.in(file("."))
  .aggregate(mapper, example)
