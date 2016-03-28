package com.medidata.strategicmonitoring.api

import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

//immutable
class Key(keyParam: UUID) extends SprayJsonSupport {

  val key = keyParam

  //for newly created entities
  def this() = {

    this(UUID.randomUUID())
  }

  //for Json marshalling
  def this(keyString: String) = {

    this(UUID.fromString(keyString))
  }

  override def toString(): String = key.toString()

  override def hashCode: Int = key.hashCode()

  override def equals(other: Any) = other match {
    case that: Key =>
      this.key.equals(that.key)
    case _ => false
  }
}
