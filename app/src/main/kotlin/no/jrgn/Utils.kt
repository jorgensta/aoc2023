package no.jrgn

import java.io.File

class Utils {

    companion object {
        fun readInput(day: Int) = File("app/src/main/resources/day$day", "input.txt")
            .readLines()

        fun readTestInput(day: Int) = File("app/src/main/resources/day$day", "input_test.txt")
            .readLines()

        fun readTestInput2(day: Int) = File("app/src/main/resources/day$day", "input_test_2.txt")
            .readLines()
    }
}
