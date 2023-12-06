package no.jrgn.day4

import no.jrgn.Utils
import kotlin.math.pow

infix fun Int.`**`(exponent: Int): Int = toDouble().pow(exponent).toInt()

class Day4 {

    fun one(input: List<String>): Int {
        val matchDigits = "\\d+".toRegex()
        return input.map { s ->
            s.split(":").last().split("|")
                .let { winAndDrawn ->
                    Pair(
                        matchDigits.findAll(winAndDrawn.first()).map { it.value }.toList(),
                        matchDigits.findAll(winAndDrawn.last()).map { it.value }.toList()
                    )
                }
        }.map { (winnerNumbers, myNumbers) ->
            myNumbers.intersect(winnerNumbers.toSet()).let { commonNumbers ->
                val nums = commonNumbers.mapNotNull { it.toIntOrNull() }
                if (nums.isEmpty()) return@let 0
                if (nums.size == 1) return@let 1
                else 2 `**` (nums.size - 1)
            }
        }.sum()
    }

    fun two(input: List<String>): Int {
        val matchDigits = "\\d+".toRegex()
        val cache = mutableMapOf<Int, Int>()
        val winsCache = mutableMapOf<Triple<Int, List<String>, List<String>>, Int>()

        val cards = input.map { s ->
            s.split(":").last().split("|")
                .let { winAndDrawn ->
                    Triple(
                        matchDigits.find(s.split(":").first())?.value?.toInt() ?: 1,
                        matchDigits.findAll(winAndDrawn.first()).map { it.value }.toList(),
                        matchDigits.findAll(winAndDrawn.last()).map { it.value }.toList()
                    )
                }
        }

        cards.forEach { (cardNumber) -> cache[cardNumber] = 1 }

        cards.map { (cardNumber, winnerNumbers, myNumbers) ->
            println(cardNumber)

            val numCopies = cache.getOrDefault(cardNumber, 1)
            (0..<numCopies).forEach {
                // If cachedWin is 4, you win a copy of the next four cards.
                val cachedWin = winsCache.getOrPut(Triple(cardNumber, winnerNumbers, myNumbers)) {
                    myNumbers.intersect(winnerNumbers.toSet()).size
                }
                (cardNumber + 1..cardNumber + cachedWin).forEach {
                    try {
                        cache[it] = cache[it]!! + 1
                    } catch (ex: Exception) {
                        println(ex.message)
                    }
                }
            }
        }

        return cache.values.sum()
    }
}


fun main() {
    val input = Utils.readInput(4)
    val testInput = Utils.readTestInput(4)
    val testTwoInput = Utils.readTestInput(4)

    println("Test: ${Day4().one(testInput)}")
    println("First: ${Day4().one(input)}")

    println("Test: ${Day4().two(testTwoInput)}")
    println("Second: ${Day4().two(input)}")
}
