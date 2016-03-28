package com.medidata.strategicmonitoring.api

import java.util.UUID

import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat}

object UUIDJsonProtocol extends DefaultJsonProtocol {

  // implicit JSON marshaller for UUID
  implicit object UUIDJsonFormat extends RootJsonFormat[UUID] {

    def write(c: UUID) = JsString(c.toString())

    def read(value: JsValue) = UUID.fromString(value.toString())
  }
}
