
fun main() {
    fun part1(input: List<String>): Long {
        val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        val fullInput = input.joinToString("")
        val matches = regex.findAll(fullInput)
        var answer = 0L
        matches.forEach {
            answer+= it.groupValues[1].toLong() * it.groupValues[2].toLong()
        }
        return answer
    }

    fun part2(input: List<String>): Long {
        val fullInput = input.joinToString("")
        val regexFilter = Regex("don't\\(\\)(.*?)do\\(\\)")
        val newInput = regexFilter.replace(fullInput, "")
        val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        newInput.println()
        val matches = regex.findAll(newInput)
        var answer = 0L
        matches.forEach {
            println(it.groupValues)
            answer+= it.groupValues[1].toLong() * it.groupValues[2].toLong()
        }
        return answer
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161L)
    check(part2(testInput) == 48L)
    
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
