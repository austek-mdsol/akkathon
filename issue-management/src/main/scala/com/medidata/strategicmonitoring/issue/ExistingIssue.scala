package com.medidata.strategicmonitoring.issue

import java.time.Instant

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.medidata.strategicmonitoring.api.{AbstractEntity, KeyAndVersion}

//IMMUTABLE

//represents DTO for PUT
//AND returned DTO for a GET
class ExistingIssue(keyAndVersionParam: KeyAndVersion, aaaParam: String, bbbParam: String, createdTimeParam: Instant) extends AbstractEntity(keyAndVersionParam, aaaParam, bbbParam, createdTimeParam) with SprayJsonSupport {

  //constructor that turns NewIssue -> ExistingIssue
  def this(newIssue: NewIssue) {

    this(new KeyAndVersion(newIssue.keyAndVersion), newIssue.aaa, newIssue.bbb, newIssue.createdTime)
  }
}
