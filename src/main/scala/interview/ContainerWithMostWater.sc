//You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
//
//  Find two lines that together with the x-axis form a container, such that the container contains the most water.
//
//Return the maximum amount of water a container can store.
//
//Notice that you may not slant the container.

//[1,8,6,2,5,4,8,3,7]

def volume(nums : List[Int]): Int = {

  var maxWater = 0
  var left = 0
  var right = nums.length - 1

  while (left < right) {
    val hLeft = nums(left)
    val hRight = nums(right)
    val minHeight = math.min(hLeft, hRight)
    val baseL = right - left
    maxWater = math.max(maxWater, minHeight * baseL)

    if (hLeft < hRight) left +=1
    else right -= 1
  }
  maxWater
}