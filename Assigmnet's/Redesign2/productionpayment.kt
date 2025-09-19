
sealed class paymentstatus {

   object Pending:paymentstatus()
   object Completed:paymentstatus()
   data class Failed(val reason:String):paymentstatus()

}

fun handlepayment(sattus:paymentstatus){

    when (sattus){

paymentstatus.Pending -> println("pending stsus")
paymentstatus.Completed ->println("compleetd stsus")
is paymentstatus.Failed -> paymentstatus.Failed("insuffifenct balance ")
    
}

}

fun main(){

     val s1:paymentstatus = paymentstatus.Pending
     val s2:paymentstatus = paymentstatus.Completed
     val s3:paymentstatus = paymentstatus.Failed("insuffiencent balance")

    handlepayment(s3)
    handlepayment(s2)

}