import scala.collection.mutable.ListBuffer

def threeSum(nums: Array[Int]): List[List[Int]] = {
  val result = ListBuffer[List[Int]]()

  if (nums.length < 3) return result.toList

  val sortedNums = nums.sorted

  for (i <- 0 until sortedNums.length - 2) {
    if (i == 0 || (i > 0 && sortedNums(i) != sortedNums(i - 1))) {
      var lo = i + 1
      var hi = sortedNums.length - 1
      val target = -sortedNums(i)

      while (lo < hi) {
        if (sortedNums(lo) + sortedNums(hi) == target) {
          result += List(sortedNums(i), sortedNums(lo), sortedNums(hi))
          while (lo < hi && sortedNums(lo) == sortedNums(lo + 1)) lo += 1
          while (lo < hi && sortedNums(hi) == sortedNums(hi - 1)) hi -= 1
          lo += 1
          hi -= 1
        } else if (sortedNums(lo) + sortedNums(hi) < target) {
          lo += 1
        } else {
          hi -= 1
        }
      }
    }
  }

  result.toList
}

// Example usage:
val nums = Array(-1, 0, 1, 2, -1, -4)
val result = threeSum(nums)
println(result)
// Output: List(List(-1, -1, 2), List(-1, 0, 1))
