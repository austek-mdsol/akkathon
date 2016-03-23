package com.mediddata.strategicmonioring.strategicmonitoringapi

import java.time.Instant
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

//key point this should be IMMUTABLE
//represents DTO from a POST
abstract class AbstractEntity(keyAndVersionParam: KeyAndVersion, aaaParam: String, bbbParam: String, createdTimeParam: Instant = Instant.now()) extends SprayJsonSupport {

  val keyAndVersion = keyAndVersionParam
  val aaa = aaaParam
  val bbb = bbbParam
  val createdTime = createdTimeParam

  override def toString(): String = "keyAndVersion=" + keyAndVersion.toString() + ", aaa=" + aaa + ", bbb=" + bbb + ", createdTime=" + createdTime.toString()
}
