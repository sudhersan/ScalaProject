package catseffect

import cats.effect.{Deferred, IO, IOApp, Ref}
import cats.implicits._

import scala.concurrent.duration.DurationInt

class IOConcurrencyEx extends IOApp.Simple {

  val list = List("Scal","a is awesome","and interes","ting <>EOF")

  def fileDownLoader(): IO[Unit] = {
    def fileDownloadWatcher(signal: Deferred[IO, String]) =
      for {
      _ <- IO("Wait for file to complete download")
      _ <- signal.get
      _ <- IO("Download complete")
      } yield ()

    def fileDownloadChecker(filepart: String, ref : Ref[IO, String], signal: Deferred[IO, String]) =  for {
      _ <- IO(s"Download $filepart")
      - <- IO.sleep(1.second)
      filepart <- ref.updateAndGet(part => part + ref)
      _ <- if (filepart.contains("EOF"))  signal.complete(filepart) else IO.unit
    } yield ()

     for {
       ref <- Ref[IO].of(" ")
       signal <- Deferred[IO, String]
       watcherfib <- fileDownloadWatcher(signal).start
       checkerfib <- list.map(part => fileDownloadChecker(part, ref, signal)).sequence.start
       _ <- watcherfib.join
       _ <- checkerfib.join
     } yield ()
  }

  override def run: IO[Unit] = fileDownLoader()
}
