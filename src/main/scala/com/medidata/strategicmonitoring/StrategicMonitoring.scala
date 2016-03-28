package com.medidata.strategicmonitoring

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.RouteResult.route2HandlerFlow
import akka.stream.ActorMaterializer
import com.medidata.strategicmonitoring.controller.SMController
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

//boilerplate, not much to see here
object StrategicMonitoring extends App {

  val logger = LoggerFactory.getLogger(getClass())

  val config = ConfigFactory.load()
  implicit val system = ActorSystem("StrategicMonitoring", config)
  implicit val materializer = ActorMaterializer()

  import system.dispatcher

  val bindingFuture = Http().bindAndHandle(SMController.aggregateRoutes(system), "localhost", 8080)
  logger.info("StrategicMonitoring online at http://localhost:8080\nPress RETURN to stop...")
  scala.io.StdIn.readLine() // for the future transformations
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ ⇒ system.terminate()) // and shutdown when done
}
