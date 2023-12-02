import kotlin.math.max

fun main() {

    val colorCount = mapOf("red" to 12, "green" to 13, "blue" to 14)

    fun part1(input: List<String>): Int {
        return input
            .mapNotNull { gameInput ->
                val (game, scores) = gameInput.split(": ")
                val gameId = game.removePrefix("Game ").toInt()
                val impossibleScore = scores
                    .split("; ")
                    .any { cubeGrab ->
                        cubeGrab.split(", ").any { cube ->
                            val (countString, color) = cube.split(" ")
                            countString.toInt() > colorCount.getValue(color)
                        }
                    }
                if (impossibleScore) {
                    null
                } else {
                    gameId
                }
            }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input
            .sumOf { gameInput ->
                val (game, scores) = gameInput.split(": ")
                val gameId = game.removePrefix("Game ").toInt()
                scores
                    .split("; ")
                    .fold(emptyMap<String, Int>()) { map, cubeGrab ->
                        var map = map.toMutableMap()
                        cubeGrab.split(", ").forEach {
                            val (countString, color) = it.split(" ")
                            map[color] = max(map[color] ?: 0, countString.toInt())
                        }
                        map
                    }
                    .let {
                        (it["red"] ?: 0) * (it["green"] ?: 0) * (it["blue"] ?: 0)
                    }
            }
    }

    val input = readInput("Day02")
    val testInput = readInput("Day02_test")

    checkEquals(8, part1(testInput))
    checkEquals(2685, part1(input))

    checkEquals(2286, part2(testInput))
    checkEquals(83707, part2(input))
}
