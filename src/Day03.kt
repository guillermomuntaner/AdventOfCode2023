typealias Coordinate2D = Pair<Int, Int>
fun main() {

    fun part1(input: List<String>): Int {
        val map: Array<CharArray> = Array(input.size) { input[it].toCharArray() }

        val goodCoordinates = mutableListOf<Coordinate2D>()
        for (y in map.indices) {
            val row = map[y]
            for (x in row.indices) {
                val char = row[x]
                if (!char.isDigit() and (char != '.')) {
                    for (deltaX in -1..1) {
                        for (deltaY in -1..1) if (deltaX != 0 || deltaY != 0) {
                            val adjacentX = x + deltaX
                            val adjacentY = y + deltaY
                            if (map.indices.contains(adjacentY) && row.indices.contains(adjacentX)) {
                                goodCoordinates.add(Coordinate2D(adjacentX, adjacentY))
                            }
                        }
                    }
                }
            }
        }

        var partsNumbersSum = 0
        for (y in map.indices) {
            val row = map[y]
            var partNumber = 0
            var isPart = false
            for (x in row.indices) {
                val char = row[x]
                if (char.isDigit()) {
                    partNumber = 10 * partNumber + char.digitToInt()
                    if (goodCoordinates.contains(Coordinate2D(x, y))) {
                        isPart = true
                    }
                } else {
                    if (partNumber > 0 && isPart) {
                        partsNumbersSum += partNumber
                    }
                    partNumber = 0
                    isPart = false
                }
            }
            if (partNumber > 0 && isPart) {
                partsNumbersSum += partNumber
            }
        }

        return partsNumbersSum
    }

    fun part2(input: List<String>): Int {
        val map: Array<CharArray> = Array(input.size) { input[it].toCharArray() }

        val goodCoordinates = mutableListOf<Pair<Coordinate2D, Coordinate2D>>()
        for (y in map.indices) {
            val row = map[y]
            for (x in row.indices) {
                val char = row[x]
                if (char == '*') {
                    for (deltaX in -1..1) {
                        for (deltaY in -1..1) if (deltaX != 0 || deltaY != 0) {
                            val adjacentX = x + deltaX
                            val adjacentY = y + deltaY
                            if (map.indices.contains(adjacentY) && row.indices.contains(adjacentX)) {
                                goodCoordinates.add(Pair(Coordinate2D(adjacentX, adjacentY), Coordinate2D(x, y)))
                            }
                        }
                    }
                }
            }
        }

        val gearToPartNumbers = mutableMapOf<Coordinate2D, Set<Int>>()
        for (y in map.indices) {
            val row = map[y]
            var partNumber = 0
            val gears = mutableListOf<Coordinate2D>()
            for (x in row.indices) {
                val char = row[x]
                if (char.isDigit()) {
                    partNumber = 10 * partNumber + char.digitToInt()
                    val goodCoordinate = goodCoordinates.firstOrNull { it.first == Coordinate2D(x, y) }
                    if (goodCoordinate != null) {
                        gears.add(goodCoordinate.second)
                    }
                } else {
                    if (partNumber > 0 && gears.isNotEmpty()) {
                        for (gear in gears) {
                            gearToPartNumbers[gear] = gearToPartNumbers.getOrElse(gear) { emptySet() }.plus(partNumber)
                        }
                    }
                    partNumber = 0
                    gears.clear()
                }
            }
            if (partNumber > 0 && gears.isNotEmpty()) {
                for (gear in gears) {
                    gearToPartNumbers[gear] = gearToPartNumbers.getOrElse(gear) { emptySet() }.plus(partNumber)
                }
            }
        }

        return gearToPartNumbers
            .values
            .filter { it.size == 2 }
            .sumOf { it.reduce { acc, i -> acc * i} }
    }

    val input = readInput("Day03")
    val testInput = readInput("Day03_test")

    checkEquals(4361, part1(testInput))
    checkEquals(498559, part1(input))

    checkEquals(467835, part2(testInput))
    checkEquals(0, part2(input))
}

