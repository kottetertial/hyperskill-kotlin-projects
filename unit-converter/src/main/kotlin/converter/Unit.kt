package converter

import converter.UnitGroup.LENGTH
import converter.UnitGroup.TEMPERATURE
import converter.UnitGroup.WEIGHT

enum class Unit(
    val group: UnitGroup,
    val pluralName: String,
    val singularName: String,
    private vararg val otherNames: String,
    val formula: (Double, Unit) -> Double
) {
    METER(
        LENGTH,
        "meters",
        "meter",
        "m",
        formula = { _, _ -> 1.0 }
    ),
    KILOMETER(
        LENGTH,
        "kilometers",
        "kilometer",
        "km",
        formula = { _, _ -> 1000.0 }
    ),
    CENTIMETER(
        LENGTH,
        "centimeters",
        "centimeter",
        "cm",
        formula = { _, _ -> 0.01 }
    ),
    MILLIMETER(
        LENGTH,
        "millimeters",
        "millimeter",
        "mm",
        formula = { _, _ -> 0.001 }
    ),
    MILE(
        LENGTH,
        "miles",
        "mile",
        "mi",
        formula = { _, _ -> 1609.35 }
    ),
    YARD(
        LENGTH,
        "yards",
        "yard",
        "yd",
        formula = { _, _ -> 0.9144 }
    ),
    FOOT(
        LENGTH,
        "feet",
        "foot",
        "ft",
        formula = { _, _ -> 0.3048 }
    ),
    INCH(
        LENGTH,
        "inches",
        "inch",
        "in",
        formula = { _, _ -> 0.0254 }
    ),
    GRAM(
        WEIGHT,
        "grams",
        "gram",
        "g",
        formula = { _, _ -> 1.0 }
    ),
    KILOGRAM(
        WEIGHT,
        "kilograms",
        "kilogram",
        "kg",
        formula = { _, _ -> 1000.0 }
    ),
    MILLIGRAM(
        WEIGHT,
        "milligrams",
        "milligram",
        "mg",
        formula = { _, _ -> 0.001 }
    ),
    POUND(
        WEIGHT,
        "pounds",
        "pound",
        "lb",
        formula = { _, _ -> 453.592 }
    ),
    OUNCE(
        WEIGHT,
        "ounces",
        "ounce",
        "oz",
        formula = { _, _ -> 28.3495 }
    ),
    CELSIUS(
        TEMPERATURE,
        "degrees Celsius",
        "degree Celsius",
        "celsius", "dc", "c",
        formula = { number, unitTo ->
            when (unitTo) {
                FAHRENHEIT -> number * 9 / 5 + 32
                KELVIN -> number + 273.15
                CELSIUS -> number
                else -> throw RuntimeException("Unknown temperature unit $unitTo")
            }
        }
    ),
    FAHRENHEIT(
        TEMPERATURE,
        "degrees Fahrenheit",
        "degree Fahrenheit",
        "fahrenheit", "df", "f",
        formula = { number, unitTo ->
            when (unitTo) {
                CELSIUS -> (number - 32) * 5 / 9
                KELVIN -> (number + 459.67) * 5 / 9
                FAHRENHEIT -> number
                else -> throw RuntimeException("Unknown temperature unit $unitTo")
            }
        }
    ),
    KELVIN(
        TEMPERATURE,
        "kelvins",
        "kelvin",
        "k",
        formula = { number, unitTo ->
            when (unitTo) {
                CELSIUS -> number - 273.15
                FAHRENHEIT -> number * 9 / 5 - 459.67
                KELVIN -> number
                else -> throw RuntimeException("Unknown temperature unit $unitTo")
            }
        }
    ),
    NULL(
        UnitGroup.NULL,
        "???",
        "???",
        "???",
        formula = { _, _ -> 0.0 }
    );

    fun names() = setOf(this.pluralName, this.singularName, *this.otherNames)
}
