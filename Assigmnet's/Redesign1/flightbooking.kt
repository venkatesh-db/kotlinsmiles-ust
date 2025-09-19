



data class Flight(
    val flightId: String,
    val airline: String,
    val source: String,
    val destination: String,
    val departureTime: Long,
    val arrivalTime: Long,
    val price: Int
)

// Store flights
val flights = mutableListOf<Flight>()

// Add flight
fun addFlight(flight: Flight) {
    flights.add(flight)
}

// Get flight by ID
fun getFlightById(id: String): Flight? {
    return flights.find { it.flightId == id }
}

// Search flights with filters
fun searchFlights(
    source: String,
    destination: String,
    maxPrice: Int? = null
): List<Flight> {
    return flights.asSequence()
        .filter { it.source == source && it.destination == destination }
        .filter { maxPrice == null || it.price <= maxPrice }
        .sortedBy { it.price }
        .toList()
}

// Example usage
fun main() {
    addFlight(Flight("F101", "IndiGo", "BLR", "DEL", 1694853000, 1694859000, 4500))
    addFlight(Flight("F102", "Air India", "BLR", "DEL", 1694856000, 1694862000, 5200))
    addFlight(Flight("F103", "Vistara", "BLR", "DEL", 1694859000, 1694865000, 4800))

    println("🔍 Search BLR -> DEL under ₹5000:")
    val results = searchFlights("BLR", "DEL", 5000)
    results.forEach { println(it) }

    println("\n🔎 Get flight by ID (F102):")
    println(getFlightById("F102"))
}
