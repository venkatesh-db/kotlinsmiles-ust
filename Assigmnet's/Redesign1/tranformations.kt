

val storesales = listOf(50000, 200000 ,1000000 ,100000)
val retailstore = mutableListOf("free marign","kunil")

fun main(){

   retailstore.add("Jai mart")
   val ret=storesales.sorted()
   val rest=storesales.filter { it > 100000}
   println(rest)
   println(ret)

   val rest3=storesales.map{it -1000 }
  println(rest3)

val rest45=storesales.any{ it > 200000 }
  println(rest45)

  val rest44=storesales.all{ it >= 50000 }
  println(rest44)

   val rest78=storesales.sum()
     println(rest78)


    val sums= storesales.reduce { acc,n->acc+n+n}

        println(sums)
}