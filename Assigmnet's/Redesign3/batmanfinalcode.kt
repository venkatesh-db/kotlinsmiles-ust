
// 1. Sealed Class for Transaction Status
sealed class Status {
    object Pending : Status()
    object Settled : Status()
    object Failed : Status()
}

// 2. Base Class with Modifiers
open class BaseTransaction(
    val id: Int,                 // val → immutable once assigned
    var customer: String         // var → mutable (e.g. customer name can be updated)
)

// 3. Derived Class with Default Args & Null Safety
class Transaction(
    id: Int,
    customer: String,
    val amount: Double = 0.0,                  // default arg
    val reconciledAmount: Double? = null,      // nullable type
    val status: Status = Status.Settled        // default arg with sealed class
) : BaseTransaction(id, customer) {

    // Helper method: resolve null safety with elvis operator
    fun isMismatched(): Boolean =
        amount != (reconciledAmount ?: 0.0)
}

// 4. Categorizer using one pass
fun categorizeTransactions(transactions: List<Transaction>):
        Triple<List<Transaction>, List<Transaction>, List<Transaction>> {

    val pending = mutableListOf<Transaction>()
    val highValue = mutableListOf<Transaction>()
    val mismatched = mutableListOf<Transaction>()

    for (t in transactions) {
        if (t.status is Status.Pending) pending.add(t)
        if (t.amount > 50_000) highValue.add(t)
        if (t.isMismatched()) mismatched.add(t)
    }

    return Triple(pending, highValue, mismatched)
}

// 5. Main Program
fun main() {
    val transactions = (1..1_000_000).map { id ->
        Transaction(
            id = id,
            customer = "Customer$id",
            amount = if (id % 1000 == 0) 100_000.0 else 5000.0,
            reconciledAmount = if (id % 500 == 0) null else 5000.0,  // null safety demo
            status = if (id % 200 == 0) Status.Pending else Status.Settled
        )
    }

    val (pending, highValue, mismatched) = categorizeTransactions(transactions)

    println("Pending Settlements: ${pending.size}")
    println("High Value Transactions: ${highValue.size}")
    println("Mismatched Records: ${mismatched.size}")
}
