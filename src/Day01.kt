
fun main() {
    fun part1(input: List<String>): Int {
        var diffence = 0
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()
        for(line in input){
            firstList.add(line.split("   ")[0].toInt())
            secondList.add(line.split("   ")[1].toInt())
        }
        firstList.sort()
        secondList.sort()
        for(i in 0..firstList.size-1){
            if(firstList[i] >= secondList[i]){
                diffence += firstList[i] - secondList[i]
            }
            else{
                diffence += secondList[i] - firstList[i]
            }
        }
        return diffence
    }

    fun part2(input: List<String>): Int {
        var similarity = 0
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()
        for(line in input){
            firstList.add(line.split("   ")[0].toInt())
            secondList.add(line.split("   ")[1].toInt())
        }
        firstList.forEach{
            val count = secondList.count { num -> num == it }
            similarity += count*it
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
