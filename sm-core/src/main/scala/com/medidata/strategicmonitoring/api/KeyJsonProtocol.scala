package com.medidata.strategicmonitoring.api

import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat}

object KeyJsonProtocol extends DefaultJsonProtocol {

  // implicit JSON marshaller for MyKey
  implicit object KeyJsonFormat extends RootJsonFormat[Key] {

    def write(c: Key) = JsString(c.toString())

    def read(value: JsValue) = new Key(value.toString())
  }
}
