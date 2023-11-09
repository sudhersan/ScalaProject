//You are given an array prices where prices[i] is the price of a given stock on the ith day.
//
//  You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
//
//  Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.


def maxProfit(prices: Array[Int]): Int = {
  if (prices.isEmpty) return 0

  var minPrice = prices(0)
  var maxProfit = 0

  for (price <- prices) {
    minPrice = math.min(minPrice, price)
    maxProfit = math.max(maxProfit, price - minPrice)
  }

  maxProfit
}