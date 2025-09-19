
// 1. Data Class for transaction
data class Transaction(
    val id: String,
    val amount: Double,
    val currency: String,
    val customerId: String
)

// 2. Sealed class for Payment Providers
sealed class PaymentProvider {
    object Visa : PaymentProvider()
    object MasterCard : PaymentProvider()
    object PayPal : PaymentProvider()
    object ApplePay : PaymentProvider()
    object GooglePay : PaymentProvider()
}

// 3. Interfaces for reusable behaviors
interface Refundable {
    fun refund(transactionId: String): Boolean
}

interface Compliant {
    fun checkCompliance(): Boolean
}

// 4. Abstract class for Payment Gateway
abstract class PaymentGateway(val provider: PaymentProvider) : Refundable, Compliant {
    abstract fun pay(transaction: Transaction): Boolean
}

// 5. Implementations of gateways
class VisaGateway : PaymentGateway(PaymentProvider.Visa) {
    override fun pay(transaction: Transaction): Boolean {
        println("Processing Visa payment of ${transaction.amount} ${transaction.currency}")
        return true
    }

    override fun refund(transactionId: String): Boolean {
        println("Refund processed via Visa for $transactionId")
        return true
    }

    override fun checkCompliance(): Boolean {
        println("Visa PCI-DSS compliance check passed")
        return true
    }
}

class PayPalGateway : PaymentGateway(PaymentProvider.PayPal) {
    override fun pay(transaction: Transaction): Boolean {
        println("Processing PayPal payment of ${transaction.amount} ${transaction.currency}")
        return true
    }

    override fun refund(transactionId: String): Boolean {
        println("Refund processed via PayPal for $transactionId")
        return true
    }

    override fun checkCompliance(): Boolean {
        println("PayPal PSD2 compliance check passed")
        return true
    }
}

// 6. Singleton using Companion Object
class PaymentProcessor private constructor() {
    companion object {
        private val gateways = mapOf(
            PaymentProvider.Visa to VisaGateway(),
            PaymentProvider.PayPal to PayPalGateway()
        )

        fun getGateway(provider: PaymentProvider): PaymentGateway? {
            return gateways[provider]
        }
    }
}

// 7. Usage
fun main() {
    val txn = Transaction("TXN1001", 250.0, "GBP", "cust123")

    val visaGateway = PaymentProcessor.getGateway(PaymentProvider.Visa)
    visaGateway?.checkCompliance()
    visaGateway?.pay(txn)
    visaGateway?.refund(txn.id)

    val paypalGateway = PaymentProcessor.getGateway(PaymentProvider.PayPal)
    paypalGateway?.checkCompliance()
    paypalGateway?.pay(txn)
}
