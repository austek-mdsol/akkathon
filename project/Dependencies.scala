import sbt._

object Dependencies {
  def compile(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")

  def provided(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")

  def test(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")

  def runtime(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")

  def container(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

  val akkaVersion = "2.4.2"

  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaHttp = "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion
  val akkaHttpSprayJson = "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaVersion
  val storeHaus = "com.twitter" %% "storehaus-cache" % "0.12.0"
  val scalaReflect = "org.scala-lang" % "scala-reflect" % "2.11.7"
  val slf4jApi = "org.slf4j" % "slf4j-api" % "1.7.5"
  val slf4jSimple  = "org.slf4j" % "slf4j-simple" % "1.7.5"
}

