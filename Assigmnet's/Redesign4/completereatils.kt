
// Abstract Base Entity
abstract class Transaction(val id: String, val amount: Double) {
    abstract fun process(): Boolean
}

// Interfaces for cross-cutting concerns
interface FraudCheck {
    fun validate(txn: Transaction): Boolean
}
interface InvoiceGenerator {
    fun generateInvoice(orderId: String, amount: Double): String
}
interface LoyaltyProgram {
    fun applyLoyalty(customerId: String, amount: Double): Double
}

// Payment Gateway
class CardPayment(id: String, amount: Double, val cardNo: String) : Transaction(id, amount) {
    override fun process(): Boolean {
        println("Processing card payment $id for $amount")
        return true
    }
}

// POS Processing
class POSProcessor {
    fun handlePayment(txn: Transaction): Boolean {
        println("POS processing for txn ${txn.id}")
        return txn.process()
    }
}

// Order & Invoice
class OrderService(private val invoiceGen: InvoiceGenerator) {
    fun createOrder(orderId: String, txn: Transaction) {
        val invoice = invoiceGen.generateInvoice(orderId, txn.amount)
        println("Order $orderId created with Invoice: $invoice")
    }
}

// Fraud Detection
class FraudDetectionService : FraudCheck {
    override fun validate(txn: Transaction): Boolean {
        println("Running fraud checks on txn ${txn.id}")
        return true // assume valid
    }
}

// Settlement (Singleton)
class SettlementManager private constructor() {
    companion object {
        private val instance = SettlementManager()
        fun getInstance() = instance
    }
    fun settle(txn: Transaction) {
        println("Settling transaction ${txn.id} of amount ${txn.amount}")
    }
}

// Loyalty Program
class LoyaltyService : LoyaltyProgram {
    override fun applyLoyalty(customerId: String, amount: Double): Double {
        val discount = amount * 0.05
        println("Applied loyalty discount of $discount for $customerId")
        return amount - discount
    }
}

// Invoice Generator Impl
class SimpleInvoiceGenerator : InvoiceGenerator {
    override fun generateInvoice(orderId: String, amount: Double): String {
        return "INV-$orderId-${amount.toInt()}"
    }
}

// === System Integration ===
fun main() {
    val txn = CardPayment("TXN1001", 1000.0, "1234-5678-9012")

    val pos = POSProcessor()
    if (pos.handlePayment(txn)) {
        val fraudService = FraudDetectionService()
        if (fraudService.validate(txn)) {
            val loyalty = LoyaltyService()
            val discountedAmount = loyalty.applyLoyalty("CUST101", txn.amount)

            val orderService = OrderService(SimpleInvoiceGenerator())
            orderService.createOrder("ORD1001", txn)

            val settlement = SettlementManager.getInstance()
            settlement.settle(CardPayment("TXN1001", discountedAmount, "1234-5678"))
        }
    }
}
