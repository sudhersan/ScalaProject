package catseffect

import cats.effect.unsafe.implicits.global
import cats.effect.{ExitCode, IO, IOApp}

object CatsIOEx extends IOApp {

  val add: IO[Unit] = for {
    a <- IO(1)
    b <- IO(2)
    _ <- IO(println(a + b))
  } yield ()

  override def run(args: List[String]): IO[ExitCode] = add.as(ExitCode.Success)
}
