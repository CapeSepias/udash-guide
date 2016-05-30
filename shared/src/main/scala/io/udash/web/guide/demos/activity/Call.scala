package io.udash.web.guide.demos.activity

case class Call(rpcName: String, method: String, args: Seq[String])