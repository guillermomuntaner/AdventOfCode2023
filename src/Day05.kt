import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Long {
        val seeds = input.first().removePrefix("seeds: ").split(" ").map { it.toLong() }
        val iterator = input.drop(2).iterator()
        // Mote: Switched to use ranges, as storing all elements was causing OOM
        val maps = mutableListOf<Map<Pair<Long, Long>, Long>>()
        var map = mutableMapOf<Pair<Long, Long>,Long>()
        while (iterator.hasNext()) {
            iterator.next()
            while (iterator.hasNext()) {
                val line = iterator.next()
                if (line.isNotBlank()) {
                    val (to, from, size) = line.split(" ").map { it.toLong() }
                    map[Pair(from, from + size)] = to
                } else {
                    break
                }
            }
            maps.add(map)
            map = mutableMapOf()
        }

        return seeds.minOf {
            maps.fold(it) { acc, map ->
                map.keys
                    .firstOrNull { (it.first until it.second).contains(acc) }
                    ?.let { map[it]!! + (acc - it.first) }
                    ?: acc
            }
        }
    }

    fun part2(input: List<String>): Long {
        val seeds = input.first().removePrefix("seeds: ").split(" ").map { it.toLong() }
            .chunked(2).map { it.first() until (it.first() + it.last()) }
        val iterator = input.drop(2).iterator()
        // Mote: Switched to use ranges, as storing all elements was causing OOM
        val maps = mutableListOf<Map<LongRange, Long>>()
        var map = mutableMapOf<LongRange,Long>()
        while (iterator.hasNext()) {
            iterator.next()
            while (iterator.hasNext()) {
                val line = iterator.next()
                if (line.isNotBlank()) {
                    val (to, from, size) = line.split(" ").map { it.toLong() }
                    map[from until (from + size)] = to
                } else {
                    break
                }
            }
            maps.add(map)
            map = mutableMapOf()
        }

        return seeds.minOf {
            maps.fold(listOf(it)) { acc, map ->
                val fromRanges = acc.toMutableList()
                val toRanges = mutableListOf<LongRange>()
                rangeLoop@ while (fromRanges.isNotEmpty()) {
                    val fromRange = fromRanges.removeAt(0)
                    for ((mapFromRange, to) in map.entries) {

                        val intersection = max(fromRange.first, mapFromRange.first) .. min(fromRange.last, mapFromRange.last)
                        if (intersection.isEmpty().not()) {

                            val mappedFrom = intersection.first - mapFromRange.first + to
                            val mappedTo = intersection.last - mapFromRange.first + to
                            val mappedRange = mappedFrom .. mappedTo
                            toRanges.add(mappedRange)

                            val below = fromRange.first..< min(fromRange.last, mapFromRange.first)
                            if (below.isEmpty().not()) {
                                fromRanges.add(below)
                            }

                            val above = (min(fromRange.last, mapFromRange.last) + 1) .. fromRange.last
                            if (above.isEmpty().not()) {
                                fromRanges.add(above)
                            }

                            continue@rangeLoop
                        }
                    }
                    toRanges.add(fromRange)
                }
                toRanges
            }.minOf { it.first }
        }
    }

    val input = readInput("Day05")
    val testInput = readInput("Day05_test")

    checkEquals(35L, part1(testInput))
    checkEquals(107430936L, part1(input))

    checkEquals(46L, part2(testInput))
    checkEquals(23738616L, part2(input))
}

