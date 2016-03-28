package com.medidata.strategicmonitoring.api

import akka.actor.ActorSystem
import akka.http.scaladsl.server._

trait Routable {
 def title: String
 def getRoutes(actorSystem: ActorSystem): Route
}
