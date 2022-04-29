package flashcards

fun main() {
    println("Input the number of cards:")
    val n = readLine()!!.toInt()
    val terms = MutableList(n) { "" }
    val defs = MutableList(n) { "" }
    for (i in 0..n - 1) {
        println("Card #${i+1}")
        terms[i] = readLine()!!
        println("The definition for card #${i+1}")
        defs[i] = readLine()!!
    }
    for (i in 0..n - 1) {
        println("Print the definition of \"" + terms[i] + "\":")
        val answer = readLine()!!
        println(if (answer == defs[i]) "Correct!" else "Wrong. The right answer is \"" + defs[i] + "\".")
    }
}
