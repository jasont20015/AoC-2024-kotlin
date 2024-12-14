
fun main() {
    fun moveRobot(w: Int,h: Int,coordinate: coordinate,velocity:coordinate, iterations:Int): coordinate {
        val currentX = coordinate.first
        val currentY = coordinate.second
        val velX = velocity.first
        val velY = velocity.second

        var newX = (currentX+(velX*iterations)) % w
        var newY = (currentY+(velY*iterations)) % h

        if (newX < 0) {
            newX += w
        }
        if (newY < 0) {
            newY += h
        }

        return coordinate(newX, newY)
    }
    fun countQuadrants(w:Int,h:Int,robotList: List<coordinate>): List<Int>{
        val middleVertical = h/2
        val middleHorizontal = w/2

        val quad1 = robotList.count { it.second < middleVertical && it.first < middleHorizontal }
        val quad2 = robotList.count { it.second > middleVertical && it.first < middleHorizontal }
        val quad3 = robotList.count { it.second < middleVertical && it.first > middleHorizontal }
        val quad4 = robotList.count { it.second > middleVertical && it.first > middleHorizontal }

        return listOf(quad1, quad2, quad3, quad4)
    }

    fun part1(input: List<String>, testinput: Boolean = false): Long {
        var w = 101
        var h = 103
        if(testinput){
            w = 11
            h = 7
        }

        val robotList = mutableListOf<Pair<coordinate, coordinate>>()
        val finalRobotList = mutableListOf<coordinate>()

        input.forEach {
            val optionsSplit = it.split(" ")
            val position = optionsSplit.first().substringAfter("=")
            val velocity = optionsSplit.last().substringAfter("=")
            val posInts = position.split(",").map { coord -> coord.toInt() }
            val velInts = velocity.split(",").map { coord -> coord.toInt() }
            robotList.add(Pair(coordinate(posInts.first(), posInts.last()), coordinate(velInts.first(), velInts.last())))
        }
        robotList.forEach { robot ->
            val finalCoord: coordinate
            finalCoord = moveRobot(w,h,robot.first, robot.second, 100)
            finalRobotList.add(finalCoord)
        }

        val numberOfRobots = countQuadrants(w,h,finalRobotList)

        var answer = 1L
        numberOfRobots.forEach {
            answer *=  if (it > 0) it else 1
        }
        return answer
    }

    fun part2(input: List<String>, testinput: Boolean = false): Int {
        var w = 101
        var h = 103
        if(testinput){
            w = 11
            h = 7
        }

        var answer = 1
        val robotList = mutableListOf<Pair<coordinate, coordinate>>()

        input.forEach {
            val optionsSplit = it.split(" ")
            val position = optionsSplit.first().substringAfter("=")
            val velocity = optionsSplit.last().substringAfter("=")
            val posInts = position.split(",").map { coord -> coord.toInt() }
            val velInts = velocity.split(",").map { coord -> coord.toInt() }
            robotList.add(Pair(coordinate(posInts.first(), posInts.last()), coordinate(velInts.first(), velInts.last())))
        }

        while(true) {
            val newList = mutableListOf<coordinate>()
            robotList.forEach { robot ->
                val finalCoord: coordinate
                finalCoord = moveRobot(w, h, robot.first, robot.second, answer)
                newList.add(finalCoord)
            }
            val posMap = newList.toSet().size == robotList.size

            if(posMap) {
                return answer
            }

            answer++
        }
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput, true) == 12L)
    check(part2(testInput) == 1)
    
    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
