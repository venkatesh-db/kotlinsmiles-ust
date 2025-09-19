
data class Train(
    val trainNo: String,
    val name: String,
    val source: String,
    val destination: String,
    val departureTime: String,  // "22:30"
    val arrivalTime: String,    // "05:45"
    val fare: Int,
    val classType: String       // "Sleeper", "3AC", "2AC", "1AC"
)

// Mutable storage for trains
val trains = mutableListOf<Train>()

// Add train
fun addTrain(train: Train) {
    trains.add(train)
}

// Search trains by source & destination
fun searchTrains(
    source: String,
    destination: String,
    maxFare: Int? = null,
    classType: String? = null
): List<Train> {
    return trains.asSequence()
        .filter { it.source.equals(source, ignoreCase = true) }
        .filter { it.destination.equals(destination, ignoreCase = true) }
        .filter { maxFare == null || it.fare <= maxFare }
        .filter { classType == null || it.classType.equals(classType, ignoreCase = true) }
        .sortedBy { it.fare } // Cheapest train first
        .toList()
}

// Get train by number
fun getTrainByNo(trainNo: String): Train? {
    return trains.find { it.trainNo == trainNo }
}

// Example usage (simulate IRCTC search)
fun main() {
    // Adding sample trains (like IRCTC database)
    addTrain(Train("12627", "Karnataka Express", "BLR", "DEL", "20:00", "10:30", 2200, "Sleeper"))
    addTrain(Train("12628", "Rajdhani Express", "BLR", "DEL", "18:00", "08:00", 3200, "3AC"))
    addTrain(Train("12629", "Duronto Express", "BLR", "DEL", "22:00", "11:00", 2800, "2AC"))
    addTrain(Train("12630", "Shatabdi Express", "BLR", "DEL", "06:00", "19:00", 1500, "Sleeper"))

    println("🔍 Search BLR → DEL under ₹3000 (only AC classes):")
    val results = searchTrains("BLR", "DEL", maxFare = 3000, classType = "2AC")
    results.forEach { println(it) }

    println("\n🔎 Get train by number 12628:")
    println(getTrainByNo("12628"))
}
