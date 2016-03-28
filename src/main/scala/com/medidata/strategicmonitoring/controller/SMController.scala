package com.medidata.strategicmonitoring.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.medidata.strategicmonitoring.api.{ImplicitTimeout, Routable}
import org.slf4j.LoggerFactory

class SMController extends Routable with ImplicitTimeout {

  val logger = LoggerFactory.getLogger(getClass)

  override def title: String = "Strategic Monitoring Controller"

  override def getRoutes(actorSystem: ActorSystem): Route = {
    logger.info("SMController::getRoutes called")

    pathSingleSlash {
      get {
        complete {
          "Strategic Monitoring"
        }
      }
    }
  }
}
