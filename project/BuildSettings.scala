import sbt.Keys._
import sbt._

object BuildSettings {
  val VERSION = "0.1-SNAPSHOT"

  lazy val basicSettings = Seq(
    version := VERSION,
    homepage := Some(new URL("https://github.com/mdsol/strategic_monitoring")),
    organization := "mdsol",
    organizationHomepage := Some(new URL("http://mdsol.com")),
    description := "Strategic Monitoring",
    scalaVersion := "2.11.7",
    exportJars := true,
    scalacOptions := Seq(
      "-encoding", "utf8",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-target:jvm-1.8",
      "-language:_",
      "-Xlog-reflective-calls",
      "-Ywarn-adapted-args"
    )
  )

  lazy val issueManagementModuleSettings =
    basicSettings ++
      Seq(
        parallelExecution in Test := false,
        testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
      )
}
