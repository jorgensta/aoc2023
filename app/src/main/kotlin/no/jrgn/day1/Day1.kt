import no.jrgn.Utils

class Day1 {
    fun one(input: List<String>): Int {
        return input
            .map { it.filter { char -> char.isDigit() } }
            .map { "${it.first()}${it.last()}" }
            .sumOf { it.toInt() }
    }

    fun two(input: List<String>): Int {
        return input
            .map { findAllDigitsInString(it) }
            .map { "${it.first()}${it.last()}" }
            .sumOf { it.toInt() }
    }

    private fun findAllDigitsInString(str: String): String {
        val letterIndexesPairs = letterWords.keys.mapNotNull { str.findAnyOf(listOf(it)) }
        val reversedLetterIndexPairs = letterWords.keys.mapNotNull { str.findLastAnyOf(listOf(it)) }

        val digitIndexPairs = letterWords.values.mapNotNull { str.findAnyOf(listOf(it.toString())) }
        val reversedDigitPairs = letterWords.values.mapNotNull { str.findLastAnyOf(listOf(it.toString())) }

        val sortedByIndex =
            (letterIndexesPairs + digitIndexPairs + reversedDigitPairs + reversedLetterIndexPairs).sortedBy { it.first }
        return sortedByIndex.joinToString(separator = "") { it.second.toNumeric() }
    }

    private fun String.isNumeric() = this.matches("-?[0-9]+(\\.[0-9]+)?".toRegex())
    private fun String.toNumeric(): String {
        if (this.isNumeric()) {
            return this
        }

        return letterWords[this].toString()
    }

    companion object {
        val letterWords = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )
    }
}

fun main() {
    val input = Utils.readInput(1)
    println("First: ${Day1().one(input)}")
    println("Second: ${Day1().two(input)}")
}
