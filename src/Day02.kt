
fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1)
    // check(part2(testInput) == 1)
    
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
