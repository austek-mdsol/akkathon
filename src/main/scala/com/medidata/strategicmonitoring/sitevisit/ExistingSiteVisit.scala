package com.medidata.strategicmonitoring.sitevisit

import java.time.Instant
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.mediddata.strategicmonioring.strategicmonitoringapi.AbstractEntity
import com.mediddata.strategicmonioring.strategicmonitoringapi.KeyAndVersion

//IMMUTABLE

//represents DTO for PUT
//AND returned DTO for a GET
class ExistingSiteVisit(keyAndVersionParam: KeyAndVersion, aaaParam: String, bbbParam: String, createdTimeParam: Instant) extends AbstractEntity(keyAndVersionParam, aaaParam, bbbParam, createdTimeParam) with SprayJsonSupport {

  //constructor that turns NewSiteVisit -> ExistingSiteVisit
  def this(newSiteVisit: NewSiteVisit) {

    this(new KeyAndVersion(newSiteVisit.keyAndVersion), newSiteVisit.aaa, newSiteVisit.bbb, newSiteVisit.createdTime)
  }
}