package com.medidata.strategicmonitoring.issue

import scala.concurrent.Await
import org.slf4j.LoggerFactory
import java.time.Instant
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.http.scaladsl.marshalling.ToResponseMarshallable.apply
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directive.addByNameNullaryApply
import akka.http.scaladsl.server.Directive.addDirectiveApply
import akka.http.scaladsl.server.Directives.JavaUUID
import akka.http.scaladsl.server.Directives.IntNumber
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.Directives.enhanceRouteWithConcatenation
import akka.http.scaladsl.server.Directives.get
import akka.http.scaladsl.server.Directives.pathEnd
import akka.http.scaladsl.server.Directives.pathPrefix
import akka.http.scaladsl.server.Directives.post
import akka.http.scaladsl.server.Directives.put
import akka.http.scaladsl.server.Directives.segmentStringToPathMatcher
import akka.pattern.ask
import akka.http.scaladsl.model.StatusCodes
import com.mediddata.strategicmonioring.strategicmonitoringapi.Key
import com.mediddata.strategicmonioring.strategicmonitoringapi.KeyAndVersion
import com.mediddata.strategicmonioring.strategicmonitoringapi.ImplicitTimeout

//SINGLETON
object IssueController extends ImplicitTimeout {

  val logger = LoggerFactory.getLogger(getClass)

  def getRoutes(actorSystem: ActorSystem) = {

    logger.info("IssueController::getRoutes called");
    val issueActorFactoryActorRef = actorSystem.actorOf(Props(IssueActorFactory), name = "IssueActorFactory")
  
    //just for POC
    val requestExistingIssue: ExistingIssue = new ExistingIssue(new KeyAndVersion(new Key(), 1), "putAAA", "putBBB", Instant.now())

    pathPrefix("issue") {
      pathEnd {
        post {

          //fake we've marshalled JSON -> NewIssue
          val requestNewIssue: NewIssue = new NewIssue("postAAA", "postBBB")
          logger.info("POST, requestNewIssue=" + requestNewIssue);

          val responseExistingIssue = create(issueActorFactoryActorRef, requestNewIssue);
          logger.info("POST, responseExistingIssue=" + responseExistingIssue + "\n\n\n");

          complete(new HttpResponse(status = StatusCodes.Created))
        } ~ //post
          put {

            //fake we've marshalled JSON -> existingIssue
            logger.info("PUT, requestExistingIssue=" + requestExistingIssue);

            val responseExistingIssue = update(issueActorFactoryActorRef, requestExistingIssue);
            logger.info("PUT, responseExistingIssue=" + responseExistingIssue + "\n\n\n");

            complete(new HttpResponse(status = StatusCodes.OK))
          }
      } //pathEnd
    } ~ //pathPrefix
      // extract URI path element as JavaUUID
      pathPrefix("issue" / JavaUUID) { keyParam =>
        pathEnd {
          get {

            //GET THE LATEST VERSION
            val requestKey: Key = new Key(keyParam);
            logger.info("GET Key, requestKey=" + requestKey)

            val responseExistingIssue = readByKey(issueActorFactoryActorRef, requestKey);
            logger.info("GET Key, responseExistingIssue=" + responseExistingIssue + "\n\n\n");

            complete(new HttpResponse(status = StatusCodes.OK))
          } //get
        } //pathEnd
      } ~ //pathPrefix
      // extract URI path element as JavaUUID
      pathPrefix("issue" / JavaUUID / IntNumber) { (keyParam, versionParam) =>
        pathEnd {
          get {

            //GET THE LATEST VERSION
            val requestKeyAndVersion: KeyAndVersion = new KeyAndVersion(keyParam, versionParam);
            logger.info("GET keyAndVersion, requestKeyAndVersion=" + requestKeyAndVersion)

            val responseExistingIssue = readByKeyAndVersion(issueActorFactoryActorRef, requestKeyAndVersion);
            logger.info("GET KeyAndVersion, responseExistingIssue=" + responseExistingIssue + "\n\n\n");

            complete(new HttpResponse(status = StatusCodes.OK))
          } //get
        } //pathEnd
      } //pathPrefix
  } //getRoutes

  private def createIssueActor(issueActorFactoryActorRef: ActorRef, newIssue: NewIssue): ActorRef = Await.result((issueActorFactoryActorRef ? newIssue), timeout.duration).asInstanceOf[ActorRef]

  private def getIssueActor(issueActorFactoryActorRef: ActorRef, key: Key): ActorRef = Await.result((issueActorFactoryActorRef ? key), timeout.duration).asInstanceOf[ActorRef]

  private def create(issueActorFactoryActorRef: ActorRef, newIssue: NewIssue): ExistingIssue = Await.result((createIssueActor(issueActorFactoryActorRef, newIssue) ? newIssue), timeout.duration).asInstanceOf[ExistingIssue]

  private def readByKey(issueActorFactoryActorRef: ActorRef, key: Key): ExistingIssue = Await.result((getIssueActor(issueActorFactoryActorRef, key) ? key), timeout.duration).asInstanceOf[ExistingIssue]

  private def readByKeyAndVersion(issueActorFactoryActorRef: ActorRef, keyAndVersion: KeyAndVersion): ExistingIssue = Await.result((getIssueActor(issueActorFactoryActorRef, keyAndVersion.key) ? keyAndVersion), timeout.duration).asInstanceOf[ExistingIssue]

  private def update(issueActorFactoryActorRef: ActorRef, existingIssue: ExistingIssue): ExistingIssue = Await.result((getIssueActor(issueActorFactoryActorRef, existingIssue.keyAndVersion.key) ? existingIssue), timeout.duration).asInstanceOf[ExistingIssue]
} //IssueController
