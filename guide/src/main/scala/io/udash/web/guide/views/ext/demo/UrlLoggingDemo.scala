package io.udash.web.guide.views.ext.demo

import io.udash.properties.{Property, SeqProperty}

import scala.collection.mutable.ListBuffer

object UrlLoggingDemo {

  import io.udash.web.guide.Context._

  val enabled = Property(false)
  val history = SeqProperty[(String, Option[String])](ListBuffer.empty)
  enabled.listen(b => if (!b) history.set(ListBuffer.empty))

  def log(url: String, referrer: Option[String]): Unit =
    if (enabled.get) history.append((url, referrer))

}
