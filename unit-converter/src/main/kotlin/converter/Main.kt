package converter

import kotlin.RuntimeException

fun main() {
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val userInput = readln()
        if (userInput == "exit") break
        val (number, unitFrom, unitTo) = try {
            ConversionRequest(userInput)
        } catch (exception: RuntimeException) {
            println(exception.message)
            continue
        }
        val result = calculateResult(number, unitFrom, unitTo)
        val outputUnit = getUnitName(result, unitTo)
        val inputUnit = getUnitName(number, unitFrom)
        println("$number $inputUnit is $result $outputUnit")
    }
}

fun getUnitName(number: Double, unit: Unit) =
    if (number == 1.0) unit.singularName else unit.pluralName

fun calculateResult(number: Double, from: Unit, to: Unit) =
    if (from.group == UnitGroup.TEMPERATURE) from.formula(number, to)
    else number * from.formula(number, to) / to.formula(number, to)
