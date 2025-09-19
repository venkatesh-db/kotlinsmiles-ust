
data class delivery(val start:Int,val endtime:Int);

fun main(){


    val trainingroom :Triple<String,Int,String> =Triple("slides",5,"retail")
    print(trainingroom.first)
    print(trainingroom.second)


    val trainingroom1 :Triple<MutableList<delivery>,List<Int>,String> =Triple(mutableListOf(delivery(9,6)),listOf(),"retail")
    print(trainingroom1.first)
    print(trainingroom1.second)

    for ( i in trainingroom.toList()){ // ["slides",5,"retail"]
      
         println(i)
         println("count")

    }

    //                       content-slide 4 hrs industry - retail
}