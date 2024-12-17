import kotlin.math.pow

fun main() {
    fun adv(operand: Long, A: Long): Long {
        val denominator = 2.0.pow(operand.toDouble())
        return (A/denominator).toLong()
    }
    fun bxl(operand: Long, B: Long): Long {
        return operand xor B
    }
    fun bst(operand: Long): Long {
        return operand % 8
    }
    fun jnz(operand: Long, A: Long, instructionPointer: Int): Int {
        if(A != 0L) {
            return operand.toInt()
        }
        return instructionPointer

    }
    fun bxc(operand: Long, B: Long, C: Long): Long {
        return B xor C
    }
    fun out(operand: Long): Long {
        return operand % 8
    }
    fun bdv(operand: Long, A: Long): Long {
        val denominator = Math.pow(2.0, operand.toDouble())
        return (A/denominator).toLong()
    }
    fun cdv(operand: Long, A: Long): Long {
        val denominator = Math.pow(2.0, operand.toDouble())
        return (A/denominator).toLong()
    }
    fun getComboOperand(operand: Long, A: Long, B: Long, C:Long): Long {
        when(operand) {
            0L,1L,2L,3L -> return operand
            4L -> return A
            5L -> return B
            6L -> return C
            else -> return -1
        }
    }

    fun run(
        instructions: List<Long>,
        aStart: Long
    ): MutableList<Long> {
        var instructionPointer = 0
        var A = aStart
        var B = 0L
        var C = 0L
        val output = mutableListOf<Long>()
        while (instructionPointer < instructions.size) {
            val literalOperand = instructions[instructionPointer + 1]
            val comboOperand = getComboOperand(literalOperand, A, B, C)
            val instruction = instructions[instructionPointer]
            instructionPointer += 2
            when (instruction) {
                0L -> A = adv(comboOperand, A)
                1L -> B = bxl(literalOperand, B)
                2L -> B = bst(comboOperand)
                3L -> instructionPointer = jnz(literalOperand, A, instructionPointer)
                4L -> B = bxc(literalOperand, B, C)
                5L -> output.add(out(comboOperand))
                6L -> B = bdv(comboOperand, A)
                7L -> C = cdv(comboOperand, A)
            }
        }
        return output
    }

    fun part1(input: List<String>): String {
        val A = input[0].substringAfter(":").trim().toLong()
        val instructions = input[4].substringAfter(":").trim().split(",").map{it.toLong()}
        val output = run(instructions, A)
        return output.joinToString(",")
    }



    val testInput = readInput("Day17_test")
    check(part1(testInput) == "4,6,3,5,6,3,5,2,1,0")

    
    val input = readInput("Day17")
    part1(input).println()
}
