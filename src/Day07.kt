import java.lang.IllegalStateException

fun main() {
    val strengths = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')

    fun part1(input: List<String>): Int {
        return input
            .map {
                val (hand, bid) = it.split(" ")
                val sameHist = hand.toCharArray().groupBy { it }.values.map { it.count() }.groupingBy { it }.eachCount()
                val type = when {
                    sameHist[5] == 1 -> 1
                    sameHist[4] == 1 -> 2
                    sameHist[3] == 1 && sameHist[2] == 1 -> 3
                    sameHist[3] == 1 -> 4
                    sameHist[2] == 2 -> 5
                    sameHist[2] == 1 -> 6
                    sameHist[1] == 5 -> 7
                    else -> throw IllegalStateException()
                }
                Triple(type, hand.map { strengths.indexOf(it) }, bid.toInt())
            }
            .sortedWith { p0, p1 ->
                val typeCompare = p0.first.compareTo(p1.first)
                if (typeCompare != 0) {
                    typeCompare
                } else {
                    p0.second.zip(p1.second)
                        .firstNotNullOfOrNull {
                            it.first.compareTo(it.second)
                                .takeUnless { it == 0 }
                        } ?: 0
                }
            }
            .reversed()
            .mapIndexed { index, triple ->
                (index + 1) * triple.third
            }
            .sum()
    }

    val adjustedStrengths = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')

    fun part2(input: List<String>): Int {
        return input
            .map {
                val (hand, bid) = it.split(" ")
                val jokerCount = hand.count {  it == 'J' }
                val sameHist = hand.replace("J", "").toCharArray().groupBy { it }.values.map { it.count() }.groupingBy { it }.eachCount()
                 val type = when {
                     jokerCount == 5 -> 1
                     sameHist[5 -  jokerCount] == 1 -> 1
                     jokerCount == 3 && sameHist[1] == 2 -> 2
                     sameHist[4 - jokerCount] == 1 -> 2
                     jokerCount == 2 && sameHist[2] == 1 && sameHist[1] == 1 -> 3
                     jokerCount == 1 && sameHist[2] == 2 -> 3
                     jokerCount == 0 && sameHist[3] == 1  && sameHist[2] == 1 -> 3
                     jokerCount == 2 && sameHist[1] == 3 -> 4
                     sameHist[3 - jokerCount] == 1 -> 4
                     jokerCount == 1 && sameHist[2] == 1 && sameHist[1] == 2 -> 5
                     jokerCount == 0 && sameHist[2] == 2 -> 5
                     jokerCount == 1 && sameHist[1] == 4 -> 6
                     jokerCount == 0 && sameHist[2] == 1 -> 6
                     sameHist[1] == 5 -> 7
                     else -> throw IllegalStateException()
                }
                Triple(type, hand.map { adjustedStrengths.indexOf(it) }, bid.toInt())
            }
            .sortedWith { p0, p1 ->
                val typeCompare = p0.first.compareTo(p1.first)
                if (typeCompare != 0) {
                    typeCompare
                } else {
                    p0.second.zip(p1.second)
                        .firstNotNullOfOrNull {
                            it.first.compareTo(it.second)
                                .takeUnless { it == 0 }
                        } ?: 0
                }
            }
            .reversed()
            .mapIndexed { index, triple ->
                (index + 1) * triple.third
            }
            .sum()
    }

    val day = "Day07"
    val input = readInput(day)
    val testInput = readInput("${day}_test")

    checkEquals(6440, part1(testInput))
    checkEquals(248105065, part1(input))

    checkEquals(5905, part2(testInput))
    checkEquals(249515436, part2(input))
}

