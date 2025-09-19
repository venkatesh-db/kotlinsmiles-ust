
// 1. Data Model

data class Transaction(
    val id: Int,
    val customer: String,
    val amount: Double,
    val reconciledAmount: Double,
    val status: String
)

// 2. One-pass categorizer
fun categorizeTransactions(transactions: List<Transaction>):
        Triple<List<Transaction>, List<Transaction>, List<Transaction>> {


    val pending = mutableListOf<Transaction>()
    val highValue = mutableListOf<Transaction>()
    val mismatched = mutableListOf<Transaction>()

    for (t in transactions) {
        
        if (t.status == "PENDING") pending.add(t)
        if (t.amount > 50_000) highValue.add(t)
        if (t.amount != t.reconciledAmount) mismatched.add(t)
    }

    return Triple(pending, highValue, mismatched)
}

// 3. Main
fun main() {
    // Example: simulate 1M transactions


 // (1..2).map {  }

    val transactions = (1..1_000_000).map { id ->
        Transaction(
            id = id,
            customer = "Customer$id",
            amount = if (id % 1000 == 0) 100_000.0 else 5000.0,
            reconciledAmount = if (id % 500 == 0) 0.0 else 5000.0,
            status = if (id % 200 == 0) "PENDING" else "SETTLED"
        )
    }

    val (pending, highValue, mismatched) = categorizeTransactions(transactions)

    println("Pending Settlements: ${pending.size}")
    println("High Value Transactions: ${highValue.size}")
    println("Mismatched Records: ${mismatched.size}")
}
