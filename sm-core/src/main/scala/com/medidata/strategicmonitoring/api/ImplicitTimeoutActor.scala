package com.medidata.strategicmonitoring.api

import akka.actor.Actor

trait ImplicitTimeoutActor extends Actor with ImplicitTimeout {}
