import kotlin.collections.*

fun main() {
    fun getCoordinate(x: Int, y: Int, input: List<String>): Char?{
        val width = input[0].length
        val height = input.size
        if (x < 0 || y < 0 || x >= width || y >= height)
            return null
        else
            return input[y][x]
    }

    fun directionToCoords(directionInt: Int): coordinate = when (directionInt){
        0 -> 0 to -1
        1 -> 1 to 0
        2 -> 0 to 1
        3 -> -1 to 0
        else -> {0 to 0}
    }

    fun createRegion(x: Int, y: Int, c: Char?, seen: MutableSet<coordinate>, input: List<String>, part2:Boolean = false): coordinate {
        if (seen.contains(coordinate(x, y)) || getCoordinate(x, y, input) != c) {
            return coordinate(0, 0)
        }
        val directions = arrayOf(0, 1, 2, 3)
        val coords = directions.map { directionToCoords(it) }
        seen.add(coordinate(x, y))
        var area = 1
        var perimeter = 0
        for ((newX, newY) in coords) {
            if (getCoordinate(x + newX, y + newY, input) != c) {
                perimeter++
                if(part2) {
                    if (newX == 0 && newY == 1 && getCoordinate(x + 1, y, input) == c && getCoordinate(x + 1, y + 1, input) != c) {
                        perimeter--
                    }
                    if (newX == 0 && newY == -1 && getCoordinate(x + 1, y, input) == c && getCoordinate(x + 1, y - 1, input) != c) {
                        perimeter--
                    }
                    if (newX == 1 && newY == 0 && getCoordinate(x, y + 1, input) == c && getCoordinate(x + 1, y + 1, input) != c) {
                        perimeter--
                    }
                    if (newX == -1 && newY == 0 && getCoordinate(x, y + 1, input) == c && getCoordinate(x - 1,y + 1,input) != c) {
                        perimeter--
                    }
                }
            } else {
                val result = createRegion(x + newX, y + newY, c, seen, input, part2)
                area += result.first
                perimeter += result.second
            }
        }
        return coordinate(area, perimeter)
    }

    fun part1(input: List<String>): Int {
        val width = input[0].length
        val height = input.size


        var total = 0
        val seen = mutableSetOf<coordinate>()
        for (y in 0 until height) {
            for (x in 0 until width) {
                val character = getCoordinate(x, y, input)
                val (area, perimeter) = createRegion(x, y, character, seen, input)
                total += area * perimeter
            }
        }
        return total
    }

    fun part2(input: List<String>): Int {
        val width = input[0].length
        val height = input.size


        var total = 0
        val seen = mutableSetOf<coordinate>()
        for (y in 0 until height) {
            for (x in 0 until width) {
                val character = getCoordinate(x, y, input)
                val (area, perimeter) = createRegion(x, y, character, seen, input, true)
                total += area * perimeter
            }
        }
        return total
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 1930)
    check(part2(testInput) == 1206)
    
    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
