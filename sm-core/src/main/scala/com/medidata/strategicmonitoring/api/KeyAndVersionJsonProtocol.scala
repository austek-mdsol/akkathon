package com.medidata.strategicmonitoring.api

import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat}

object KeyAndVersionJsonProtocol extends DefaultJsonProtocol {

  // implicit JSON marshaller for MyKey
  implicit object KeyAndVersionJsonFormat extends RootJsonFormat[KeyAndVersion] {

    def write(c: KeyAndVersion) = JsString(c.toString())

    def read(value: JsValue) = new KeyAndVersion(value.toString())
  }
}
