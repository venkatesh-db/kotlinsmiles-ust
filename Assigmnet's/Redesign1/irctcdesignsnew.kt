

import kotlin.collections.mapOf
import kotlin.collections.getValue
// 1.product data 

data class Trains(
    val trainno: String,
    val name: String,
    val source: String,
    val destination: String,
    val departureTime: Long,
    val arrivalTime: Long,
    val fare: Int,
    val classtype:String
)

// 2.product data strutures

val trainss = mutableListOf< Map<String,List<Trains>>>()


fun addTrain(trai:Map<String,List<Trains>>)
{
      trainss.add( trai)
}

fun searchTrain( source :String,destination:String ) :List<Trains>{
    
    return trainss
        .flatMap { it.values.flatten() } // flatten all lists
        .filter { it.source.equals(source, ignoreCase = true) && it.destination.equals(destination, ignoreCase = true) }


}

fun getTRainno(trainno: String):Trains? {
    
   return trainss
        .flatMap { it.values.flatten() }
        .find { it.trainno == trainno }
}


fun main(){
    

 val firsttrain= mapOf("express" to listOf(Trains("123457","kerla express","trv","blr",16575779 ,203555,1000,"ac"),
    Trains("223457","south express","trv","blr",16575779 ,203555,1000,"ac"),
    Trains("423457","north express","sbc","CGM",16575779 ,203555,1000,"ac"),
    ))

    addTrain(firsttrain)

     val secondtrain= mapOf("superfast" to listOf(Trains("123457","kerla express","trv","blr",16575779 ,203555,1000,"ac"),
    Trains("223457","south express","trv","blr",16575779 ,203555,1000,"ac"),
    Trains("423457","north express","sbc","CGM",16575779 ,203555,1000,"ac"),
    ))

   addTrain(secondtrain)
   println(trainss)

 
   val retss=getTRainno("423457")

      println("venkatesh returns" +retss)


      val searchrest=searchTrain("sbc","CGM")

       println("ust basha returns" +searchrest)

}