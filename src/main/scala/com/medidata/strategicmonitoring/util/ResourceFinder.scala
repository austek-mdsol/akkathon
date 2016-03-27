package com.medidata.strategicmonitoring.util

import scala.collection.Iterator
import scala.tools.nsc.interpreter.InputStream

trait ResourceFinder {
  def findResource(filename: String) = {
    import java.io.File
    val url = getClass.getClassLoader.getResource(filename)
    new File(url.getFile)
  }

  def getResource(filename: String): Iterator[String] = {
    val stream: InputStream = getClass.getClassLoader.getResourceAsStream(filename)
    val lines = io.Source.fromInputStream(stream).getLines()
    lines
  }
}
