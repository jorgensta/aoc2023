package no.jrgn.day3

import no.jrgn.Utils

class Day3 {
    private fun MutableList<String>.prettyPrint() {
        println("-------------------------")
        this.forEach {
            println(it)
        }
    }

    private fun numberHasAnyAdjacentGears(
        number: String,
        grid: List<List<Char>>,
        i: Int,
        range: IntRange
    ): Pair<Int, Int>? {
        val (x, y) = Pair(i, range.first)
        val numberLength = number.length
        val rowStart = if (x - 1 <= 0) 0 else x - 1
        val rowEnd = if (x + 1 >= grid.first().size - 1) grid.first().size - 1 else x + 1
        val columnStart = if (y - 1 <= 0) 0 else y - 1
        val columnEnd = if (y + numberLength >= grid.size - 1) grid.size - 1 else y + numberLength

        val charactersInWindow = mutableListOf<Pair<Char, Pair<Int, Int>>>()

        (rowStart..rowEnd).forEach { rowX ->
            val row = mutableListOf<Char>()
            (columnStart..columnEnd).forEach { colY ->
                row.add(grid[rowX][colY])
                charactersInWindow.add(Pair(grid[rowX][colY], Pair(rowX, colY)))
            }
        }

        val gear = charactersInWindow.find { it.first == '*' }
        return gear?.second
    }

    private fun numberHasAnyAdjacentSymbols(
        number: String,
        grid: List<List<Char>>,
        symbols: List<Char>,
        i: Int,
        range: IntRange
    ): Boolean {
        val (x, y) = Pair(i, range.first)
        val numberLength = number.length
        val rowStart = if (x - 1 <= 0) 0 else x - 1
        val rowEnd = if (x + 1 >= grid.first().size - 1) grid.first().size - 1 else x + 1
        val columnStart = if (y - 1 <= 0) 0 else y - 1
        val columnEnd = if (y + numberLength >= grid.size - 1) grid.size - 1 else y + numberLength

        val charactersInWindow = mutableListOf<Char>()

        (rowStart..rowEnd).forEach { rowX ->
            val row = mutableListOf<Char>()
            (columnStart..columnEnd).forEach { colY ->
                row.add(grid[rowX][colY])
                charactersInWindow.add(grid[rowX][colY])
            }
        }

        return symbols.any { charactersInWindow.contains(it) }
    }

    fun one(input: List<String>): Int {
        val matchNumbers = "\\d+".toRegex()
        val symbols = input.map { it.toCharArray().toList() }.flatten().toSet().filter { !(it.isDigit() || it == '.') }
        val grid = input.map { it.toCharArray().toList() }

        val numbers = grid.toList().mapIndexed { i, row ->
            val rowString = row.joinToString("")
            val matchedNumbers = matchNumbers.findAll(rowString).toList().map { it.value to it.range }
            matchedNumbers.map { (number, range) ->
                val has = numberHasAnyAdjacentSymbols(number, grid, symbols, i, range)
                if (has) number.toInt() else 0
            }
        }

        return numbers.flatten().sum()
    }

    fun two(input: List<String>): Int {
        val grid = input.map { it.toCharArray().toList() }
        val matchNumbers = "\\d+".toRegex()

        val numbersWithGears = grid.toList().mapIndexed { i, row ->
            val rowString = row.joinToString("")
            val matchedNumbers = matchNumbers.findAll(rowString).toList().map { it.value to it.range }
            matchedNumbers.mapNotNull { (number, range) ->
                val gear = numberHasAnyAdjacentGears(number, grid, i, range)
                if (gear != null) Pair(number, gear) else null
            }
        }.flatten()

        return numbersWithGears
            .groupBy { it.second }
            .filterValues { it.size == 2 }
            .map { it.value.first().first.toInt() * it.value.last().first.toInt() }
            .sum()
    }
}

fun main() {
    val input = Utils.readInput(3)
    val testInput = Utils.readTestInput(3)
    val testTwoInput = Utils.readTestInput(3)

    // println("Test: ${Day3().one(testInput)}")
    // println("First: ${Day3().one(input)}")

    println("Test: ${Day3().two(testTwoInput)}")
    println("Second: ${Day3().two(input)}")
}
