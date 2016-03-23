package com.mediddata.strategicmonioring.strategicmonitoringapi

import java.util.UUID
import spray.json.DefaultJsonProtocol
import spray.json.JsString
import spray.json.JsValue
import spray.json.RootJsonFormat

object UUIDJsonProtocol extends DefaultJsonProtocol {

  // implicit JSON marshaller for UUID
  implicit object UUIDJsonFormat extends RootJsonFormat[UUID] {

    def write(c: UUID) = JsString(c.toString())

    def read(value: JsValue) = UUID.fromString(value.toString())
  }
}
