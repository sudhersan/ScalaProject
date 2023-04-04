package zioex

import zio.{Fiber, Scope, URIO, ZIO, ZIOAppArgs, ZIOAppDefault}

object FibersEx extends ZIOAppDefault{

   val fib1: ZIO[Any, Nothing, Unit] = ZIO.succeed(println("Hello"))
   val fib2: ZIO[Any, Nothing, Unit] = ZIO.succeed(println("World"))

   val result: ZIO[Any, Nothing, (String, String)] = for {
     f1 <- ZIO.succeed("Hello").fork
     f2 <- ZIO.succeed("World").fork
     f <- f1.zip(f2).join
   } yield f

  override def run = result.map(println(_))
}
