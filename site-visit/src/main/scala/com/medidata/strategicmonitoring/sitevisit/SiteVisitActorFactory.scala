package com.medidata.strategicmonitoring.sitevisit

import akka.actor.{ActorRef, Props}
import akka.pattern.ask
import com.medidata.strategicmonitoring.api.{ImplicitTimeoutActor, Key}
import com.medidata.strategicmonitoring.maudit.MAuditActor
import com.twitter.storehaus.cache.MutableLRUCache
import org.slf4j.LoggerFactory

import scala.concurrent.Await


//WE WANT ONE OF THESE LOCAL TO EACH AKKA CLUSTER NODE
//AND NOT ASYNC
//CALLS TO ACTORS SHOULD BE ASYNC
//ALSO A SUPERVISOR
//SINGLETON, needs to be a ClusterSingleton in future
object SiteVisitActorFactory extends ImplicitTimeoutActor {

  val logger = LoggerFactory.getLogger(getClass())

  //TODO read size from config file, calculate dynamically based on memory etc etc
  val cache = MutableLRUCache[Key, ActorRef](10000)

  override def preStart() = {

    val mAuditActorActorRef = context.actorOf(Props[MAuditActor], name = "MAuditActor")
    logger.info("SiteVisitActorFactory, mAuditActorActorRef=" + mAuditActorActorRef.path)
  }

  def receive = {

    case newSiteVisit: NewSiteVisit =>

      logger.info("SiteVisitActorFactory::receive, newSiteVisit=" + newSiteVisit);

      val actorRef = context.actorOf(Props[SiteVisitActor], name = "SiteVisitActor=" + newSiteVisit.keyAndVersion.key.toString())
      cache += (newSiteVisit.keyAndVersion.key -> actorRef)
      sender ! actorRef

    case key: Key =>

      //this means were doing a get ie. were looking for an existing SiteVisitActor
      //MA not be in cache yet
      logger.info("SiteVisitActorFactory::receive, key=" + key);

      if (cache.contains(key)) {

        logger.info("SiteVisitActorFactory::receive, cache hit");
        sender ! cache.get(key).get
      } else {

        logger.info("SiteVisitActorFactory::receive, cache miss, reading state of SiteVisitActor=" + key + " from DB");

        val actorRef = context.actorOf(Props[SiteVisitActor], name = "SiteVisitActor=" + key.toString())

        //this will cause actor to read latest version / state into itself
        //were not interested in the result, just want to wait till its loaded...
        Await.result((actorRef ? key), timeout.duration)

        cache += (key -> actorRef)
        logger.info("SiteVisitActorFactory::receive, actorRef.path.name=" + actorRef.path.name)
        sender ! actorRef
      }

    case _ =>
      logger.error("error");
  } //receive
} //SiteVisitFactory
