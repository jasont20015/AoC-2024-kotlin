import java.util.PriorityQueue


fun main() {
    data class Coordinate(var x: Int, var y: Int){
        operator fun plus(other: Coordinate) = Coordinate(x+other.x, y+other.y)
        operator fun times(other: Coordinate) = Coordinate(x*other.x - y*other.y, x*other.y + y*other.x)
    }

    fun Map<Coordinate, Char>.getKeyByValue(c: Char): Coordinate {
        return this.asIterable().first {it.value == c}.key
    }
    fun Map<Coordinate, Char>.score(): Int {
        data class P(val z: Coordinate, val d: Coordinate)
        data class Node(val p: P, val cost: Int)

        val map = mutableMapOf<P, Int>()
        val queue = PriorityQueue<Node> {e1, e2 -> e1.cost.compareTo(e2.cost)}
        queue.add(Node(P(this.getKeyByValue('S'), Coordinate(1,0)),0))
        val end = this.getKeyByValue('E')

        while(true) {
            val (p, cost) = queue.poll()
            map[p] = cost
            val(z,d) = p
            if (z == end) return cost
            if(this[z+d] != '#' && P(z+d, d) !in map){
                queue.add(Node(P(z+d, d), cost+1))
            }
            listOf(d*Coordinate(0,-1), d*Coordinate(0,1))
                .filter { P(z, it) !in map }
                .forEach { queue.add(Node(P(z, it), cost + 1000)) }
        }
    }
    fun Map<Coordinate, Char>.getSpots(): Int {
        data class P(val z: Coordinate, val d: Coordinate)
        data class Node(val p: P, val cost: Int, val path: List<Coordinate>)
        val map = mutableMapOf<P, Int>()
        val queue = PriorityQueue<Node> {e1, e2 -> e1.cost.compareTo(e2.cost)}
        val start = this.getKeyByValue('S')
        queue.add(Node(P(start, Coordinate(1,0)),0, listOf(start)))
        val end = this.getKeyByValue('E')
        while(true) {
            val(p,cost,path) = queue.poll()
            map[p] = cost
            val(z,d) = p
            if (z == end){
                val placesToSit = path.toMutableSet()
                generateSequence { queue.poll() }
                    .takeWhile { queue.isNotEmpty() && it.p.z == end && it.cost == cost }
                    .forEach { placesToSit.addAll(it.path) }
                return placesToSit.size
            }
            if(this[z+d] != '#' && P(z+d, d) !in map){
                queue.add(Node(P(z+d, d), cost+1, path + (z+d)))
            }
            listOf(d*Coordinate(0, -1), d * Coordinate(0, 1))
                .filter{P(z,it) !in map}
                .forEach { queue.add(Node(P(z, it), cost + 1000, path)) }
        }

    }
    



    fun part1(input: List<String>): Int {
        return input.map{ it.trim() }
            .flatMapIndexed{ y, line -> line.mapIndexed {x, c -> Coordinate(x,y) to c}}
            .toMap()
            .score()

    }

    fun part2(input: List<String>): Int {
        return input.map{ it.trim() }
            .flatMapIndexed{ y, line -> line.mapIndexed {x, c -> Coordinate(x,y) to c} }
            .toMap()
            .getSpots()
    }

    val testInput = readInput("Day16_test")
    check(part1(testInput) == 11048)
    check(part2(testInput) == 45)
    
    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
