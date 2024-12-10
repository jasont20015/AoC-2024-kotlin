fun main() {
    fun gcd(a: Int, b: Int): Int{
        if(b==0){
            return a
        }
        return gcd(b,a%b)
    }

    fun getAntiNodeLocations(a: coordinate, b: coordinate, pt1: Boolean = true): List<coordinate>{
        val coordinates = mutableListOf<coordinate>()
        val diffX = a.first - b.first
        val diffY = a.second - b.second
        if(pt1) {
            coordinates.add(coordinate(b.first - diffX, b.second - diffY))
            coordinates.add(coordinate(a.first + diffX, a.second + diffY))
        }
        else{
            val gcd = gcd(diffX, diffY)
            for(i in 0..50){
                coordinates.add(coordinate(b.first - (i*diffX/gcd), b.second - (i*diffY/gcd)))
                coordinates.add(coordinate(a.first + (i*diffX/gcd), a.second + (i*diffY/gcd)))
            }
        }
        return coordinates
    }

    fun part1(input: List<String>): Int {
        val antennaLocations: MutableMap<Char, MutableList<coordinate>> = mutableMapOf()
        input.forEachIndexed { index, s ->
            s.forEachIndexed { innerIndex, c ->
                if(c != '.'){
                    val locations = antennaLocations.getOrPut(c, { mutableListOf() })
                    locations.add(Pair(index, innerIndex))

                }
            }
        }
        val antinodes: MutableSet<coordinate> = mutableSetOf()
        for(c in antennaLocations.keys){
            val coordinates = antennaLocations.get(c)
            if (coordinates != null) {
                coordinates.forEachIndexed { index, coords ->
                    coordinates.forEachIndexed innerLoop@{ inner, innerCoords ->
                        if(inner <= index){
                            return@innerLoop
                        }
                        antinodes.addAll(getAntiNodeLocations(coords, innerCoords))
                    }
                }
            }
        }
        val filtered = antinodes.filter {
            it.first >= 0 && it.first < input.size && it.second >= 0 && it.second < input[0].length
        }

        return filtered.size
    }

    fun part2(input: List<String>): Int {
        val antennaLocations: MutableMap<Char, MutableList<coordinate>> = mutableMapOf()
        input.forEachIndexed { index, s ->
            s.forEachIndexed { innerIndex, c ->
                if(c != '.'){
                    val locations = antennaLocations.getOrPut(c, { mutableListOf() })
                    locations.add(Pair(index, innerIndex))

                }
            }
        }
        val antinodes: MutableSet<coordinate> = mutableSetOf()
        for(c in antennaLocations.keys){
            val coordinates = antennaLocations.get(c)
            if (coordinates != null) {
                coordinates.forEachIndexed { index, coords ->
                    coordinates.forEachIndexed innerLoop@{ inner, innerCoords ->
                        if(inner <= index){
                            return@innerLoop
                        }
                        antinodes.addAll(getAntiNodeLocations(coords, innerCoords, false))
                    }
                }
            }
        }
        val filtered = antinodes.filter {
            it.first >= 0 && it.first < input.size && it.second >= 0 && it.second < input[0].length
        }

        return filtered.size
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)
    
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
