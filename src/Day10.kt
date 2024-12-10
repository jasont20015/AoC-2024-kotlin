fun main() {

    fun part1(input: List<String>): Int {
        var answer = 0
        val grid = input.map {
            it.map{c ->
                c.digitToInt()
            }
        }
        grid.forEachIndexed outer@ { y, s ->
            s.forEachIndexed inner@ { x, c ->
                if(grid[y][x] != 0){
                    return@inner
                }

                val locationsVisited = mutableSetOf<coordinate>()

                fun dfs(coordinate: coordinate){
                    if(!locationsVisited.add(coordinate))
                        return
                    val nextValue = grid[coordinate.second][coordinate.first] + 1
                    if (nextValue == 10){
                        answer++
                    }
                    fun visit(coordinate: coordinate){
                        if(grid[coordinate.second][coordinate.first] == nextValue) dfs(coordinate)
                    }
                    if( coordinate.first > 0) {
                        visit(coordinate(coordinate.first-1, coordinate.second))
                    }
                    if(coordinate.first < grid[0].lastIndex){
                        visit(coordinate(coordinate.first + 1, coordinate.second))
                    }
                    if(coordinate.second > 0){
                        visit(coordinate(coordinate.first, coordinate.second - 1))
                    }
                    if(coordinate.second < grid.lastIndex){
                        visit(coordinate(coordinate.first, coordinate.second + 1))
                    }
                }
                dfs(coordinate(x,y))
            }
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        var answer = 0
        val grid = input.map {
            it.map{c ->
                c.digitToInt()
            }
        }
        grid.forEachIndexed outer@ { y, s ->
            s.forEachIndexed inner@ { x, c ->
                if(grid[y][x] != 0){
                    return@inner
                }


                fun dfs(coordinate: coordinate){
                    val nextValue = grid[coordinate.second][coordinate.first] + 1
                    if (nextValue == 10){
                        answer++
                    }
                    fun visit(coordinate: coordinate){
                        if(grid[coordinate.second][coordinate.first] == nextValue) dfs(coordinate)
                    }
                    if( coordinate.first > 0) {
                        visit(coordinate(coordinate.first-1, coordinate.second))
                    }
                    if(coordinate.first < grid[0].lastIndex){
                        visit(coordinate(coordinate.first + 1, coordinate.second))
                    }
                    if(coordinate.second > 0){
                        visit(coordinate(coordinate.first, coordinate.second - 1))
                    }
                    if(coordinate.second < grid.lastIndex){
                        visit(coordinate(coordinate.first, coordinate.second + 1))
                    }
                }
                dfs(coordinate(x,y))
            }
        }
        return answer
    }

    val testInput = readInput("Day10_test")
    println(part1(testInput))
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)
    
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
