fun main() {
    fun isTargetReachable(target: Long, numbers: List<Long>, index: Int, current: Long, allowConcat: Boolean = false): Boolean{
        val i = index+1
        if(i >= numbers.size)
            return current == target
        val nextNumber = numbers.get(i)
        var result = isTargetReachable(target, numbers, i, current+nextNumber, allowConcat) ||
                isTargetReachable(target, numbers, i, current*nextNumber, allowConcat)
        if(allowConcat){
            val concatenated: Long = "$current$nextNumber".toLong()
            if(!result){
                result = isTargetReachable(target, numbers, i, concatenated, allowConcat)
            }
        }
        return result
    }

    fun part1(input: List<String>): Long {
        var answer = 0L
        input.forEach {
            val target = it.substringBefore(':').toLong()
            val numbers = it.substringAfter(':').trim().split(" ").map { num -> num.toLong() }

            if(isTargetReachable(target, numbers, 0, numbers.get(0))){
                answer+=target
            }
        }
        return answer

    }

    fun part2(input: List<String>): Long {
        var answer = 0L
        input.forEach {
            val target = it.substringBefore(':').toLong()
            val numbers = it.substringAfter(':').trim().split(" ").map { num -> num.toLong() }

            if(isTargetReachable(target, numbers, 0, numbers.get(0), true )){
                answer+=target
            }
        }
        return answer
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)
    
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
