package com.medidata.strategicmonitoring.api

import akka.actor.ActorSystem
import akka.http.scaladsl.server._

trait Routable {
 def getRoutes(actorSystem: ActorSystem): Route
}
