package com.medidata.strategicmonitoring.controller

import java.util.ServiceLoader

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.medidata.strategicmonitoring.api.Routable
import com.medidata.strategicmonitoring.util.{ResourceFinder, ResourceLoader}

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

object SMController extends ResourceFinder with ResourceLoader {
  def aggregateRoutes(actorSystem: ActorSystem): Route = {
    val routesList = new ListBuffer[Route]()

    routesList += {
      pathSingleSlash {
        get {
          complete {
            "Strategic Monitoring"
          }
        }
      }
    }

    val routableServices: ServiceLoader[Routable] = ServiceLoader.load(classOf[Routable])
    routableServices.iterator() foreach { routableService =>
      routesList += routableService.getRoutes(actorSystem)
    }

    routesList.reduceLeft(_ ~ _)
  }
}
