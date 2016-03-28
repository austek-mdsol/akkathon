import sbt.Keys._
import sbt._

object Build extends Build {

  import BuildSettings._
  import Dependencies._

  // configure prompt to show current project
  override lazy val settings = super.settings :+ {
    shellPrompt := { s => Project.extract(s).currentProject.id + " > " }
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Root Project
  // -------------------------------------------------------------------------------------------------------------------

  lazy val strategicMonitoring = Project("strategicMonitoring", file("."))
    .aggregate(utils)
    .settings(basicSettings)
    .settings(libraryDependencies ++=
      compile(
        akkaActor, akkaHttp, scalaReflect
      )
    )
    .dependsOn(smCore)


  // -------------------------------------------------------------------------------------------------------------------
  // Modules
  // -------------------------------------------------------------------------------------------------------------------

  lazy val utils = Project("utils", file("utils"))

  lazy val smCore = Project("smCore", file("sm-core"))
    .settings(basicSettings: _*)
    .settings(libraryDependencies ++=
      compile(
        akkaHttpSprayJson, akkaSlf4j
      )
    )

  lazy val issueManagement = Project("issueManagement", file("issue-management"))
    .settings(issueManagementModuleSettings: _*)
    .dependsOn(smCore)
    .settings(libraryDependencies ++=
      compile(storeHaus)
    )

  lazy val siteVisit = Project("siteVisit", file("site-visit"))
    .settings(basicSettings: _*)
    .dependsOn(smCore)
    .settings(libraryDependencies ++=
      compile(storeHaus)
    )
}
