package io.udash.web.guide.demos.activity

import scala.collection.mutable.ListBuffer

class CallLogger {
  private val _calls = ListBuffer.empty[Call]

  def append(call: Call): Unit =
    _calls += call

  def calls: List[Call] = _calls.toList
}