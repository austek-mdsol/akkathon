package com.medidata.strategicmonitoring.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.medidata.strategicmonitoring.api.Routable
import com.medidata.strategicmonitoring.util.{ResourceFinder, ResourceLoader}

import scala.collection.Iterator
import scala.collection.mutable.ListBuffer


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
