package com.medidata.strategicmonitoring.sitevisit

import akka.actor.ActorRef
import com.medidata.strategicmonitoring.api.{ImplicitTimeoutActor, Key, KeyAndVersion}
import org.slf4j.LoggerFactory

import scala.concurrent.Await

//MUTABLE
//CONSTRUCTOR CALLED ON POST / CREATE
class SiteVisitActor extends ImplicitTimeoutActor {

  val logger = LoggerFactory.getLogger(getClass())
  var existingSiteVisit: ExistingSiteVisit = null;
  var mAuditActorActorRef: ActorRef = null

  override def preStart() = {

    mAuditActorActorRef = Await.result(context.actorSelection("/user/SiteVisitActorFactory/MAuditActor").resolveOne(), timeout.duration)
  }

  def receive = {

    //CREATE
    case newSiteVisit: NewSiteVisit =>

      logger.info("SiteVisitActor::receive called, newSiteVisit=" + newSiteVisit)

    //READ
    case key: Key =>

      logger.info("SiteVisitActor::receive called, key=" + key)

    //READ
    //keyAndVersion doesn't update this Actor, as this actor represent current version, just read data and return
    case keyAndVersion: KeyAndVersion =>

      logger.info("SiteVisitActor::receive called, keyAndVersion=" + keyAndVersion)


    //UPDATE
    case myExistingSiteVisit: ExistingSiteVisit =>

      logger.info("SiteVisitActor::receive called, myExistingSiteVisit=" + myExistingSiteVisit)

    case _ =>

      logger.error("error")
  } //receive
} //SiteVisitActor
