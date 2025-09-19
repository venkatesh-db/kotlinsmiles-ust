
// 1. Core Data Class
data class Trains(
    val trainno: String,
    val name: String,
    val source: String,
    val destination: String,
    val departureTime: Long,
    val arrivalTime: Long,
    val fare: Int,
    val classtype: String
)

// 2. Interfaces
interface Bookable {
    fun bookSeat(userId: String, seatNo: Int): String
}

interface Cancellable {
    fun cancelBooking(bookingId: String): String
}

interface Notifiable {
    fun sendNotification(message: String)
}

// 3. Abstract Base Class
abstract class TrainService(val train: Trains) {
    abstract fun getFare(): Int

    companion object {

        private val services = mutableListOf<TrainService>()

        fun addService(service: TrainService) {
            services.add(service)
        }

        fun searchTrains(source: String, destination: String): List<Trains> {
            return services.map { it.train }
                .filter { t -> 
                    t.source.equals(source, true) && 
                    t.destination.equals(destination, true) 
                }
        }

        fun getByTrainNo(trainNo: String): TrainService? {
            return services.find { it.train.trainno == trainNo }
        }
    }
}

// 4. Concrete Implementations
class ExpressTrain(train: Trains) : TrainService(train), Bookable, Cancellable, Notifiable {
   
    override fun getFare(): Int = train.fare

    override fun bookSeat(userId: String, seatNo: Int): String {
        val bookingId = "${train.trainno}-$seatNo"
        println("Seat $seatNo booked for $userId on ${train.name}")
        return bookingId
    }

    override fun cancelBooking(bookingId: String): String {
        println("Booking $bookingId cancelled on ${train.name}")
        return "Cancelled"
    }

    override fun sendNotification(message: String) {
        println("Notification for ${train.name}: $message")
    }
}

class SuperFastTrain(train: Trains) : TrainService(train), Bookable {
    override fun getFare(): Int = (train.fare * 1.2).toInt()

    override fun bookSeat(userId: String, seatNo: Int): String {
        val bookingId = "${train.trainno}-$seatNo"
        println("Superfast Seat $seatNo booked for $userId on ${train.name}")
        return bookingId
    }
}

// 5. Usage
fun main() {
    val express = ExpressTrain(
        Trains("123457","Kerala Express","TRV","BLR",16575779,203555,1000,"AC")
    )
    val superfast = SuperFastTrain(
        Trains("223457","South Superfast","TRV","BLR",16575779,203555,1200,"Sleeper")
    )

    TrainService.addService(express)
    TrainService.addService(superfast)

    // Search
    val results = TrainService.searchTrains("TRV","BLR")
    println("Search Results: $results")

    // Book
    val bookingId = express.bookSeat("user123", 42)
    println("Booking ID: $bookingId")

    // Cancel
    express.cancelBooking(bookingId)

    // Fare check
    println("Express Fare: ${express.getFare()}")
    println("Superfast Fare: ${superfast.getFare()}")
}
