
fun main() {
    val towelsTried = HashMap<String, Long>()
    fun towelsFit(towels: List<String>, output: String): Long{
        if (output.isEmpty()) return 1L
        towelsTried[output]?.let { return it }
        return towels.sumOf {
            if (output.startsWith(it))
                towelsFit (towels, output.drop(it.length))
            else
                0
        }.also { towelsTried[output] = it }

    }

    fun part1(input: List<String>): Int {
        val towels = input[0].split(",").map{it.trim()}
        val outputs = input.drop(2)

        towelsTried.clear()

        return outputs.count { towelsFit(towels, it) >= 1 }
    }

    fun part2(input: List<String>): Long {
        val towels = input[0].split(",").map{it.trim()}
        val outputs = input.drop(2)

        return outputs.sumOf { towelsFit(towels, it) }
    }

    val testInput = readInput("Day19_test")
    println(part1(testInput))
    check(part1(testInput) == 6)
    check(part2(testInput) == 16L)
    
    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}
