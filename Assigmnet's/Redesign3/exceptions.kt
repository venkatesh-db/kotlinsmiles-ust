sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val msg: String, val cause: Throwable? = null) : Result<Nothing>()
}

fun parseprice(price: String): Result<Int> {
    var rest: Result<Int>
    try {
        rest = Result.Success(price.toInt())
    } catch (e: NumberFormatException) {
        rest = Result.Error("invalid price", e)
    }
    return rest
}

fun main() {
    try {
        val rets = listOf(1, 23)
        println(rets[5])  // <-- will throw IndexOutOfBoundsException
    } catch (e: IndexOutOfBoundsException) {
        println("gulab jamon open ur mouth ${e.message}")
    }

    var result = parseprice("250")

      result = parseprice("250s")

    when (result) {
        is Result.Success -> println(result.data)
        is Result.Error -> println(result.msg + " | " + result.cause)
    }
}
