fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            10 * it.firstNotNullOf { it.digitToIntOrNull() } +
                    it.reversed().firstNotNullOf { it.digitToIntOrNull() }
        }
    }

    fun part2(input: List<String>): Int {
        val spelledDigits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        fun String.spelledDigitToIntOrNullAt(startIndex: Int): Int? {
            val substring = substring(startIndex)
            return spelledDigits
                .indexOfFirst { substring.startsWith(it) }
                .takeUnless { it == -1 }
                ?.plus(1)
        }

        return input.sumOf { line ->
            line.indices
                .mapNotNull { i -> line[i].digitToIntOrNull() ?: line.spelledDigitToIntOrNullAt(i) }
                .let { 10 * it.first() + it.last() }
        }
    }

    val testInput = readInput("Day01_test")
    checkEquals(142, part1(testInput))

    val test2Input = readInput("Day01_test2")
    checkEquals(281, part2(test2Input))

    val input = readInput("Day01")
    checkEquals(56042, part1(input))
    checkEquals(55358, part2(input))
}
