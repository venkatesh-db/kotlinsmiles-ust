
sealed class operatortariffic {

     object towerdown:operatortariffic()
     object towerup:operatortariffic()

     class netwrokfailure(val resaon:String):operatortariffic(){

        fun logerror(){
          println(resaon)
       }
       
     }
}

fun main()
{

   val technopark =operatortariffic.netwrokfailure("no tower")

  technopark.logerror()

  when(technopark){
     is operatortariffic.netwrokfailure ->{

         val technopark =  operatortariffic.netwrokfailure("no tower")
         technopark.logerror()
     }
  }

}