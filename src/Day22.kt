
fun main() {
    fun Long.mix(other: Long): Long{
        return this.xor(other)
    }
    fun Long.prune(): Long{
        return this % 16777216L
    }
    fun genNewNumber(initial: Long): Long {
        var secret = initial
        var temp = secret
        secret *= 64
        secret = secret.mix(temp)
        secret = secret.prune()

        temp = secret
        secret /= 32
        secret = secret.mix(temp)
        secret = secret.prune()

        temp = secret
        secret *= 2048
        secret = secret.mix(temp)
        secret = secret.prune()

        return secret
    }

    fun part1(input: List<String>): Long {
        var answer = 0L
        input.forEach {
            var number = it.toLong()
            repeat(2000) {
                number = genNewNumber(number)
            }
            answer += number
        }
        return answer
    }

    fun part2(input: List<String>): Long {
        var answer = 0L
        val total = hashMapOf<List<Long>, Long>()
        input.forEach {
            var number = it.toLong()
            var lastDigit = number % 10
            val pattern = mutableListOf<Pair<Long, Long>>()
            repeat(2000){
                number = genNewNumber(number)
                val temp = number % 10
                pattern.add(temp - lastDigit to temp)
                lastDigit = temp
            }
            val seenValues = hashSetOf<List<Long>>()
            for(i in 0..<pattern.size-4){
                val pat = pattern.subList(i, i + 4).map{it.first}
                val value = pattern[i+3].second
                if(pat !in seenValues){
                    seenValues.add(pat)
                    total[pat] = total.getOrDefault(pat, 0) + value
                }
            }
        }
        answer = total.values.maxOrNull() ?: 0

        return answer
    }

    val testInput = readInput("Day22_test")
    // check(part1(testInput) == 37327623L)
    check(part2(testInput) == 23L)
    
    val input = readInput("Day22")
    part1(input).println()
    part2(input).println()
}
