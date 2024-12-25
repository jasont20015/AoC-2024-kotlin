fun main() {
    fun part1(input: List<String>): Int {
        val locks = mutableListOf<List<String>>()
        val keys = mutableListOf<List<String>>()
        val locksSizes = mutableListOf<List<Int>>()
        val keysSizes = mutableListOf<List<Int>>()
        val holeSize = 5
        val inputSeperated = input.joinToString("\n").split("\n\n")

        inputSeperated.forEach {
            if(it.startsWith("#####")){
                locks.add(it.split("\n"))
            } else{
                keys.add(it.split("\n"))
            }
        }
        locks.forEach {
            val currentLock = mutableListOf<Int>()
            for(x in 0..<it[0].length) {
                for (y in 0..<it.size) {
                    if (it[y][x] == '.') {
                        currentLock.add(y-1)
                        break
                    }
                }
            }
            locksSizes.add(currentLock)
        }
        keys.forEach {
            val currentKey = mutableListOf<Int>()
            for(x in 0..<it[0].length) {
                for (y in 0..<it.size) {
                    if (it[y][x] == '#') {
                        currentKey.add(holeSize-(y-1))
                        break
                    }
                }
            }
            keysSizes.add(currentKey)
        }
        var answer = 0
        locksSizes.forEach { l ->
            keysSizes.forEach { k ->
                var keyFits = true
                for(i in 0..<k.size){
                    if(holeSize-k[i]-l[i] < 0){
                        keyFits = false
                    }

                }
                if(keyFits){
                    answer += 1
                }
            }
        }


        return answer
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day25_test")
    check(part1(testInput) == 3)
    // check(part2(testInput) == 1)

    val input = readInput("Day25")
    part1(input).println()
    part2(input).println()
}
