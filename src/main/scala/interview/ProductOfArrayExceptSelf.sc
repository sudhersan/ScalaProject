//Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].
//
//  The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
//
//You must write an algorithm that runs in O(n) time and without using the division operation.

def productExceptSelf(nums: Array[Int]): Array[Int] = {
  val n = nums.length
  val leftProducts = new Array[Int](n)
  val rightProducts = new Array[Int](n)
  val result = new Array[Int](n)

  // Calculate left products
  var leftProduct = 1
  for (i <- 0 until n) {
    leftProducts(i) = leftProduct
    leftProduct *= nums(i)
  }

  // Calculate right products and multiply them with left products to get the result
  var rightProduct = 1
  for (i <- (n - 1) to 0 by -1) {
    rightProducts(i) = rightProduct
    rightProduct *= nums(i)
    result(i) = leftProducts(i) * rightProducts(i)
  }

  result
}

// Example usage:
val nums = Array(1, 2, 3, 4)
val result = productExceptSelf(nums)
println(result.mkString("[", ", ", "]"))
// Output: [24, 12, 8, 6]
