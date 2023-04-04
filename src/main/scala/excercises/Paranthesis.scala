package excercises

import scala.annotation.tailrec

object Paranthesis extends App {

  val str = "{{{}}"

  def parcheck(str: String): Boolean = {

    @tailrec
    def tailRecCheck(str: String, count: Int, acc:Boolean) : Boolean = {
      if (str.isEmpty) count == 0
      else if (str.head == '}' && count == 0) false
      else if (str.head == '{') tailRecCheck(str.tail, count + 1, acc)
      else tailRecCheck(str.tail, count - 1 , acc)
    }
    tailRecCheck(str,0, acc = true)
  }

  println(parcheck(str))

}
