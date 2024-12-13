
fun main() {
    fun part1(input: List<String>): Int {
        val newInput = input.joinToString("\n")
        val correctlySplitInput = newInput.split("\n\n")
        var answer = 0
        correctlySplitInput.forEach {
            val options = it.split("\n")
            val ax = options.first().substringAfter("X").substringBefore(",").toInt()
            val ay = options.first().substringAfter("Y").toInt()

            val bx = options[1].substringAfter("X").substringBefore(",").toInt()
            val by = options[1].substringAfter("Y").toInt()

            val goalx = options[2].substringAfter("X=").substringBefore(",").toInt()
            val goaly = options[2].substringAfter("Y=").toInt()
            var minTokens = Int.MAX_VALUE
            for(a in 0..100){
                for(b in 0..100){
                    if(ax*a+bx*b==goalx && ay*a+by*b==goaly){
                        if(minTokens > 3*a+b){
                            minTokens = 3*a+b
                        }
                    }
                }
            }
            answer += if (minTokens != Int.MAX_VALUE) minTokens else 0
        }
        return answer
    }

    fun part2(input: List<String>): Long {
        val newInput = input.joinToString("\n")
        val correctlySplitInput = newInput.split("\n\n")
        var answer = 0L
        correctlySplitInput.forEach {
            val options = it.split("\n")
            val ax = options.first().substringAfter("X").substringBefore(",").toLong()
            val ay = options.first().substringAfter("Y").toLong()

            val bx = options[1].substringAfter("X").substringBefore(",").toLong()
            val by = options[1].substringAfter("Y").toLong()

            val goalx = options[2].substringAfter("X=").substringBefore(",").toLong() +10_000_000_000_000
            val goaly = options[2].substringAfter("Y=").toLong() + 10_000_000_000_000
            val b = (goalx*ay-goaly*ax).floorDiv(ay*bx-by*ax)
            val a = (goalx*by-goaly*bx).floorDiv(by*ax-bx*ay)
            if(ax*a+bx*b == goalx && ay*a+by*b==goaly){
                answer += 3*a+b
            }
        }
        return answer
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 480)
    check(part2(testInput) == 875318608908L)
    
    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
