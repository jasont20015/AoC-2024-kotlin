enum class Direction(val dy: Int, val dx: Int, val chr: Char) {
    UP(-1, 0, '^'),
    DOWN(1,0,'v'),
    LEFT(0, -1, '<'),
    RIGHT(0,1,'>')
}

fun main() {


    fun genSteps(map: List<List<Char>>, start: Char, end: Char, visitedButtons: String): List<String>{
        if(start == end) return listOf("A")
        val starty = map.indexOfFirst { it.contains(start) }
        val startx = map[starty].indexOf(start)
        val results = mutableListOf<String>()
        for (d in Direction.entries) {
            val nextrow = starty + d.dy
            val nextcol = startx + d.dx
            if (
                nextrow >= 0 &&
                nextrow < map.size &&
                nextcol >= 0 &&
                nextcol < map[nextrow].size &&
                map[nextrow][nextcol] != 'X' &&
                !visitedButtons.contains(map[nextrow][nextcol])
            ) {
                results.addAll(
                    genSteps(map, map[nextrow][nextcol], end, visitedButtons + start).map { d.chr + it }
                )
            }
        }
        return results
    }


    fun part1(input: List<String>): Long {
        val mapDir = listOf(
            listOf('X', '^', 'A'),
            listOf('<', 'v', '>'),
        )
        var stepsDir = mutableMapOf<Pair<Char, Char>, List<String>>()
        for (start in "^A<v>") {
            for (end in "^A<v>") {
                stepsDir.put(start to end, genSteps(mapDir, start, end, ""))
            }
        }

        val mapNum =
            listOf(
                listOf('7', '8', '9'),
                listOf('4', '5', '6'),
                listOf('1', '2', '3'),
                listOf('X', '0', 'A'),
            )
        var stepsNum = mutableMapOf<Pair<Char, Char>, List<String>>()
        for (start in "7894561230A") {
            for (end in "7894561230A") {
                stepsNum.put(start to end, genSteps(mapNum, start, end, ""))
            }
        }

        data class State(val prevchr: Char, val curchr: Char, val moreLevels: Int)
        val cache = mutableMapOf<State, Long>()

        fun numpresses(map: Map<Pair<Char, Char>, List<String>>, state: State): Long {
            if (cache.containsKey(state)) return cache[state]!!

            if (state.moreLevels == 0) return 1

            var best = Long.MAX_VALUE
            val mapval = map[state.prevchr to state.curchr]
            if (mapval != null) {
                for (steps in mapval) {
                    var value = 0L
                    var prevchr = 'A'
                    for (c in steps) {
                        value += numpresses(stepsDir, State(prevchr, c, state.moreLevels - 1))
                        prevchr = c
                    }
                    if (value < best) best = value
                }
            }
            cache.put(state, best)
            return best
        }

        var sum = 0L
        for (code in input) {
            var value = 0L
            var prevchr = 'A'
            for (c in code) {
                value += numpresses(stepsNum, State(prevchr, c, 3))
                prevchr = c
            }
            val complexity = value * code.substring(0, code.length - 1).toLong()
            sum += complexity
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val mapDir = listOf(
            listOf('X', '^', 'A'),
            listOf('<', 'v', '>'),
        )
        var stepsDir = mutableMapOf<Pair<Char, Char>, List<String>>()
        for (start in "^A<v>") {
            for (end in "^A<v>") {
                stepsDir.put(start to end, genSteps(mapDir, start, end, ""))
            }
        }

        val mapNum =
            listOf(
                listOf('7', '8', '9'),
                listOf('4', '5', '6'),
                listOf('1', '2', '3'),
                listOf('X', '0', 'A'),
            )
        var stepsNum = mutableMapOf<Pair<Char, Char>, List<String>>()
        for (start in "7894561230A") {
            for (end in "7894561230A") {
                stepsNum.put(start to end, genSteps(mapNum, start, end, ""))
            }
        }

        data class State(val prevchr: Char, val curchr: Char, val moreLevels: Int)
        val cache = mutableMapOf<State, Long>()

        fun numpresses(map: Map<Pair<Char, Char>, List<String>>, state: State): Long {
            if (cache.containsKey(state)) return cache[state]!!

            if (state.moreLevels == 0) return 1

            var best = Long.MAX_VALUE
            val mapval = map[state.prevchr to state.curchr]
            if (mapval != null) {
                for (steps in mapval) {
                    var value = 0L
                    var prevchr = 'A'
                    for (c in steps) {
                        value += numpresses(stepsDir, State(prevchr, c, state.moreLevels - 1))
                        prevchr = c
                    }
                    if (value < best) best = value
                }
            }
            cache.put(state, best)
            return best
        }

        var sum = 0L
        for (code in input) {
            var value = 0L
            var prevchr = 'A'
            for (c in code) {
                value += numpresses(stepsNum, State(prevchr, c, 26))
                prevchr = c
            }
            val complexity = value * code.substring(0, code.length - 1).toLong()
            sum += complexity
        }
        return sum
    }

    val testInput = readInput("Day21_test")
    check(part1(testInput) == 126384L)
    // check(part2(testInput) == 1)
    
    val input = readInput("Day21")
    part1(input).println()
    part2(input).println()
}
