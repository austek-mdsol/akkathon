package com.mediddata.strategicmonioring.strategicmonitoringapi

import spray.json.DefaultJsonProtocol
import spray.json.JsString
import spray.json.JsValue
import spray.json.RootJsonFormat

object KeyJsonProtocol extends DefaultJsonProtocol {

  // implicit JSON marshaller for MyKey
  implicit object KeyJsonFormat extends RootJsonFormat[Key] {

    def write(c: Key) = JsString(c.toString())

    def read(value: JsValue) = new Key(value.toString())
  }
}
