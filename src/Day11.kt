
fun main() {
    val cache = mutableMapOf<Pair<Long, Int>, Long>()


    fun applyRule(number: Long): List<Long> {
        if(number == 0L){
            return listOf(1L)
        }
        else if(number.toString().length % 2 == 0){
            val length = number.toString().length/2
            val leftHalf = number.toString().substring(0, length).toLong()
            val rightHalf = number.toString().substring(length).toLong()
            return listOf(leftHalf, rightHalf)
        }
        else{
            return listOf(number*2024)
        }
    }
    fun applyRules(num: List<Long>): List<Long>{
        val output: MutableList<Long> = mutableListOf()
        num.forEach {
            output.addAll(applyRule(it))
        }
        return output
    }

    fun part1(input: List<String>): Long {
        val inputNums = input[0].split(" ").map { it.toLong() }
        var numbers = inputNums
        repeat(25) {
            numbers = applyRules(numbers)
        }
        return numbers.size.toLong()
    }

    fun calculateCount(amount: Long, iterations: Int): Long {
        if(iterations == 0)
            return 1
        cache[amount to iterations]?.let{return it}
        return applyRule(amount).sumOf{ calculateCount(it, iterations - 1)}
            .also{ cache[amount to iterations] = it}
    }

    fun part2(inp: List<String>): Long {
        val inputNums = inp[0].split(" ").map { it.toLong() }
        return inputNums.sumOf { calculateCount(it, 75) }
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 55312L)
    // check(part2(testInput) == 1)
    
    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
