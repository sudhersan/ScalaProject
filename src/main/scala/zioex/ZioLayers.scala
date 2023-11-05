package zioex

import zio.{Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object ZioLayers extends ZIOAppDefault{

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ???
}
