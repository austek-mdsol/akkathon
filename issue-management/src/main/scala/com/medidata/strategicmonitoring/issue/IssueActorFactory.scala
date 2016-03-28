package com.medidata.strategicmonitoring.issue

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
object IssueActorFactory extends ImplicitTimeoutActor {

  val logger = LoggerFactory.getLogger(getClass())

  //TODO read size from config file, calculate dynamically based on memory etc etc
  val cache = MutableLRUCache[Key, ActorRef](10000)

  override def preStart() = {

    val mAuditActorActorRef = context.actorOf(Props[MAuditActor], name = "MAuditActor")
    logger.info("IssueActorFactory, mAuditActorActorRef=" + mAuditActorActorRef.path)
  }

  def receive = {

    case newIssue: NewIssue =>

      logger.info("IssueActorFactory::receive, newIssue=" + newIssue)

      val actorRef = context.actorOf(Props[IssueActor], name = "IssueActor=" + newIssue.keyAndVersion.key.toString())
      cache += (newIssue.keyAndVersion.key -> actorRef)
      sender ! actorRef
      //newIssue

    case key: Key =>

      //this means were doing a get ie. were looking for an existing IssueActor
      //MA not be in cache yet
      logger.info("IssueActorFactory::receive, key=" + key);

      if (cache.contains(key)) {

        logger.info("IssueActorFactory::receive, cache hit");
        sender ! cache.get(key).get
      } else {

        logger.info("IssueActorFactory::receive, cache miss, reading state of IssueActor=" + key + " from DB");

        val actorRef = context.actorOf(Props[IssueActor], name = "IssueActor=" + key.toString())

        //this will cause actor to read latest version / state into itself
        //were not interested in the result, just want to wait till its loaded...
        Await.result((actorRef ? key), timeout.duration)

        cache += (key -> actorRef)
        logger.info("IssueActorFactory::receive, actorRef.path.name=" + actorRef.path.name)
        sender ! actorRef
      }

    case _ =>
      logger.error("error");
  } //receive
} //IssueFactory
