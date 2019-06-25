name := "dynama"

version := "0.1"

scalaVersion := "2.12.8"

val mapper = project
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "software.amazon.awssdk" % "dynamodb" % "2.5.65",

      "org.scalatest" %% "scalatest" % "3.0.8" % Test
    )
  )

val example = project.dependsOn(mapper)

val root = project.in(file("."))
  .aggregate(mapper, example)
