package io.udash.web.guide

import com.avsystem.commons.rpc.RPC
import io.udash.web.guide.demos.DemosClientRPC

@RPC
trait MainClientRPC {
  def demos(): DemosClientRPC
}
