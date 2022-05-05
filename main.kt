import java.io.File
import kotlin.random.Random

class Card {
    var def: String = ""
    var errors: Int = 0
}

fun findKey(map: MutableMap<String, Card>, value: String) : String {
    for ((k, v) in map) {
        if (v.def == value) {
            return k
        }
    }
    return ""
}

var log = ""
fun println(str: String) {
    log += str + "\n"
    kotlin.io.println(str)
}
fun readLine(): String? {
    val str = kotlin.io.readLine()
    log += str!! + "\n"
    return str
}

fun addCard(dict: MutableMap<String, Card>): MutableMap<String, Card>{
    println("The card:")
    val term = readLine()!!
    if (dict.contains(term)) {
        println("The card \"$term\" already exists.")
        return dict
    }
    println("The definition of the card:")
    val def = readLine()!!
    if (findKey(dict, def) != "") {
        println("The definition \"$def\" already exists.")
        return dict
    }
    /////////////////////////////////////////////
    dict[term] = Card()
    dict[term]!!.def = def
    println("The pair (\"$term\":\"$def\") has been added.")
    return dict
}

fun removeCard(dict: MutableMap<String, Card>): MutableMap<String, Card> {
    println("Which card?")
    val def = readLine()!!
    if (dict.contains(def)) {
        dict -= def
        println("The card has been removed.")
    } else {
        println("Can't remove \"$def\": there is no such card.")
    }
    return dict
}

fun importDict(dict: MutableMap<String, Card>): MutableMap<String, Card> {
    println("File name:")
    val fileName = readLine()!!
    val file = File(fileName)
    if (!file.exists()) {
        println("File not found.")
        return dict
    }
    val lines = File(fileName).readLines()
    val n = lines[0].toInt()
    for (i in 0..n - 1){
        dict[lines[3 * i + 1]]!!.def = lines[3 * i + 2]
        dict[lines[3 * i + 1]]!!.errors = lines[3 * i + 3].toInt()
    }
    println("$n cards have been loaded.")
    return dict
}

fun exportDict(dict: MutableMap<String, Card>): Unit {
    println("File name:")
    val fileName = readLine()!!
    val file = File(fileName)
    file.writeText("${dict.size}\n")
    for ((k, v) in dict) {
        file.appendText("$k\n")
        file.appendText("${v.def}\n")
        file.appendText("${v.errors}\n")
    }
    println("${dict.size} cards have been saved.")
}

fun askTerm(dict: MutableMap<String, Card>): MutableMap<String, Card> {
    println("How many times to ask?")
    val n = readLine()!!.toInt()
    val list = dict.toList()
    for (i in 0..n - 1) {
        val num = Random.nextInt(0, n)
        println("Print the definition of \"${list[num].first}\":")
        val answer = readLine()!!
        val key = findKey(dict, answer)
        if (answer == list[num].second.def) {
            println("Correct!")
        } else {
            ++dict[list[num].first]!!.errors
            if (key != "") {
                println("Wrong. The right answer is \"${list[num].second.def}\", but your definition is correct for \"$key\".")
            } else {
                println("Wrong. The right answer is \"${list[num].second.def}\".")
            }
        }
    }
    return dict
}

fun writeLog(): Unit {
    println("File name:")
    val fn = readLine()!!
    val file = File(fn)
    file.writeText(log)
    println("The log has been saved.")
}

fun findHardestCard(dict: MutableMap<String, Card>): Unit {
    var maxErrors = 0
    for ((_, value) in dict) {
        if (value.errors > maxErrors) {
            maxErrors = value.errors
        }
    }
    if (maxErrors == 0) {
        println("There are no cards with errors.")
        return
    }
    var resList = mutableListOf<String>()
    for ((key, value) in dict) {
        if (value.errors == maxErrors) {
            resList.add(key)
        }
    }
    if (resList.size == 1) {
        println("The hardest card is \"${resList[0]}\".")
    } else {
        println("The hardest cards are " + resList.joinToString(separator = ", "))
    }
    println("You have $maxErrors errors answering it")
}

fun resetStats(dict: MutableMap<String, Card>): MutableMap<String, Card> {
    for ((_, value ) in dict) {
        value.errors = 0
    }
    println("Card statistics have been reset.")
    return dict
}

fun main() {
    var dict = mutableMapOf<String, Card>()
    while (true) {
        println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
        val input = readLine()!!
        when (input) {
            "add" -> dict = addCard(dict)
            "remove" -> dict = removeCard(dict)
            "import" -> dict = importDict(dict)
            "export" -> exportDict(dict)
            "ask" -> dict = askTerm(dict)
            "exit" -> {
                println("Bye bye!")
                return
            }
            "log" -> writeLog()
            "hardest card" -> findHardestCard(dict)
            "reset stats" -> dict = resetStats(dict)
            else -> println("Action not found.")
        }
    }
}
