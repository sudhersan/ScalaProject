package excercises

object OccurancesOfCharV2 extends App {

  val str = "shello"

  val occurances: Map[Char, Int] = str.foldLeft(Map.empty[Char, Int]) { (acc, s) =>
    if (acc.contains(s)) {
      val current = acc(s)
      acc + (s -> (current + 1))
    }
    else acc + (s -> 1)
  }

  println(occurances.maxBy(_._2))

  val maxOccur = occurances.foldLeft(0){ (acc, m) =>
   if (m._2 > acc) m._2 else acc
  }



}
