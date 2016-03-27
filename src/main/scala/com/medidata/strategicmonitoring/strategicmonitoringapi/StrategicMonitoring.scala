package main.scala

import org.slf4j.LoggerFactory
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.RouteResult.route2HandlerFlow
import akka.stream.ActorMaterializer
import com.medidata.strategicmonitoring.issue.IssueController
import com.medidata.strategicmonitoring.sitevisit.SiteVisitController
import akka.http.scaladsl.server.Directives._
import com.medidata.strategicmonitoring.api.SMController

//boilerplate, not much to see here
object StrategicMonitoring extends App {

  val logger = LoggerFactory.getLogger(getClass())

  val config = ConfigFactory.load();
  //implicit val system = ActorSystem("StrategicMonitoring")
  implicit val system = ActorSystem("StrategicMonitoring", config)
  implicit val materializer = ActorMaterializer()

  import system.dispatcher
  
  //TODO figure out how to combine routes
  val bindingFuture = Http().bindAndHandle(SMController.aggregateRoutes(system), "localhost", 8080)
  logger.info("StrategicMonitoring online at http://localhost:8080\nPress RETURN to stop...")
  Console.readLine() // for the future transformations
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ ⇒ system.shutdown()) // and shutdown when done
}
