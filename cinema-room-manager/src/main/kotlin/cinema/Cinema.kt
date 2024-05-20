package cinema

fun main() {
    val (rows, seatsRow) = setUpCinema()
    val cinema = buildCinema(rows, seatsRow)

    val totalTickets = rows * seatsRow
    var purchasedTickets = 0

    val ticketsPrices = getTicketsPrices(totalTickets)
    val totalIncome = calculateProfit(cinema, ticketsPrices)
    var currentIncome = 0

    do {
        printMenu()
        val command = readln()
        when (command) {
            "1" -> printCinema(cinema)
            "2" -> {
                currentIncome += buyTicket(ticketsPrices, cinema)
                purchasedTickets++
            }

            "3" -> printStatistics(purchasedTickets, totalTickets, currentIncome, totalIncome)
        }
    } while (command != "0")
}

fun setUpCinema(): Pair<Int, Int> {
    println("Enter the number of rows:")
    val rows = readln().toInt()

    println("Enter the number of seats in each row:")
    val seatsRow = readln().toInt()

    return Pair(rows, seatsRow)
}

fun buildCinema(rows: Int, seatsRow: Int): MutableList<MutableList<Char>> {
    return MutableList(rows) { MutableList(seatsRow) { 'S' } }
}

fun printMenu() {
    println(
        """
        1. Show the seats
        2. Buy a ticket
        3. Statistics
        0. Exit
    """.trimIndent()
    )
}

fun printCinema(cinema: MutableList<MutableList<Char>>) {
    val (rows, seatsRow) = getCinemaSettings(cinema)

    println("Cinema:\n ")
    (1..seatsRow).forEach { print(" $it") }
    println()
    (1..rows).forEach { println("$it ${cinema[it - 1].joinToString(" ")}") }
}

fun printStatistics(purchasedTickets: Int, totalTickets: Int, currentIncome: Int, totalIncome: Int) {
    val percentage = 100.0 * purchasedTickets / totalTickets
    val formatPercentage = "%.2f".format(percentage)

    println(
        """
        Number of purchased tickets: $purchasedTickets
        Percentage: $formatPercentage%
        Current income: $$currentIncome
        Total income: $$totalIncome
    """.trimIndent()
    )
}

fun buyTicket(
    ticketPrices: Pair<Int, Int>,
    cinema: MutableList<MutableList<Char>>
): Int {
    val (row, seatInRow) = chooseSeat(cinema)
    val (frontRowsPrice, backRowsPrice) = ticketPrices
    val ticketPrice = if (row <= cinema.size / 2) frontRowsPrice else backRowsPrice
    println("Ticket price: $$ticketPrice")

    cinema[row - 1][seatInRow - 1] = 'B'

    return ticketPrice
}

fun chooseSeat(cinema: MutableList<MutableList<Char>>): Pair<Int, Int> {
    return try {
        val seat = getUserChoice()
        validateSeat(seat, cinema)
        seat
    } catch (exception: RuntimeException) {
        println(exception.message)
        chooseSeat(cinema)
    }
}

fun getUserChoice(): Pair<Int, Int> {
    println("Enter a row number:")
    val row = readln().toInt()

    println("Enter a seat number in that row:")
    val seatInRow = readln().toInt()

    return Pair(row, seatInRow)
}

fun validateSeat(seat: Pair<Int, Int>, cinema: MutableList<MutableList<Char>>) {
    val (row, seatInRow) = seat
    val (rows, seatsRow) = getCinemaSettings(cinema)
    if (row > rows || seatInRow > seatsRow) throw RuntimeException("Wrong input!")
    if (cinema[row - 1][seatInRow - 1] == 'B') throw RuntimeException("That ticket has already been purchased!")
}

fun calculateProfit(cinema: MutableList<MutableList<Char>>, ticketPrices: Pair<Int, Int>): Int {
    val (rows, seatsRow) = getCinemaSettings(cinema)
    val frontHalf = rows / 2
    val backHalf = rows - frontHalf
    return ticketPrices.first * frontHalf * seatsRow + ticketPrices.second * backHalf * seatsRow
}

fun getTicketsPrices(seats: Int): Pair<Int, Int> {
    val backRowsPrice = if (seats <= 60) 10 else 8
    return Pair(10, backRowsPrice)
}

fun getCinemaSettings(cinema: MutableList<MutableList<Char>>) = Pair(cinema.size, cinema.first().size)
