package flashcards

fun findKey(map: MutableMap<String, String>, value: String) : String {
    for ((k, v) in map) {
        if (v == value) {
            return k
        }
    }
    return ""
}

fun main() {
    println("Input the number of cards:")
    val n = readLine()!!.toInt()
    var dict = mutableMapOf<String, String>()
    for (i in 0..n - 1) {
        println("Card #${i+1}")
        var term = readLine()!!
        while (dict.contains(term)) {
            println("The term \"$term\" already exists. Try again:")
            term = readLine()!!
        }
        println("The definition for card #${i+1}")
        var def = readLine()!!
        while (findKey(dict, def) != "") {
            println("The definition \"$def\" already exists. Try again:")
            def = readLine()!!
        }
        dict[term] = def
    }
    for ((k, v) in dict) {
        println("Print the definition of \"$k\":")
        val answer = readLine()!!
        val key = findKey(dict, answer)
        if (answer == v) {
            println("Correct!")
        } else if (key != "") {
            println("Wrong. The right answer is \"$v\", but your definition is correct for \"$key\".")
        } else {
            println("Wrong. The right answer is \"$v\".")
        }
    }
}
