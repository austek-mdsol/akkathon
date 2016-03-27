package com.medidata.strategicmonitoring.util

import scala.reflect.runtime._

trait ResourceLoader {
  val runtimeMirror = universe.runtimeMirror(getClass.getClassLoader)

  def getCompanionObject[T](objectName: String): T = {
    val module = runtimeMirror.staticModule(objectName)
    val obj = runtimeMirror.reflectModule(module)
    obj.instance.asInstanceOf[T]
  }
}
