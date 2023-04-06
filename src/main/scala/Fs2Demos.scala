import cats.effect.{IO, IOApp}
import fs2.{Pure, Stream}

class Fs2Demos extends IOApp.Simple {

  case class Country(name: String, province: String)


    val cal: Country = Country("usa","california")
    val ny: Country = Country("usa","new york")
    val texas: Country = Country("usa","texas")
    val nm: Country = Country("usa","new mexico")
    val vc: Country= Country("usa","north carolina")
    val sc: Country = Country("usa","south carolina")


    val on: Country = Country("canada","ontario")
    val quebec: Country = Country("canada","quebec")
    val yukon: Country = Country("canada","yukon")
    val bc: Country = Country("canada","british columbia")
    val pei: Country = Country("canada","pei")
    val ns: Country = Country("canada","nova scotia")

    val usStreamPure: Stream[Pure, Country] = Stream(
      cal,ny,texas,nm
    )


    // Convert Stream[Pure, Country] => Stream[IO, provice]
    val usStreamIo: Stream[IO, Unit] = usStreamPure.flatMap{ province =>
      Stream.eval(IO.println(province))
    }

   // flatMap + eval = evalMap
    val usStreamIo_v2: Stream[IO, Unit] = usStreamPure.evalMap(IO.println)

    val usStreamIo_v3: Stream[IO, Country] = usStreamPure.evalTap(IO.println)


  override def run: IO[Unit] = ???
}
