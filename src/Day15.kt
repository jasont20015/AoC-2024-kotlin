import kotlinx.coroutines.newSingleThreadContext

fun main() {
    fun instructionToCoordinate(instruction: Char): coordinate {
        val coordinate = when (instruction) {
            '^' -> coordinate(0, -1)
            '>' -> coordinate(1, 0)
            'v' -> coordinate(0, 1)
            '<' -> coordinate(-1, 0)
            else -> coordinate(0,0)
        }
        return coordinate
    }

    fun tryMove(currentPosition: coordinate, instruction: coordinate, boxes: HashSet<coordinate>, wall: HashSet<coordinate>): Pair<coordinate, HashSet<coordinate>>{
        val currentX = currentPosition.first
        val currentY = currentPosition.second
        var newX = instruction.first + currentX
        var newY = instruction.second + currentY

        var canMove = true
        if(wall.contains(coordinate(newX, newY))) {
            canMove = false
        }
        while(boxes.contains(coordinate(newX, newY)) || wall.contains(coordinate(newX, newY))){
            if(wall.contains(coordinate(newX, newY))){
                canMove = false
                break
            }
            newX += instruction.first
            newY += instruction.second

        }
        if(!canMove){
            return Pair(currentPosition, boxes)
        }
        val firstOpenCoordinate = coordinate(newX, newY)
        newX = instruction.first + currentX
        newY = instruction.second + currentY

        if(boxes.contains(coordinate(newX, newY))){
            boxes.remove(coordinate(newX, newY))
            boxes.add(firstOpenCoordinate)
        }
        return Pair(coordinate(newX, newY), boxes)
    }

    fun instructionToCoordinatePt2(instruction: Char): coordinate {
        val coordinate = when (instruction) {
            '^' -> coordinate(-1, 0)
            '>' -> coordinate(0, 1)
            'v' -> coordinate(1, 0)
            '<' -> coordinate(0, -1)
            else -> coordinate(0,0)
        }
        return coordinate
    }

    fun part1(input: List<String>): Int {
        val inputAsString = input.joinToString("\n")
        val inputSplit = inputAsString.split("\n\n")
        val grid = inputSplit[0].split("\n")
        val instructions = inputSplit[1].replace("\n", "")

        var boxPositions: HashSet<coordinate> = hashSetOf()
        var currentPosition = coordinate(0,0)
        val wall: HashSet<coordinate> = hashSetOf()
        grid.forEachIndexed { y, s ->
            s.forEachIndexed inner@{x, c ->
                when(c) {
                    '#' -> wall.add(coordinate(x,y))
                    'O' -> boxPositions.add(coordinate(x,y))
                    '@' -> currentPosition = coordinate(x,y)
                }
            }
        }
        instructions.forEach {
            val currentInstruction = instructionToCoordinate(it)
            val newPositions =  tryMove(currentPosition, currentInstruction, boxPositions, wall)
            currentPosition = newPositions.first
            boxPositions = newPositions.second
        }
        var answer = 0
        boxPositions.forEach {
            val y = it.second
            val x = it.first
            answer += (100*y)+x
        }

        return answer
    }

    fun part2(input: List<String>): Int {
        val inputAsString = input.joinToString("\n")
        val inputSplit = inputAsString.split("\n\n")

        val grid = inputSplit[0].replace("#", "##").replace("O", "[]").replace(".", "..").replace("@", "@.").lines()
        val moves = inputSplit[1].replace("\n", "")

        var boxLefts = mutableSetOf<coordinate>()
        val walls = mutableSetOf<coordinate>()
        var robot: coordinate = (0 to 0)

        grid.forEachIndexed { y, s ->
            s.forEachIndexed { x, c ->
                when (c) {
                    '[' -> boxLefts.add(y to x)
                    '#' -> walls.add(y to x)
                    '@' -> robot = y to x
                }
            }
        }
        fun add(coord1: coordinate, coord2: coordinate): coordinate {
            return coord1.first + coord2.first to coord1.second + coord2.second
        }

        fun checkIntersect(coords: coordinate, boxLefts: Set<coordinate>): coordinate? {
            return when {
                coords in boxLefts -> coords
                add(coords, (0 to -1)) in boxLefts -> add(coords, (0 to -1))
                else -> null
            }
        }

        fun tryMove(start: coordinate, direction: coordinate, boxLefts: Set<coordinate>): Pair<Boolean, Set<coordinate>> {
            val target = add(start, direction)
            if (walls.contains(target)) {
                return false to emptySet()
            }
            val nextBox = checkIntersect(target, boxLefts)
            if (nextBox == null) {
                return true to emptySet()
            } else {
                when (direction) {
                    (0 to -1) -> {
                        val nextMove = tryMove(nextBox, direction, boxLefts)
                        return nextMove.first to (nextMove.second + nextBox)
                    }
                    (0 to 1) -> {
                        val nextMove = tryMove(add(nextBox, direction), direction, boxLefts)
                        return nextMove.first to (nextMove.second + nextBox)
                    }
                    else -> {
                        val nextMove1 = tryMove(nextBox, direction, boxLefts)
                        val nextMove2 = tryMove(add(nextBox, (0 to 1)), direction, boxLefts)
                        return (nextMove1.first && nextMove2.first) to (nextMove1.second + nextMove2.second + nextBox)
                    }
                }
            }
        }

        for (move in moves) {
            val direction = instructionToCoordinatePt2(move)
            val (moveBool, moveBoxes) =  tryMove(robot, direction, boxLefts)
            if (moveBool) {
                boxLefts = (boxLefts.filterNot { it in moveBoxes }.toMutableSet() + moveBoxes.map { add(it, direction) }) as MutableSet<coordinate>
                robot = add(robot, direction)
            }
        }

        val answer = boxLefts.sumOf { 100 * it.first + it.second }
        return answer
    }

    val testInput = readInput("Day15_test")
    check(part1(testInput) == 10092)
    check(part2(testInput) == 9021)
    
    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
