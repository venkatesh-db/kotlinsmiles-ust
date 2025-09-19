
import kotlin.random.Random
import java.time.LocalDateTime

// ------------------- Exceptions -------------------
open class AppException(val code: Int, message: String) : RuntimeException(message)

class OrderException(message: String) : AppException(1001, message)
class PaymentException(message: String) : AppException(2001, message)
class InventoryException(message: String) : AppException(3001, message)

// ------------------- Structured Log -------------------
fun logError(ex: AppException, requestId: String) {
    println(
        "[${LocalDateTime.now()}] " +
        "RequestID=$requestId, ErrorCode=${ex.code}, Message=${ex.message}"
    )
}

// ------------------- Services -------------------
class InventoryService {
    private val stock = mutableMapOf("PROD1" to 5, "PROD2" to 0)

    fun checkStock(productId: String, qty: Int) {
        if (stock.getOrDefault(productId, 0) < qty)
            throw InventoryException("Product $productId out of stock")
    }

    fun reduceStock(productId: String, qty: Int) {
        stock[productId] = stock.getOrDefault(productId, 0) - qty
    }
}

class PaymentService {
    fun processPayment(amount: Double): Boolean {
        // Simulate random payment failure
        return Random.nextBoolean()
    }
}

class OrderService(
    private val inventoryService: InventoryService,
    private val paymentService: PaymentService
) {
    fun placeOrder(requestId: String, orderId: String, productId: String, qty: Int, amount: Double) {
        try {
            // Inventory check
            inventoryService.checkStock(productId, qty)

            // Payment with simple retry mechanism
            var paymentSuccess = false
            repeat(3) { attempt ->
                if (paymentService.processPayment(amount)) {
                    paymentSuccess = true
                    return@repeat
                } else {
                    println("Retrying payment... Attempt ${attempt + 1}")
                }
            }
            if (!paymentSuccess) throw PaymentException("Payment failed for order $orderId")

            // Reduce stock
            inventoryService.reduceStock(productId, qty)
            println("Order $orderId placed successfully for $productId x$qty")

        } catch (ex: AppException) {
            // Centralized logging
            GlobalErrorHandler.handle(ex, requestId)
        }
    }
}

// ------------------- Global Error Handler -------------------
object GlobalErrorHandler {
    fun handle(ex: AppException, requestId: String) {
        logError(ex, requestId)
        // Could also send alerts to SRE / monitoring system here
    }
}

// ------------------- Main Simulation -------------------
fun main() {
    val inventoryService = InventoryService()
    val paymentService = PaymentService()
    val orderService = OrderService(inventoryService, paymentService)

    val orders = listOf(
        Triple("ORD1", "PROD1", 2),
        Triple("ORD2", "PROD2", 1),
        Triple("ORD3", "PROD1", 4)
    )

    for ((orderId, productId, qty) in orders) {
        val requestId = "REQ-${Random.nextInt(1000, 9999)}"
        orderService.placeOrder(requestId, orderId, productId, qty, qty * 500.0)
    }
}
