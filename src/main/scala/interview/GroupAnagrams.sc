//Given an array of strings strs, group the anagrams together. You can return the answer in any order.
//
//  An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.
//

List("eat","tea","tan","ate","nat","bat").groupBy(str => str.sorted).values


import scala.collection.mutable

def groupAnagrams(strs: Array[String]): List[List[String]] = {
  // Create a mutable map to store anagrams
  val anagrams = mutable.Map.empty[String, List[String]]

  for (word <- strs) {
    // Sort the word to use as a key for the anagram groups
    val sortedWord = word.sorted

    // Add the word to the list of anagrams with the same key
    val updatedAnagrams = anagrams.getOrElse(sortedWord, List()) :+ word
    anagrams.update(sortedWord, updatedAnagrams)
  }

  // Convert the values of the map into a list of lists
  anagrams.values.toList
}

// Example usage:
val strs = Array("eat", "tea", "tan", "ate", "nat", "bat")
val result = groupAnagrams(strs)
println(result.mkString("[", ", ", "]"))

