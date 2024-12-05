
fun main() {

    fun isOrderCorrect(input: List<Int>, prerequisites: Map<Int, List<Int>>): Boolean {
        val processedNumbers: MutableList<Int> = mutableListOf()
        var rightOrder = true
        input.forEach {
            if (prerequisites.containsKey(it)) {
                prerequisites.get(it)?.let { prereqs ->
                    if (!processedNumbers.containsAll(prereqs.filter { input.contains(it) })) {
                        rightOrder = false
                    }
                }
            }
            processedNumbers.add(it)
        }
        return rightOrder
    }

    fun part1(input: List<String>): Int {
        val prereqs = mutableMapOf<Int, MutableList<Int>>()
        var answer = 0
        input.takeWhile { it.contains("|") }.forEach {
            val (x, y) = it.split("|").map{it.toInt()}
            if(prereqs.containsKey(y)){
                val list = prereqs.getValue(y)
                list.add(x)
                prereqs.set(y, list)
            }else{
                val list = mutableListOf(x)
                prereqs.set(y, list)
            }
        }

        input.forEach {
            if(!it.contains("|") && it != ""){
                val numbers = it.split(",").map{it.toInt()}
                if(isOrderCorrect(numbers, prereqs)){
                    answer += numbers.get((numbers.size/2))
                }

            }
        }

        return answer
    }
    fun sortUpdate(update: List<Int>, rules: Set<Pair<Int, Int>>): List<Int> {
        return update.sortedWith { page1, page2 ->
            when {
                Pair(page1, page2) in rules -> -1
                Pair(page2, page1) in rules -> 1
                else -> 0
            }
        }
    }
    fun part2(input: List<String>): Int {
        val prereqs = mutableMapOf<Int, MutableList<Int>>()
        var answer = 0

        input.takeWhile { it.contains("|") }.forEach {
            val (x, y) = it.split("|").map{it.toInt()}
            if(prereqs.containsKey(y)){
                val list = prereqs.getValue(y)
                list.add(x)
                prereqs.set(y, list)
            }else{
                val list = mutableListOf(x)
                prereqs.set(y, list)
            }
        }
        val newInput = input.filter {
            !it.contains("|") && it != "" && !isOrderCorrect(it.split(",").map{it.toInt()}, prereqs)
        }
        //can't figure it out using normal prereqs, time for another approach
        val rules = mutableSetOf<Pair<Int, Int>>()
        for(line in input){
            if(line.contains("|")) {
                val (a, b) = line.trim().split("|").map { it.toInt() }
                rules.add(Pair(a, b))
            }
        }


        newInput.forEach {
            val list = it.split(",").map{values -> values.toInt()}
            answer += sortUpdate(list, rules).get(list.size/2)
        }


        return answer
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    println(part2(testInput))
    check(part2(testInput) == 123)
    
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
