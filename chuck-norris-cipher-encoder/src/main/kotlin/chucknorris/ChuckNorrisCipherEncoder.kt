package chucknorris

fun main() {
    do {
        println("Please input operation (encode/decode/exit):")
        val userChoice = readln()
        when (userChoice) {
            "encode" -> encode()
            "decode" -> decode()
            "exit" -> println("Bye!")
            else -> println("There is no '$userChoice' operation")
        }
    } while (userChoice != "exit")
}

fun encode() {
    println("Input string:")
    val inputString = readln()
    println("Encoded string:")
    println(convertToChuckNorris(inputString))
}

fun decode() {
    println("Input encoded string:")
    val inputString = readln()
    val result = try {
        convertFromChuckNorris(inputString)
    } catch (exception: IllegalArgumentException) {
        println("Encoded string is not valid.")
        return
    }
    println("Decoded string:")
    println(result)
}

fun convertToBinary(char: Char): List<Int> {
    var code = char.code
    val result = MutableList(7) { 0 }
    for (i in 6 downTo 0) {
        result[i] = code % 2
        code /= 2
    }
    return result
}

fun convertToBinary(string: String): List<Int> {
    return string.map { convertToBinary(it) }.flatten()
}

fun convertToChuckNorris(binary: List<Int>): List<String> {
    val result = mutableListOf<String>()
    var previousElement: Int? = null
    for (element in binary) {
        if (previousElement == element) {
            result[result.lastIndex] += "0"
            continue
        }
        result.add(if (element == 1) "0" else "00")
        result.add("0")
        previousElement = element
    }
    return result
}

fun convertToChuckNorris(string: String): String {
    val binary = convertToBinary(string)
    return convertToChuckNorris(binary).joinToString(" ")
}

fun convertFromChuckNorris(string: String): String {
    validateContainsOnlyAcceptedCharacters(string)
    val binary = convertFromChuckNorris(string.split(" "))
    return convertFromBinary(binary).joinToString("")
}

fun convertFromChuckNorris(string: List<String>): List<List<Int>> {
    validateNumberOfBlocksIsEven(string)
    val result = mutableListOf<Int>()
    var currentElement = 0
    for (i in string.indices) {
        val block = string[i]
        if (i % 2 == 0) {
            validateBlockIsAccepted(block)
            currentElement = if (block == "0") 1 else 0
            continue
        }
        val elementCount = block.length
        repeat(elementCount) {
            result.add(currentElement)
        }
    }
    return result.chunked(7)
}

fun convertFromBinary(binary: List<List<Int>>): List<Char> {
    return binary.map { convertFromBinary(it) }.map { it.toChar() }
}

fun convertFromBinary(binary: List<Int>): Int {
    validateBinaryLengthIsSeven(binary)
    var result = 0
    var currentTwoPow = 1
    for (i in 6 downTo 0) {
        result += binary[i] * currentTwoPow
        currentTwoPow *= 2
    }
    return result
}

fun validateContainsOnlyAcceptedCharacters(string: String) {
    val regex = "[0\\s]+".toRegex()
    if (!regex.matches(string)) throw IllegalArgumentException()
}

fun validateNumberOfBlocksIsEven(string: List<String>) {
    if (string.size % 2 != 0) throw IllegalArgumentException()
}

fun validateBlockIsAccepted(block: String) {
    if (block != "0" && block != "00") throw IllegalArgumentException()
}

fun validateBinaryLengthIsSeven(binary: List<Int>) {
    if (binary.size != 7) throw IllegalArgumentException()
}
