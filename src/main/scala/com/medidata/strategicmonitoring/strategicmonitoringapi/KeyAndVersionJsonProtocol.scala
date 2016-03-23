package com.mediddata.strategicmonioring.strategicmonitoringapi

import spray.json.DefaultJsonProtocol
import spray.json.JsString
import spray.json.JsValue
import spray.json.RootJsonFormat

object KeyAndVersionJsonProtocol extends DefaultJsonProtocol {

  // implicit JSON marshaller for MyKey
  implicit object KeyAndVersionJsonFormat extends RootJsonFormat[KeyAndVersion] {

    def write(c: KeyAndVersion) = JsString(c.toString())

    def read(value: JsValue) = new KeyAndVersion(value.toString())
  }
}