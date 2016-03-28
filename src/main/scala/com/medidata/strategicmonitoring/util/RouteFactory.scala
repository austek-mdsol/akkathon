package com.medidata.strategicmonitoring.util

import java.util.ServiceLoader

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.medidata.strategicmonitoring.api.Routable

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._

object RouteFactory {
  def aggregateRoutes(actorSystem: ActorSystem): Route = {
    val routesList = new ListBuffer[Route]()

    val routableServices: ServiceLoader[Routable] = ServiceLoader.load(classOf[Routable])
    routableServices.iterator() foreach { routableService =>
      routesList += routableService.getRoutes(actorSystem)
    }

    routesList.reduceLeft(_ ~ _)
  }
}
