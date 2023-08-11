package catss

import cats.Semigroupal
import cats.data.Validated

object CatsLearnings extends App{

  trait MySemigroupal[F[_]] {
    def product[A, B](xa: F[A])(xb: F[B]): F[(A, B)]
  }

  trait MyMonad[F[_]] extends MySemigroupal[F] {
    def pure[A] (x: A):F[A]
    def flatMap[A, B](xa: F[A])(f: A => F[B]): F[B]
    def map[A, B](xa: F[A])(f : A => B): F[B] = {
      flatMap(xa)(x => pure(f(x)))
    }

    import cats.Monad
    import cats.syntax.flatMap._
    import cats.syntax.functor._

    def product[F[_]: Monad, A, B](xa: F[A])(xb: F[B]): F[(A, B)] = for {
      a <- xa
      b <- xb
    } yield (a , b)

  }

  type ValidatedErrorOr[A] = Validated[List[String], A]

  println(
    Semigroupal[ValidatedErrorOr].product(
      Validated.invalid(
        List("Error A", "Error B")
      ),
      Validated.invalid(List("Error C"))
    )
  )

  type EitherErrorOr[A] = Either[List[String], A]

  println(
    Semigroupal[EitherErrorOr].product(
      Left(
        List("Error A", "Error B")
      ),
      Left(List("Error C"))
    )
  )
}
