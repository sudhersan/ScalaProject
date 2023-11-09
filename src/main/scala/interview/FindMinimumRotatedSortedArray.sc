//Suppose an array of length n sorted in ascending order is rotated between 1 and n times.
// For example, the array nums = [0,1,2,4,5,6,7] might become:
//
//[4,5,6,7,0,1,2] if it was rotated 4 times.
//[0,1,2,4,5,6,7] if it was rotated 7 times.
//Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
//
//Given the sorted rotated array nums of unique elements, return the minimum element of this array.
//
//You must write an algorithm that runs in O(log n) time.


def findMin(nums: Array[Int]): Int = {
  var left = 0
  var right = nums.length - 1

  while (left < right) {
    val mid = left + (right - left) / 2

    if (nums(mid) > nums(right)) {
      // The minimum element must be in the right subarray
      left = mid + 1
    } else {
      // The minimum element must be in the left subarray or at the current position
      right = mid
    }
  }

  nums(left)
}