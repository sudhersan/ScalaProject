package excercises

import scala.annotation.tailrec

object OccurancesOfChar extends App {

  // Scala : {'s' -> 1, 'c' -> 1, 'a' -> 2, 'l' -> 1}

  def charOccur(str: String) : Map[Char, Int] = {
    @tailrec
    def parseStr(str: String, acc: Map[Char, Int]):Map[Char, Int] = {
      if (str.isEmpty) acc
      else if (!acc.contains(str.head)) parseStr(str.tail,acc + (str.head -> 1))
      else {
        val cureValue = acc(str.head)
        val currChar = str.head
        parseStr(str.tail, acc + (currChar -> (cureValue + 1)))
      }
    }
   parseStr("scala",Map.empty[Char, Int])
  }

  //println(charOccur("scala"))

  def charOccurV2(str: String): Map[Char, Int] = str.foldLeft(Map.empty[Char, Int]) { (acc, chr) =>
    if (!acc.contains(chr)) acc + (chr -> 1)
    else {
      val currValue= acc(chr)
      acc + (chr -> (currValue + 1))
    }
  }

  //println(charOccurV2("scala"))

  def countChar3(str: String): Map[Char, Int] = str.foldLeft(Map.empty[Char, Int]){ (acc, s) =>
    if (acc.contains(s)) acc + (s -> (acc(s) + 1))
    else acc + (s -> 1 )
  }

  println(countChar3("hello"))
}
