
fun main() {
    fun AND(input1: Boolean, input2: Boolean): Boolean{
        if(input1 && input2)
            return true
        return false
    }
    fun OR(input1: Boolean, input2: Boolean): Boolean{
        if(input1 || input2)
            return true
        return false
    }
    fun XOR(input1: Boolean, input2: Boolean): Boolean{
        if(input1 != input2)
            return true
        return false

    }
    fun part1(input: List<String>): Long {
        val inputSections = input.joinToString("\n").split("\n\n")
        val initialValues = inputSections[0].split("\n")
        val gates = inputSections[1].split("\n")
        val values = mutableMapOf<String, Boolean>()
        val answerBits = mutableListOf<Boolean>()
        for(i in initialValues){
            values.put(i.substringBefore(": "), i.substringAfter(": ").toInt().toBoolean())
        }
        var processedAllValues = false
        while(!processedAllValues){
            processedAllValues = true
            gates.forEach {
                val input1 = it.substringBefore(" ").trim()
                val input2 = it.substringBefore(" -> ").substringAfterLast(" ").trim()
                if(!values.containsKey(input1) || !values.containsKey(input2)){
                    processedAllValues = false
                    return@forEach
                }
                val operator = it.substringAfter(input1).substringBefore(input2).trim()
                val output = it.substringAfterLast(" ").trim()
                values.put(output, when(operator)  {
                    "AND" -> AND(values.get(input1)!!, values.get(input2)!!)
                    "OR" -> OR(values.get(input1)!!, values.get(input2)!!)
                    "XOR" -> XOR(values.get(input1)!!, values.get(input2)!!)
                    else -> false
                })
            }
        }
        val allKeys = values.keys
        var zKeys = allKeys.filter { it.startsWith("z") }
        zKeys = zKeys.sortedDescending()

        zKeys.forEach {
            values.get(it)?.let { it1 -> answerBits.add(it1) }
        }
        var answerBinary = ""
        for(bit in answerBits){
            val bitStr = when(bit){
                true -> "1"
                false -> "0"
            }
            answerBinary += bitStr
        }

        return answerBinary.toLong(2)
    }

    fun isBinaryEqual(input: Long, testOutput: Long): Boolean {
        return input == testOutput
    }

    fun part2(input: List<String>): String {
        return ""
    }

    val testInput = readInput("Day24_test")
    println(part2(testInput))
//    check(part1(testInput) == 2024L)
    check(part2(testInput) == "z00,z01,z02,z05")
    
    val input = readInput("Day24")
    part1(input).println()
    part2(input).println()
}

private fun Int.toBoolean(): Boolean {
    if(this >= 1){
        return true
    }
    return false
}
