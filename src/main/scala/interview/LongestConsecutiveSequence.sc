//Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
//
//You must write an algorithm that runs in O(n) time.

// List(100, 4, 200, 1, 3, 2)

def longestConsecutiveSeq(nums: List[Int]): Int = {
  val hashSet = scala.collection.mutable.HashSet[Int]()
  var maxLength = 0

  var currentLength = 0
  var currentNum = 0

  for (num <- nums){
    if (!hashSet.contains(num)) {
      currentNum = num
      currentLength = 1
    }

    while (hashSet.contains(currentNum + 1)){
      currentNum += 1
      currentLength += 1
    }

    maxLength = math.max(maxLength, currentLength)
  }
  maxLength
}

