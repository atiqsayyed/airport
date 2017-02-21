name := """airport"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "mysql" % "mysql-connector-java" % "5.1.24",
  "com.typesafe.slick" %% "slick" % "3.2.0-M1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "org.mockito" % "mockito-all" % "1.9.5" % Test,
  "org.jsoup" % "jsoup" % "1.7.2" % Test
)

