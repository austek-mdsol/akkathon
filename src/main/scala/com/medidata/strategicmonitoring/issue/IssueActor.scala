package com.medidata.strategicmonitoring.issue

import scala.concurrent.Await
import org.slf4j.LoggerFactory
import akka.actor.ActorRef
import akka.actor.actorRef2Scala
import com.mediddata.strategicmonioring.strategicmonitoringapi.ImplicitTimeoutActor
import com.mediddata.strategicmonioring.strategicmonitoringapi.Key
import com.mediddata.strategicmonioring.strategicmonitoringapi.KeyAndVersion

//MUTABLE
//CONSTRUCTOR CALLED ON POST / CREATE
class IssueActor extends ImplicitTimeoutActor {

  val logger = LoggerFactory.getLogger(getClass())
  var existingIssue: ExistingIssue = null;
  var mAuditActorActorRef: ActorRef = null

  override def preStart() = {

    mAuditActorActorRef = Await.result(context.actorSelection("/user/IssueActorFactory/MAuditActor").resolveOne(), timeout.duration)
  }

  def receive = {

    //CREATE
    case newIssue: NewIssue =>

      logger.info("IssueActor::receive called, newIssue=" + newIssue)
      
    //READ
    case key: Key =>

      logger.info("IssueActor::receive called, key=" + key)
      
    //READ
    //keyAndVersion doesn't update this Actor, as this actor represent current version, just read data and return
    case keyAndVersion: KeyAndVersion =>

      logger.info("IssueActor::receive called, keyAndVersion=" + keyAndVersion)

    //UPDATE
    case myExistingIssue: ExistingIssue =>

      logger.info("IssueActor::receive called, myExistingIssue=" + myExistingIssue)

    case _ =>

      logger.error("error")
  } //receive
} //IssueActor