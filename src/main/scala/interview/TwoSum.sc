import scala.annotation.tailrec
//Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
//
//You may assume that each input would have exactly one solution, and you may not use the same element twice.
//
//  You can return the answer in any order.

List(2,5,7)

def twoSum(nums: Array[Int], target: Int): Array[Int] = {
  // Create a mutable map to store the values and their corresponding indices
  val numIndices = collection.mutable.Map.empty[Int, Int]

  // Iterate through the array with indices
  for (i <- nums.indices) {
    val num = nums(i)
    // Calculate the complement needed to reach the target
    val complement = target - num

    // Check if the complement is in the map
    if (numIndices.contains(complement)) {
      // If found, return the indices of the two numbers
      return Array(numIndices(complement), i)
    }

    // If the complement is not in the map, store the current number and its index
    numIndices += (num -> i)
  }

  // If no solution is found, return an empty array
  Array()
}

// Example usage:
val nums = Array(2, 7, 11, 15)
val target = 9
val result = twoSum(nums, target)
println(result.mkString("[", ", ", "]"))  // Output: [0, 1] (indices of the numbers 2 and 7 that add up to 9)

