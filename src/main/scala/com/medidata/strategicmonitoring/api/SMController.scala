package com.medidata.strategicmonitoring.api

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import org.reflections.Reflections

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import scala.reflect.runtime.universe

object SMController {
  def aggregateRoutes(actorSystem: ActorSystem): Route = {
    val runtimeMirror = universe.runtimeMirror(getClass.getClassLoader)
    val reflections = new Reflections("com.medidata.strategicmonitoring")
    val subclasses = reflections.getSubTypesOf(classOf[Routable])
    val routesList = new ListBuffer[Route]()

    for (i <- subclasses) {
      val module = runtimeMirror.staticModule(i.getName)
      val obj = runtimeMirror.reflectModule(module)
      val someTrait: Routable = obj.instance.asInstanceOf[Routable]
      routesList += someTrait.getRoutes(actorSystem)
    }
    routesList.reduceLeft(_ ~ _)
  }
}
