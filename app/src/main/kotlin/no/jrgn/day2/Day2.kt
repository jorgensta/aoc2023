package no.jrgn.day2

import no.jrgn.Utils

class Day2 {
    companion object {
        const val RED_CUBES = 12
        const val BLUE_CUBES = 14
        const val GREEN_CUBES = 13
    }

    fun one(input: List<String>): Int {
        val gameMatcher = "Game \\d+".toRegex()
        val matcherBags = "\\d+\\s+\\w+".toRegex()
        val gameIds = input.mapNotNull { line ->
            val gameId = gameMatcher.find(line)?.value?.split(" ")?.last()?.toInt()
            val shownCubesDuringLine = line.split(gameMatcher).last().split(";")
            val anyNonPossibleCubesCombo = shownCubesDuringLine.map { subLine ->
                matcherBags
                    .findAll(subLine)
                    .map { it.value }
                    .groupBy { cube -> cube.split(" ").last() }
                    .mapValues { v -> v.value.mapNotNull { it.split(" ").first().toIntOrNull() }.sumOf { it } }
                    .any { entry ->
                        return@any when(entry.key){
                            "red" -> entry.value > RED_CUBES
                            "blue" -> entry.value > BLUE_CUBES
                            "green" -> entry.value > GREEN_CUBES
                            else -> false
                        }
                    }
            }

            if(anyNonPossibleCubesCombo.all { !it }) {
                return@mapNotNull gameId
            }

            return@mapNotNull 0
        }

        return gameIds.sumOf { it }
    }

    fun two(input: List<String>): Int {
        val gameMatcher = "Game \\d+".toRegex()
        val matcherBags = "\\d+\\s+\\w+".toRegex()
        val gameIds = input.map { line ->
            val shownCubesDuringLine = line.split(gameMatcher).last().split(";")
            val drawnCubes = shownCubesDuringLine.map { subLine ->
                matcherBags
                    .findAll(subLine)
                    .map { it.value }
                    .groupBy { cube -> cube.split(" ").last() }
                    .mapValues { v -> v.value.mapNotNull { it.split(" ").first().toIntOrNull() }.sumOf { it } }
            }

            val minRed = drawnCubes.map { draw -> draw.filter { it.key == "red" } }.flatMap { it.values }.max()
            val minBlue = drawnCubes.map { draw -> draw.filter { it.key == "blue" } }.flatMap { it.values }.max()
            val minGreen = drawnCubes.map { draw -> draw.filter { it.key == "green" } }.flatMap { it.values }.max()

            return@map minRed * minGreen * minBlue
        }

        return gameIds.sumOf { it }
    }
}

fun main() {
    val input = Utils.readInput(2)
    val testInput = Utils.readTestInput(2)
    val testTwoInput = Utils.readTestInput(2)

    println("Test: ${Day2().one(testInput)}")
    println("First: ${Day2().one(input)}")

    println("Test: ${Day2().two(testTwoInput)}")
    println("Second: ${Day2().two(input)}")
}
