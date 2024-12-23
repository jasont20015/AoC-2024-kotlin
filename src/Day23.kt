fun main() {
    fun bronKerbosch(
        graph: HashMap<String, HashSet<String>>,
        r: HashSet<String>,
        p: HashSet<String>,
        x: HashSet<String>,
        cliques: MutableList<HashSet<String>>
    ) {
        if (p.isEmpty()) {
            if (x.isEmpty()) {
                cliques.add(HashSet(r))
            }
            return
        }
        val iterator = p.iterator()
        while (iterator.hasNext()) {
            val i = iterator.next()
            val neighbours = graph[i] ?: continue
            val pSet = p.intersect(neighbours).toHashSet()
            val xSet = x.intersect(neighbours).toHashSet()
            r.add(i)
            bronKerbosch(graph, r, pSet, xSet, cliques)
            r.remove(i)
            iterator.remove()
            x.add(i)
        }
    }



    fun part1(input: List<String>): Int {
        val graph = HashMap<String, HashSet<String>>()
        input.forEach { line ->
            val (a, b) = line.split("-")
            graph.computeIfAbsent(a) { HashSet() }.add(b)
            graph.computeIfAbsent(b) { HashSet() }.add(a)
        }
        val threeCliques = HashSet<List<String>>()
        for (i in graph.keys) {
            if (!i.startsWith('t')) {
                continue
            }
            for (j in graph[i] ?: emptySet()) {
                for (k in graph[i]!!.intersect(graph[j] ?: emptySet())) {
                    val c = listOf(i, j, k).sorted()
                    threeCliques.add(c)
                }
            }
        }
        return threeCliques.size
    }

    fun part2(input: List<String>): String {
        val graph = HashMap<String, HashSet<String>>()
        input.forEach { line ->
            val (a, b) = line.split("-")
            graph.computeIfAbsent(a) { HashSet() }.add(b)
            graph.computeIfAbsent(b) { HashSet() }.add(a)
        }
        val cliques = mutableListOf<HashSet<String>>()
        bronKerbosch(graph, HashSet(), graph.keys.toHashSet(), HashSet(), cliques)
        val p2 = cliques.maxByOrNull { it.size }?.sorted()?.joinToString(",") ?: ""
        return p2
    }

    val testInput = readInput("Day23_test")
    println(part1(testInput))
    check(part1(testInput) == 7)
    check(part2(testInput) == "co,de,ka,ta")
    
    val input = readInput("Day23")
    part1(input).println()
    part2(input).println()
}
