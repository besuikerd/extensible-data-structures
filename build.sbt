name := "extensible-data-structures"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.6.7",
  "org.typelevel" %% "cats-core" % "1.0.1",
  "com.iravid" %% "play-json-cats" % "1.0.0",

  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)