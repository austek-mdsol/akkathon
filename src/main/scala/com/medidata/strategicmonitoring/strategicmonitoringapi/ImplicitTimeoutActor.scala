package com.mediddata.strategicmonioring.strategicmonitoringapi

import akka.actor.Actor

trait ImplicitTimeoutActor extends Actor with ImplicitTimeout {}
