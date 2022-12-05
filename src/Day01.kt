fun main() {

    fun getCalories(input: List<String>, numberToTake: Int): Int {
        var elfTotals = mutableMapOf<Int,Int>()

        var elfNumber = 0

        input.forEach {
                s ->
            if(s.isNullOrEmpty()){
                elfNumber++
            }
            else{
                elfTotals[elfNumber] = elfTotals[elfNumber]?.plus(s.toInt()) ?: s.toInt()
            }
        }

        return elfTotals.values.sortedDescending().take(numberToTake).sum();
    }

    fun part1(input: List<String>): Int {

        return getCalories(input,1);
    }

    fun part2(input: List<String>): Int {
        return getCalories(input,3);
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
