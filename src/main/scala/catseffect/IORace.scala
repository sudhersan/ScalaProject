package catseffect

import cats.effect.IO
import scala.concurrent.duration._
import cats.effect.unsafe.implicits._

object IORace extends App{

  def simpleIO(value: Int,  finiteDuration: FiniteDuration): IO[Int]  = {
    IO("Starting computation") >>
      IO.sleep(finiteDuration) >>
      IO("Computation Finished") >>
      IO(value)
  }.onCancel(IO(s"Comptation cancelled for IO $value"))

  def racingIOs() = {

    val firstIO = simpleIO(100, 1.seconds)
    val secondIO = simpleIO(200, 2.seconds)

    val raceIO: IO[Either[Int, Int]] = IO.race(firstIO, secondIO)

    raceIO.flatMap{
      case Left(a) => IO(s"First IO won with value: $a")
      case Right(b)  => IO(s"Second IO won with value: $b")
    }
  }

  def raceIOsPair() = {

    val firstIO = simpleIO(100, 1.seconds)
    val secondIO = simpleIO(100, 2.seconds)

    val raceIOPair = IO.racePair(firstIO, secondIO)

    raceIOPair.flatMap{
      case Left((outA, fibB)) => fibB.cancel >> IO("First IO won") >> IO(outA)
      case Right((fibA, outB)) => fibA.cancel >> IO("Second IO won") >> IO(outB)
    }
  }


  def ioTimeout = simpleIO(100,10.seconds).timeout(5.seconds)

  racingIOs().unsafeRunSync()
  raceIOsPair().unsafeRunSync()
  ioTimeout.unsafeRunSync()

}
