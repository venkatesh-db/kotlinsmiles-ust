
// 1.product data 

data class Flights
(
  val flightid:String,
  val airlines:String,
  val source:String,
  val destionation:String,
  val depature:String,
  val arrival:String,
  val price:Int
)

// 2.product data strutures

val flight=mutableListOf<Flights>()

// 3. design 

fun addFlights( flighte:Flights){

   // 4. flow of code 

    flight.add(flighte)

    // 1. Arguments & return type 
    // 2. error handling 
    // 3. SRP priciple 

    // 4. logics --> Lib methods

}

fun getFlightByIds( id:String):Flights?{

      // 4. logics --> Lib methods

  return flight.find { it.flightid == id}
}

fun searchFlights(source:String , destionation:String,price:Int  ):List<Flights?>{

    // mutableListOf<Flights> in to Sequence<Flights>
  return   flight.asSequence().filter { it.source == source && it.destionation==destionation }.filter { it.price <= price }.sortedBy {it.price }.toList()

}

fun main(){

    // 5. executions

    addFlights( Flights("6144", "Indigo", "BLR", "TIR", "10:25 ","11:35" , 7000))

    addFlights( Flights("7144", "sharathfly", "BLR", "KOC", "7:25 ","9:35" , 1000))

    addFlights( Flights("7777", "flyvishnu", "BLR", "CAL", "10:25 ","11:35" , 900))

   val results= searchFlights("BLR", "BLR",900)

   println("search"+results)

   println(getFlightByIds("7777"))

}