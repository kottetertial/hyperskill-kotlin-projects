package converter

import java.util.Scanner

class ConversionRequest(userInput: String) {
    private val number: Double
    private val unitFrom: Unit
    private val unitTo: Unit

    init {
        val scanner = Scanner(userInput)

        this.number = parseNumber(scanner)
        this.unitFrom = parseUnit(scanner)

        parseNext(scanner)

        this.unitTo = parseUnit(scanner)

        validateInput()
    }

    private fun parseUnit(scanner: Scanner): Unit {
        val next = parseNext(scanner)
        return if (next.startsWith("degree", true)) getUnit(parseNext(scanner)) else getUnit(next)
    }

    private fun parseNumber(scanner: Scanner) =
        try {
            scanner.nextDouble()
        } catch (exception: RuntimeException) {
            throw RuntimeException("Parse error")
        }

    private fun parseNext(scanner: Scanner) =
        try {
            scanner.next()
        } catch (exception: RuntimeException) {
            throw RuntimeException("Parse error")
        }

    private fun getUnit(unitName: String): Unit {
        val lowercaseUnit = unitName.lowercase()
        for (entry in Unit.entries) {
            if (lowercaseUnit in entry.names()) return entry
        }
        return Unit.NULL
    }

    private fun validateInput() {
        validateUnitsAreMatching()
        validateNumberIsValidForGroup(unitFrom)
        validateNumberIsValidForGroup(unitTo)
    }

    private fun validateNumberIsValidForGroup(unit: Unit) {
        val validateUnit = unit.group.validate(number)
        if (validateUnit.isNotEmpty())
            throw RuntimeException(validateUnit.joinToString(", "))
    }

    private fun validateUnitsAreMatching() {
        if (unitFrom == Unit.NULL || unitTo == Unit.NULL || unitFrom.group != unitTo.group)
            throw RuntimeException("Conversion from ${unitFrom.pluralName} to ${unitTo.pluralName} is impossible")
    }

    operator fun component1(): Double {
        return number
    }

    operator fun component2(): Unit {
        return unitFrom
    }

    operator fun component3(): Unit {
        return unitTo
    }
}
