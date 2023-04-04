package excercises

import scala.annotation.tailrec

object RansomNote extends App  {

  def countChars(str: String): Map[Char, Int] =
    str.foldLeft(Map[Char, Int]()) { case(map, chr) =>
      map + (chr -> (map.getOrElse(chr,0) + 1))
    }

 def countCharsV2(str: String) = {

   @tailrec
   def countCharsTV2(str: String, acc: Map[Char, Int]): Map[Char, Int] = {
     if (str.isEmpty) acc
     else if (acc.contains(str.head)) {
       val value = acc(str.head)
       countCharsTV2(str.tail, acc + (str.head -> (value + 1)))
     }
     else {
       countCharsTV2(str.tail, acc + (str.head -> 1))
     }
   }
   countCharsTV2(str,Map())
 }


   //println("Hello".groupBy(c =>c).view.mapValues(_.length).toMap)

  println("Hello".groupBy(c => c))

  //println(countCharsV2("Hello"))
}
