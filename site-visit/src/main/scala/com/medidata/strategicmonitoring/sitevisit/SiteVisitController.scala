package com.medidata.strategicmonitoring.sitevisit

import java.time.Instant

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.marshalling.ToResponseMarshallable.apply
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directive.{addByNameNullaryApply, addDirectiveApply}
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import com.medidata.strategicmonitoring.api.{ImplicitTimeout, Key, KeyAndVersion, Routable}
import org.slf4j.LoggerFactory

import scala.concurrent.Await

//SINGLETON
class SiteVisitController extends Routable with ImplicitTimeout {

  val logger = LoggerFactory.getLogger(getClass)

  override def title = "Site Visit Controller"

  override def getRoutes(actorSystem: ActorSystem) = {

    logger.info("SiteVisitController::getRoutes called")
    val siteVisitActorFactoryActorRef = actorSystem.actorOf(Props(SiteVisitActorFactory), name = "SiteVisitActorFactory")

    //just for POC
    val requestExistingSiteVisit: ExistingSiteVisit = new ExistingSiteVisit(new KeyAndVersion(new Key(), 1), "putAAA", "putBBB", Instant.now())

    pathPrefix("sitevisit") {
      pathEnd {
        post {

          //fake we've marshalled JSON -> NewSiteVisit
          val requestNewSiteVisit: NewSiteVisit = new NewSiteVisit("postAAA", "postBBB")
//          logger.info("POST, requestNewSiteVisit=" + requestNewSiteVisit);
//
//          val responseExistingSiteVisit = create(siteVisitActorFactoryActorRef, requestNewSiteVisit);
//          logger.info("POST, responseExistingSiteVisit=" + responseExistingSiteVisit + "\n\n\n");
//
//          complete(new HttpResponse(status = StatusCodes.Created))
          complete{
            "Visit: "+ requestNewSiteVisit
          }
        } ~ //post
          put {

            //fake we've marshalled JSON -> existingSiteVisit
            logger.info("PUT, requestExistingSiteVisit=" + requestExistingSiteVisit);

            val responseExistingSiteVisit = update(siteVisitActorFactoryActorRef, requestExistingSiteVisit);
            logger.info("PUT, responseExistingSiteVisit=" + responseExistingSiteVisit + "\n\n\n");

            complete(new HttpResponse(status = StatusCodes.OK))
          }
      } //pathEnd
    } ~ //pathPrefix
      // extract URI path element as JavaUUID
      pathPrefix("sitevisit" / JavaUUID) { keyParam =>
        pathEnd {
          get {

            //GET THE LATEST VERSION
            val requestKey: Key = new Key(keyParam);
            logger.info("GET Key, requestKey=" + requestKey)

            val responseExistingSiteVisit = readByKey(siteVisitActorFactoryActorRef, requestKey);
            logger.info("GET Key, responseExistingSiteVisit=" + responseExistingSiteVisit + "\n\n\n");

            complete(new HttpResponse(status = StatusCodes.OK))
          } //get
        } //pathEnd
      } ~ //pathPrefix
      // extract URI path element as JavaUUID
      pathPrefix("sitevisit" / JavaUUID / IntNumber) { (keyParam, versionParam) =>
        pathEnd {
          get {

            //GET THE LATEST VERSION
            val requestKeyAndVersion: KeyAndVersion = new KeyAndVersion(keyParam, versionParam);
            logger.info("GET keyAndVersion, requestKeyAndVersion=" + requestKeyAndVersion)

            val responseExistingSiteVisit = readByKeyAndVersion(siteVisitActorFactoryActorRef, requestKeyAndVersion);
            logger.info("GET KeyAndVersion, responseExistingSiteVisit=" + responseExistingSiteVisit + "\n\n\n");

            complete(new HttpResponse(status = StatusCodes.OK))
          } //get
        } //pathEnd
      } //pathPrefix
  } //getRoutes

  private def createSiteVisitActor(siteVisitActorFactoryActorRef: ActorRef, newSiteVisit: NewSiteVisit): ActorRef = Await.result((siteVisitActorFactoryActorRef ? newSiteVisit), timeout.duration).asInstanceOf[ActorRef]

  private def getSiteVisitActor(siteVisitActorFactoryActorRef: ActorRef, key: Key): ActorRef = Await.result((siteVisitActorFactoryActorRef ? key), timeout.duration).asInstanceOf[ActorRef]

  private def create(siteVisitActorFactoryActorRef: ActorRef, newSiteVisit: NewSiteVisit): ExistingSiteVisit = Await.result((createSiteVisitActor(siteVisitActorFactoryActorRef, newSiteVisit) ? newSiteVisit), timeout.duration).asInstanceOf[ExistingSiteVisit]

  private def readByKey(siteVisitActorFactoryActorRef: ActorRef, key: Key): ExistingSiteVisit = Await.result((getSiteVisitActor(siteVisitActorFactoryActorRef, key) ? key), timeout.duration).asInstanceOf[ExistingSiteVisit]

  private def readByKeyAndVersion(siteVisitActorFactoryActorRef: ActorRef, keyAndVersion: KeyAndVersion): ExistingSiteVisit = Await.result((getSiteVisitActor(siteVisitActorFactoryActorRef, keyAndVersion.key) ? keyAndVersion), timeout.duration).asInstanceOf[ExistingSiteVisit]

  private def update(siteVisitActorFactoryActorRef: ActorRef, existingSiteVisit: ExistingSiteVisit): ExistingSiteVisit = Await.result((getSiteVisitActor(siteVisitActorFactoryActorRef, existingSiteVisit.keyAndVersion.key) ? existingSiteVisit), timeout.duration).asInstanceOf[ExistingSiteVisit]
}

//SiteVisitController
