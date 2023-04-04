package excercises

import scala.annotation.tailrec

object DuplicateItems extends App {

  val list = List(8,8,9)

  def check(list: List[Int]) = {
    @tailrec
    def isSorted(list: List[Int], no: Int, acc: Boolean): Boolean = {
      if (list.tail == Nil) acc
      else if (list.head >= no) {
        isSorted(list.tail, list.head, acc = true)
      }
      else isSorted(list.tail, list.head, acc=false)
    }
    isSorted(list, Integer.MIN_VALUE, acc = false)
  }
  println(check(list))

}