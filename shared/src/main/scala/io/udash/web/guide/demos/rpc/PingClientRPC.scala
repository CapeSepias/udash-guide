package io.udash.web.guide.demos.rpc

import com.avsystem.commons.rpc.RPC

@RPC
trait PingClientRPC {
  def pong(id: Int): Unit
}
