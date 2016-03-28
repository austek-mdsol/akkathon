package com.medidata.strategicmonitoring.api

import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

//immutable
//REPRESENTS DTO for GET and DELETE if we coded it
//encapsulate / abstract away key, might just be UUID, might be UUID and version number combo
//CONVENIENCE constructors dealing with Strings etc from JSON
class KeyAndVersion(keyParam: Key, versionParam: Int) extends SprayJsonSupport {

  val key: Key = keyParam
  val version: Int = versionParam

  def this(keyParam: UUID, versionParam: Int) = {

    this(new Key(keyParam), versionParam)
  }

  def this(previousKeyAndVersion: KeyAndVersion) = {

    this(previousKeyAndVersion.key, (previousKeyAndVersion.version + 1))
  }

  def this() = {

    this(new Key(), 0);
  }

  //for Json marshalling / unmarshalling
  def this(keyAndVersionString: String) = {

    this(new Key(keyAndVersionString.substring(0, keyAndVersionString.indexOf("/"))),
      keyAndVersionString.substring(keyAndVersionString.indexOf("/")).toInt)
  }

  override def toString(): String = key + "/" + version

  override def hashCode: Int = toString().hashCode()

  override def equals(other: Any) = other match {
    case that: KeyAndVersion =>
      this.toString().equals(that.toString())
    case _ => false
  }
}
