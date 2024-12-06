
fun main() {
    fun moveForward(coordinates:Pair<Int, Int>, direction: Int): Pair<Int, Int> {
        var currentX = coordinates.first
        var currentY = coordinates.second
        if(direction == 0){
            currentY-=1
        }
        else if(direction == 1){
            currentX+=1
        }
        else if(direction==2){
            currentY+=1
        }
        else if(direction == 3){
            currentX-=1
        }
        return Pair<Int, Int>(currentX, currentY)
    }
    fun guardOnMap(input: List<String>, coordinates: Pair<Int, Int>): Boolean{
        val (x, y) = coordinates
        if(x > input[0].length-1 || x < 0 || y > input.size-1 || y < 0){
            return false
        }
        return true
    }

    fun guardObstructed(input: List<String>, coordinates: Pair<Int, Int>, direction: Int, customObstruction: Pair<Int, Int> = Pair(-1,-1)): Boolean{
        val newCoordinates = moveForward(coordinates, direction)
        if(!guardOnMap(input, newCoordinates)){
            return false
        }
        if(input[newCoordinates.second][newCoordinates.first] == '#')
            return true
        if(newCoordinates == customObstruction)
            return true
        return false
    }
    fun getInitialCoords(input: List<String>): Pair<Int, Int> {
        var x = 0
        var y = 0
        input.forEachIndexed { index, s ->
            s.forEachIndexed { innerindex, c ->
                if(c == '^') {
                    x = innerindex
                    y = index
                }
            }
        }
        return Pair(x, y)
    }


    fun part1(input: List<String>): Int {
        var direction = 0 //0 = up, 1 = right, 2 = down, 3 = left
        val visitedPositions = mutableSetOf<Pair<Int, Int>>()
        var coordinates = getInitialCoords(input)
        while(guardOnMap(input, coordinates)){
            visitedPositions.add(coordinates)
            while(guardObstructed(input, coordinates, direction)){
                direction += 1
                if(direction == 4){
                    direction = 0
                }
            }
            coordinates = moveForward(coordinates, direction)
        }
        return visitedPositions.count()
    }

    fun part2(input: List<String>): Int {
        var direction: Int  //0 = up, 1 = right, 2 = down, 3 = left
        val visitedPositions = mutableSetOf<Pair<Int, Pair<Int, Int>>>()
        var coordinates: Pair<Int, Int>
        var answer = 0
        var customObstruction: Pair<Int, Int>
        input.forEachIndexed { y, s ->
            s.forEachIndexed inner@{ x, c ->
                if(c == '#'){
                    return@inner
                }
                customObstruction = Pair(x, y)
                coordinates = getInitialCoords(input)
                direction = 0
                visitedPositions.clear()
                while(guardOnMap(input, coordinates)){
                    visitedPositions.add(Pair(direction, coordinates))
                    while(guardObstructed(input, coordinates, direction, customObstruction)){
                        direction += 1
                        if(direction == 4){
                            direction = 0
                        }
                    }
                    coordinates = moveForward(coordinates, direction)
                    if(visitedPositions.contains(Pair(direction, coordinates))){
                        answer += 1
                        break
                    }
                }
            }
        }

        return answer
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)
    
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
