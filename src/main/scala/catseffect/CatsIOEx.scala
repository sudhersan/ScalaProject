package catseffect

import cats.effect.kernel.Fiber
import cats.effect.kernel.Outcome.{Canceled, Errored, Succeeded}
import cats.effect.{ExitCode, IO, IOApp, Outcome}

import scala.concurrent.duration.{DurationInt, FiniteDuration}

object CatsIOEx extends IOApp.Simple {

  // Excercise #1
  val add: IO[Unit] = for {
    a <- IO(1)
    b <- IO(2)
    _ <- IO(println(a + b))
  } yield ()

  // Fibre datatype
  def fiber() : IO[Fiber[IO, Throwable, Int]] = ???

  //Outcome datatype
  def outcome(): IO[Outcome[IO, Throwable, Int]] = ???

  // Excercise #2
  def tupledIO[A,B](ioa: IO[A], iob: IO[B]): IO[(A, B)] = {
    val tupled = for {
      a <- ioa.start
      b <- iob.start
      resulta <- a.join
      resultb <- b.join
    } yield (resulta, resultb)

     tupled.flatMap{
       case (Succeeded(fa), Succeeded(fb)) => for {
         a <- fa
         b <- fb
       } yield (a, b)
       case (Errored(e1), _ ) => IO.raiseError(e1)
       case (_, Errored(e2)) => IO.raiseError(e2)
       case _ => IO.raiseError(new RuntimeException("Some computation cancelled.."))
     }
  }

  // Excercise #2
  def timeout[A](io: IO[A], duration: FiniteDuration): IO[A] ={
    val computation =  for {
      fib <- io.start
      _ <- IO.sleep(duration) >> fib.cancel
      result <- fib.join
    } yield result

    computation.flatMap {
      case Succeeded(fa) => fa
      case Errored(e) => IO.raiseError(e)
      case Canceled() => IO.raiseError(new RuntimeException("Cancelled"))
    }
  }


  def test2(): IO[Int] = {
    val ioComputation = IO("Starting") >> IO.sleep(1.second) >> IO("done") >> IO(42)
    timeout(ioComputation, 2.seconds)
  }

  override def run: IO[Unit] = ???
}
