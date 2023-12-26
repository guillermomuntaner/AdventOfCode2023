fun main() {
    fun parse(input: List<String>): Pair<CharArray, Map<String, Pair<String, String>>> = Pair(
        input.first().toCharArray(),
        input.drop(2).associate {
            val (node, sides) = it.split(" = ")
            val (left, right) = sides.removePrefix("(").removeSuffix(")").split(", ")
            node to Pair(left, right)
        }
    )

    fun steps(
        start: String,
        instructions: CharArray,
        nodes: Map<String, Pair<String, String>>,
        endCondition: (String) -> Boolean
    ): Int {
        var position = start
        var steps = 0
        while (!endCondition(position)) {
            position = when (instructions[steps % instructions.size]) {
                'L' -> nodes[position]!!.first
                'R' -> nodes[position]!!.second
                else -> throw IllegalStateException()
            }
            steps += 1
        }
        return steps
    }

    fun part1(input: List<String>): Int {
        val (instructions, nodes) = parse(input)
        return steps("AAA", instructions, nodes) { it == "ZZZ" }
    }

    fun part2(input: List<String>): Long {
        val (instructions, nodes) = parse(input)
        return nodes.keys
            .filter { it.endsWith("A") }
            .map {
                steps(it, instructions, nodes) { it.endsWith("Z") }
                    .toLong()
            }
            .reduce { prev, next -> prev.lcm(next) }
    }

    val day = "Day08"
    val input = readInput(day)
    val testInput = readInput("${day}_test")
    val test2Input = readInput("${day}_test2")
    val test3Input = readInput("${day}_test3")

    checkEquals(2, part1(testInput))
    checkEquals(6, part1(test2Input))
    checkEquals(13207, part1(input))

    checkEquals(6, part2(test3Input))
    checkEquals(12324145107121, part2(input))
}