package excercises

import scala.annotation.tailrec

object MergeSort extends App {


  /*
      merge([1,3], [2,4,5,6,7], []) =
      merge([3], [2,4,5,6,7], [1]) =
      merge([3], [4,5,6,7], [2,1]) =
      merge([], [4,5,6,7], [3,2,1]) =
      [1,2,3] ++ [4,5,6,7] =
      [1,2,3,4,5,6,7]
     */


  @tailrec
  def merged(list1: List[Int], list2: List[Int], acc: List[Int]): List[Int] = {
    if (list1.isEmpty) acc.reverse ++ list2
    else if (list2.isEmpty) acc.reverse ++ list1
    else if (list1.head < list2.head) merged(list1.tail, list2, list1.head :: acc)
    else merged(list1, list2.tail, list2.head :: acc)
  }

  /*
   [3,1,2,5,4] => [[3],[1],[2],[5],[4]]
   mst([[3],[1],[2],[5],[4]], []) =
   = mst([[2],[5],[4]], [[1,3]])
   = mst([[4]], [[2,5], [1,3]])
   = mst([], [[4], [2,5], [1,3]]) =
   = mst([[4], [2,5], [1,3]], [])
   = mst([[1,3]], [[2,4,5]])
   = mst([], [[1,3], [2,4,5]])
   = mst([[1,3], [2,4,5]], [])
   = mst([], [[1,2,3,4,5]])
   = [1,2,3,4,5]
   Complexity: O(n * log(n))
   complexity(n) = 2 * complexity(n/2) + n
  */

  def mergeSort(list:List[Int]): List[Int] = {

    @tailrec
    def mergeSortTailRec(smallList: List[List[Int]], bigList: List[List[Int]]): List[Int] = {
      if (smallList.isEmpty) {
        if (bigList.isEmpty) Nil
        else if (bigList.tail.isEmpty) bigList.head
        else mergeSortTailRec(bigList, Nil)
      }
      else if (smallList.tail.isEmpty) mergeSortTailRec(smallList.head :: bigList, Nil)
      else {
        val first = smallList.head
        val second = smallList.tail.head
        val merge = merged(first, second, Nil)
        mergeSortTailRec(smallList.tail.tail, merge :: bigList)
      }
    }

    mergeSortTailRec(list.map(x => x :: Nil), Nil)
  }

  println(
    
    mergeSort(List(0,0,-1,0))
  )
}

