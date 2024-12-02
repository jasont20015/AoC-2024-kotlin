
fun main() {
    fun isIncreasing(input: List<Long>): Boolean {
        var increasingActions = 0
        var decreasingActions = 0
        var first = input[0]
        for(num in input.drop(1)){
            if(first < num){
                increasingActions += 1
            }
            else if(first > num){
                decreasingActions += 1
            }
            first = num
        }
        if(increasingActions > decreasingActions){
            return true
        }
        return false

    }

    fun isSafe(
        numbers: List<Long>
    ): Boolean {
        var safe = true

        val increasing = isIncreasing(numbers)

        var lastNumber = numbers[0]
        for(num in numbers.drop(1)){
            if (increasing) {
                if (!(num - lastNumber <= 3 && num - lastNumber >= 1)) {
                    safe = false
                }
            } else {
                if (!(lastNumber - num <= 3 && lastNumber - num >= 1)) {
                    safe = false
                }
            }
            lastNumber = num
            if (!safe)
                break
        }
        return safe
    }

    fun part1(input: List<String>): Int {
        var amount = 0
        input.forEach{
            val numbers = it.split(' ').map{num -> num.toLong()}
            val safe = isSafe(numbers)
            if(safe)
                amount += 1
        }
        return amount
    }

    fun part2(input: List<String>): Int {
        var amount = 0
        input.forEach{
            val numbers = it.split(' ').map{num -> num.toLong()}
            val safe = isSafe(numbers)
            if(safe) {
                amount += 1
            }
            else{
                for(number in 0..< numbers.size){
                    val numbersCopy = numbers.toMutableList()
                    numbersCopy.removeAt(number)
                    val safeWithRemoved = isSafe(numbersCopy)
                    if(safeWithRemoved) {
                        amount += 1
                        break
                    }
                }
            }
        }
        return amount
    }


    val testInput = readInput("Day02_test")
    part2(testInput).println()
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)
    
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
