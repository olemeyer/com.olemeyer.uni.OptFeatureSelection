name := """scala-dci"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

lazy val sparkVersion = "1.6.0"
lazy val spark = "org.apache.spark"

libraryDependencies ++= Seq(
  spark %% "spark-core" % sparkVersion,
  spark %% "spark-graphx" % sparkVersion
)
