
// 1. Data Model
data class Transaction(
    val id: Int,
    val customer: String,
    val amount: Double,
    val reconciledAmount: Double,
    val status: String
)

// 2. Higher-order function
fun processTransactions(transactions: List<Transaction>, rule: (Transaction) -> Boolean): List<Transaction> {
    return transactions.filter(rule)
}

// 3. Main
fun main() {
    
    val transactions = listOf(
        Transaction(1, "Alice", 60000.0, 60000.0, "SETTLED"),
        Transaction(2, "Bob", 25000.0, 0.0, "PENDING"),
        Transaction(3, "Charlie", 80000.0, 75000.0, "SETTLED"),
        Transaction(4, "David", 1500.0, 1500.0, "SETTLED"),
        Transaction(5, "Eve", 3000.0, 0.0, "PENDING")
    )

   // passing 3 function's 

    val pendingSettlements = processTransactions(transactions) { it.status == "PENDING" }
    val highValueTxns = processTransactions(transactions) { it.amount > 50000 }
    val mismatchedTxns = processTransactions(transactions) { it.amount != it.reconciledAmount }

    println("Pending Settlements: $pendingSettlements")
    println("High Value Transactions: $highValueTxns")
    println("Mismatched Records: $mismatchedTxns")
}
