import kotlin.math.abs

fun main() {
    fun getLists(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()
        for (line in input) {
            firstList.add(line.split("   ")[0].toInt())
            secondList.add(line.split("   ")[1].toInt())
        }
        return Pair(firstList, secondList)
    }

    fun part1(input: List<String>): Int {
        val triple = getLists(input)
        var difference = 0
        val firstList = triple.first
        val secondList = triple.second
        firstList.sort()
        secondList.sort()
        for(i in 0..<firstList.size){
            difference += abs(firstList[i] - secondList[i])
        }
        return difference
    }

    fun part2(input: List<String>): Int {
        val triple = getLists(input)
        var similarity = 0
        val firstList = triple.first
        val secondList = triple.second
        val counts = HashMap<Int, Int>()
        secondList.forEach {
            counts.merge(it, 1, Int::plus)
        }

        firstList.forEach{
            val count = counts[it]
            similarity += count?.times(it) ?: 0
        }

        return similarity
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
