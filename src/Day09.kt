fun main() {

    fun part1(input: List<String>): Long {
        val lengths = input[0].map { it.digitToInt() }

        val grid = mutableListOf<Int>()

        lengths.forEachIndexed { index, num ->
            repeat(num) {
                if (index % 2 == 0)
                    grid.add(index / 2)
                else
                    grid.add(-1)
            }
        }

        while (grid.contains(-1)) {
            if (grid.last() == -1) {
                grid.removeAt(grid.size - 1)
            } else {
                val index = grid.indexOf(-1)
                grid[index] = grid.removeAt(grid.size - 1)
            }
        }

        val answer = grid.mapIndexed() { i, num -> (i * num).toLong() }.sum()
        return answer
    }
    fun part2(input: List<String>): Long {
        val inputContent = input[0].map{it.digitToInt()}
        val answer: Long

        var id = 0
        val strip = mutableListOf<Int?>()
        val gaps = mutableListOf<Pair<Int, Int>>()
        val blocks = mutableListOf<Triple<Int, Int, Int>>()

        inputContent.forEachIndexed {index, _ ->
            if(index % 2 == 0){
                blocks.add(Triple(strip.size, id, inputContent[index]))
                strip.addAll(List(inputContent[index]) { id })
                id++
            }
            else {
                gaps.add(Pair(inputContent[index], strip.size))
                strip.addAll(List(inputContent[index]) { null })
            }
        }


        for (block in blocks.reversed()) {
            val (position, identifier, length) = block


            for ((idx, gap) in gaps.withIndex()) {
                val (gLength, gPosition) = gap
                if (gPosition > position) {
                    break
                }

                if (gLength >= length) {
                    for (l in 0 until length) {
                        strip[position + l] = null
                        strip[gPosition + l] = identifier
                    }

                    val diff = gLength - length
                    if (diff > 0) {
                        gaps[idx] = Pair(diff, gPosition + length)
                    } else {
                        gaps.removeAt(idx)
                    }
                    break
                }
            }
        }
        answer = strip.mapIndexed { idx, value -> ((value ?: 0) * idx).toLong() }.sum()
        return answer
    }

    val testInput = readInput("Day09_test")
    println(part1(testInput))
    check(part1(testInput) == 1928L)
    println(part2(testInput))
    check(part2(testInput) == 2858L)
    
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
