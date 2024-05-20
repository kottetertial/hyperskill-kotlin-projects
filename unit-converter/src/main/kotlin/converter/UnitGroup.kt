package converter

enum class UnitGroup(val validate: (Double) -> Set<String>) {
    LENGTH({ number: Double -> if (number < 0) setOf("Length shouldn't be negative") else setOf() }),
    WEIGHT({ number: Double -> if (number < 0) setOf("Weight shouldn't be negative") else setOf() }),
    TEMPERATURE({ setOf() }),
    NULL({ setOf() })
}
