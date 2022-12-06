import java.util.zip.DataFormatException

enum class Hand {
    Rock, Paper, Scissors
}
enum class CodedHand(val hand: Hand, val handScore: Int) {
    A(Hand.Rock, 1),
    B(Hand.Paper, 2),
    C(Hand.Scissors, 3),
    X(Hand.Rock, 1),
    Y(Hand.Paper, 2),
    Z(Hand.Scissors, 3),
}

enum class CodedHand2(val score: Int) {
    A(1),
    B(2),
    C(3),
    X(0),
    Y(3),
    Z(6),
}
fun main() {

    fun getScore(input: List<String>): Int {
        var sum = 0

        input.forEach {
                s ->
            var opponent = CodedHand.valueOf(s[0].toString());
            var me = CodedHand.valueOf(s[2].toString());

            var pointsForMatch = 0
            if(opponent.handScore - me.handScore == -1 || (opponent.hand == Hand.Scissors && me.hand == Hand.Rock)){
                pointsForMatch = 6
            }
            else if(opponent.handScore == me.handScore){
                pointsForMatch = 3;
            }

            sum += pointsForMatch + me.handScore;
        }

        return sum;
    }

    fun part1(input: List<String>): Int {

        return getScore(input);
    }

    fun part2(input: List<String>): Int {
        var sum = 0

        input.forEach {
                s ->
            var opponent = CodedHand2.valueOf(s[0].toString());
            var me = CodedHand2.valueOf(s[2].toString());

            var pointsForItem: Int
            pointsForItem = when (me) {
                CodedHand2.X -> {
                    opponent.score -1;
                }

                CodedHand2.Z -> {
                    opponent.score +1;
                }

                CodedHand2.Y -> {
                    opponent.score;
                }

                else -> {
                    throw DataFormatException("Unexpected data")
                }
            }

            //fix rollover cases
            if(pointsForItem == 0){
                pointsForItem = 3;
            }
            if(pointsForItem == 4){
                pointsForItem = 1;
            }

            sum += pointsForItem + me.score;
        }

        return sum;
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    println(part1(testInput))
    println(part2(testInput))
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)
    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
