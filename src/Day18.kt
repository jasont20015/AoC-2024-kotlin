
fun main() {
    fun maze(points: List<coordinate>, endLocation: coordinate): Int?{
        val start = (0 to 0)
        val end = endLocation

        val seen = mutableSetOf<coordinate>()
        val toVisit = mutableListOf(Pair(start, 0))

        while(toVisit.isNotEmpty()) {
            val (currentPoint, currentDistance) = toVisit.removeAt(0)
            if(currentPoint in seen){
                continue
            }
            if(currentPoint == end){
                return currentDistance
            }
            val directions = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
            for((dx,dy) in directions){
                val newPoint = coordinate(currentPoint.first + dx, currentPoint.second + dy)
                if(newPoint.first in 0..end.first && newPoint.second in 0..end.second && newPoint !in seen && newPoint !in points)
                    toVisit.add(Pair(newPoint, currentDistance+1))
            }
            seen.add(currentPoint)
        }

        return null
    }

    fun part1(input: List<String>, test: Boolean=false): Int {
        var size = coordinate(70,70)
        var simulationSize = 1024
        val corruptedCoordinates = mutableListOf<coordinate>()
        if(test){
            size = coordinate(6,6)
            simulationSize = 12
        }
        for(i in 0..<simulationSize){
            val coords = input[i].split(",").map{it.toInt()}
            corruptedCoordinates.add(coords.first() to coords.last())
        }
        val answer = maze(corruptedCoordinates, size)
        if(answer != null)
            return answer
        return -1
    }

    fun part2(input: List<String>, test:Boolean=false): String {
        var size = coordinate(70,70)
        var simulationSize = 1024
        val corruptedCoordinates = mutableListOf<coordinate>()
        if(test){
            size = coordinate(6,6)
            simulationSize = 12
        }
        for(i in 0..<input.size){
            val coords = input[i].split(",").map{it.toInt()}
            corruptedCoordinates.add(coords.first() to coords.last())
        }
        for(i in 0..<corruptedCoordinates.size){
            if(maze(corruptedCoordinates.subList(0, i), size) == -1){
                val coordinates = corruptedCoordinates.get(i)
                return "${coordinates.first},${coordinates.second}"
            }
        }
        return "maze always reachable"
    }

    val testInput = readInput("Day18_test")
    check(part1(testInput, true) == 22)
    println(part2(testInput, true))
    check(part2(testInput, true) == "6,1")
    
    val input = readInput("Day18")
    part1(input).println()
    part2(input).println()
}
