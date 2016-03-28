package com.medidata.strategicmonitoring.maudit

import akka.actor.Actor
import org.slf4j.LoggerFactory

//EVOLVE THIS TO USE AKKA PERSISTENCE / A PERSISTENT MAILBOX SO WE DON'T LOSE
//AUDITS
//CALL via ask / ! so ITS 100% ASYNC
//should be stateless, i.e. receive called once per message, just loop on failure and send to mAudit...
class MAuditActor extends Actor {

  val logger = LoggerFactory.getLogger(getClass())

  def receive = {

    case any: Any =>
      //invoke maudit via restful interface, looping on failure
      logger.info("NON BLOCKING MAuditActor::receive called, any=" + any + "\n")
  }
}
