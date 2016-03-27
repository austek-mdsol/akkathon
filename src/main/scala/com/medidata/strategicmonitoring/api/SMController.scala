package com.medidata.strategicmonitoring.api

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.collection.mutable.ListBuffer
import com.medidata.strategicmonitoring.util.{ResourceFinder, ResourceLoader}

import scala.collection.Iterator

object SMController extends ResourceFinder with ResourceLoader {
  def aggregateRoutes(actorSystem: ActorSystem): Route = {
    val routeResources: Iterator[String] = getResource("META-INF/routes")
    val routesList = new ListBuffer[Route]()

    for (routeClass <- routeResources) {
      val someTrait: Routable = getCompanionObject[Routable](routeClass.toString)
      routesList += someTrait.getRoutes(actorSystem)
    }
    routesList.reduceLeft(_ ~ _)
  }
}
