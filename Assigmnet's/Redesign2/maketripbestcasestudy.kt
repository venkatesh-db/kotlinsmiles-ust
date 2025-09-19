

// 1️⃣ Sealed class for Airlines (fixed set of types)
sealed class Airline(val name: String) {

    object IndiGo : Airline("IndiGo")
    object AirIndia : Airline("Air India")
    object Vistara : Airline("Vistara")
    object SpiceJet : Airline("SpiceJet")
    // ✅ No other airline allowed outside this sealed hierarchy
}

// 2️⃣ Open class for general Flight
open class Flight(
    val flightId: String,
    val airline: Airline,
    val source: String,
    val destination: String,
    val departureTime: Long,
    val arrivalTime: Long,
    val price: Int
) {
    override fun toString(): String {
        return "[$flightId - ${airline.name}] $source → $destination | ₹$price"
    }
}

// 3️⃣ Final class for specific company flights
final class IndiGoFlight(
    flightId: String,
    source: String,
    destination: String,
    departureTime: Long,
    arrivalTime: Long,
    price: Int
) : Flight(flightId, Airline.IndiGo, source, destination, departureTime, arrivalTime, price)

final class AirIndiaFlight(
    flightId: String,
    source: String,
    destination: String,
    departureTime: Long,
    arrivalTime: Long,
    price: Int
) : Flight(flightId, Airline.AirIndia, source, destination, departureTime, arrivalTime, price)

// 4️⃣ Object → Singleton for Flight Database
object FlightDatabase {
    
    private val flights = mutableListOf<Flight>()

    fun addFlight(flight: Flight) {
        flights.add(flight)
    }

    fun getFlightById(id: String): Flight? =
        flights.find { it.flightId == id }

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
}

// 5️⃣ Example usage
fun main() {
    // Add flights
    FlightDatabase.addFlight(IndiGoFlight("F101", "BLR", "DEL", 1694853000, 1694859000, 4500))
    FlightDatabase.addFlight(AirIndiaFlight("F102", "BLR", "DEL", 1694856000, 1694862000, 5200))
    FlightDatabase.addFlight(IndiGoFlight("F103", "BLR", "DEL", 1694859000, 1694865000, 4800))

    println("🔍 Search BLR -> DEL under ₹5000:")
    val results = FlightDatabase.searchFlights("BLR", "DEL", 5000)
    results.forEach { println(it) }

    println("\n🔎 Get flight by ID (F102):")
    println(FlightDatabase.getFlightById("F102"))
}
