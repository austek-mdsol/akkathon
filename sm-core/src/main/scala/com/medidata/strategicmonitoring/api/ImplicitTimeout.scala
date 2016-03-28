package com.medidata.strategicmonitoring.api

import akka.util.Timeout

import scala.concurrent.duration.DurationInt

trait ImplicitTimeout {

  implicit val timeout = Timeout(5 seconds) //set to network timeout for rest call
}
