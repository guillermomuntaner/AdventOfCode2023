fun main() {
    fun part1(input: List<String>): Int = input
        .first().removePrefix("Time:").trim().split("\\s+".toRegex()).map { it.toInt() }
        .zip(input.last().removePrefix("Distance:").trim().split("\\s+".toRegex()).map { it.toInt() })
        .map {
            (0..it.first).count { t -> (it.first - t) * t > it.second }
        }
        .reduce { acc, i -> acc * i }

    fun part2(input: List<String>): Long {
        val time = input.first().removePrefix("Time:").replace(" ", "").toLong()
        val distance = input.last().removePrefix("Distance:").replace(" ", "").toLong()
        val first = (0..time).indexOfFirst { t -> (time - t) * t > distance }
        val last = time - (0..time).reversed().indexOfFirst { t -> (time - t) * t > distance }.toLong()
        return last - first + 1
    }

    val input = readInput("Day06")
    val testInput = readInput("Day06_test")

    checkEquals(288, part1(testInput))
    checkEquals(211904, part1(input))

    checkEquals(71503L, part2(testInput))
    checkEquals(43364472L, part2(input))
}

