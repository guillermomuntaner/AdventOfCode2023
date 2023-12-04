import kotlin.math.pow

fun main() {
    fun parseLine(line: String): Set<Int> = line
        .split(": ").last().split(" | ")
        .map { numbers -> numbers.trim().split("\\s+".toRegex()).map { it.toInt() }.toSet() }
        .reduce { acc, ints -> acc.intersect(ints) }

    fun part1(input: List<String>): Int = input
        .map(::parseLine)
        .sumOf { line ->
            line.takeIf { it.isNotEmpty() }?.let { 2.0.pow(it.size - 1).toInt() } ?: 0
        }

    fun part2(input: List<String>): Int {
        val scratchcards = input.map(::parseLine)
        fun score(index: Int): Int = 1 + (0 until scratchcards[index].size).sumOf { score(index + 1 + it) }
        return scratchcards.indices.sumOf(::score)
    }

    val input = readInput("Day04")
    val testInput = readInput("Day04_test")

    checkEquals(13, part1(testInput))
    checkEquals(23673, part1(input))

    checkEquals(30, part2(testInput))
    checkEquals(12263631, part2(input))
}

