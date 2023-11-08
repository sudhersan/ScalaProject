//Given an integer array nums and an integer k,
//return the k most frequent elements.
//  You may return the answer in any order.

def kMostFrequentElements(nums: Array[Int], k: Int): List[Int] =
  nums.groupBy(identity).mapValues(_.length).toList.sortBy(-_._2).take(k).map(_._1)