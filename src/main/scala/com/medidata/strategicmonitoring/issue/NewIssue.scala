package com.medidata.strategicmonitoring.issue

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.mediddata.strategicmonioring.strategicmonitoringapi.AbstractEntity
import com.mediddata.strategicmonioring.strategicmonitoringapi.KeyAndVersion

//IMMUTABLE
//represents DTO from a POST
//MyKeyAndVersion represent the version we're GOING to give the IssueActor
class NewIssue(aaaParam: String, bbbParam: String) extends AbstractEntity(new KeyAndVersion(), aaaParam, bbbParam) with SprayJsonSupport {
}
