
fun main() {
    fun directionFromStr(cardinal: String): Pair<Int, Int> {
        val directions = mapOf( //Y, X, not X, Y
            "NW" to Pair(-1, -1),
            "N" to Pair(-1, 0),
            "NE" to Pair(-1, 1),
            "W" to Pair(0, -1),
            "E" to Pair(0, 1),
            "SW" to Pair(1, -1),
            "S" to Pair(1, 0),
            "SE" to Pair(1, 1)
        )
        return directions.getOrDefault(cardinal, Pair(0,0))

    }

    fun part1(input: List<String>): Int {
        var occurrences = 0
        val maxX = input[0].length-1
        val maxY = input.size-1
        val possibleDirections = listOf("NW", "N", "NE", "W", "E", "SW", "S", "SE")
        input.forEachIndexed { index, line ->
            line.forEachIndexed{
                horizontalIndex, c ->
                    if(c == 'X'){
                        val directions: MutableList<String> = possibleDirections.toMutableList()
                        if(horizontalIndex < 3){
                            directions.removeAll { it.contains("W") }
                        }
                        if(index < 3){
                            directions.removeAll { it.contains("N") }
                        }
                        if(horizontalIndex > maxX-3){
                            directions.removeAll { it.contains("E") }
                        }
                        if(index > maxY-3){
                            directions.removeAll { it.contains("S") }
                        }
                        directions.forEach {
                            val directionPair = directionFromStr(it)
                            if(input[directionPair.first+index][directionPair.second+horizontalIndex] == 'M' &&
                                input[directionPair.first*2+index][directionPair.second*2+horizontalIndex] == 'A' &&
                                input[directionPair.first*3+index][directionPair.second*3+horizontalIndex] == 'S')
                                occurrences++
                        }

                    }

            }
        }

        return occurrences
    }

    fun isMas(a: Char, c: Char): Boolean {
        return (a == 'M' && c == 'S') || (a == 'S' && c == 'M')
    }

    fun part2(input: List<String>): Int {
        var occurrences = 0
        val maxX = input[0].length-1
        val maxY = input.size-1
        input.forEachIndexed { index, line ->
            line.forEachIndexed inner@ { horizontalIndex, c ->
                if(c == 'A'){
                    if(horizontalIndex < 1 || index < 1 || horizontalIndex > maxX-1 || index > maxY-1){
                        return@inner
                    }
                    val NW = input[index-1][horizontalIndex-1]
                    val SE = input[index+1][horizontalIndex+1]

                    val NE = input[index-1][horizontalIndex+1]
                    val SW = input[index+1][horizontalIndex-1]
                    if(isMas(NW, SE) && isMas(NE, SW)){
                        occurrences += 1
                    }

                }

            }
        }

        return occurrences
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)
    
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
