
// RetailSimulation.kt

// ------------------- Exceptions -------------------
open class RetailException(message: String) : RuntimeException(message)

class OrderNotFoundException(orderId: String) :
    RetailException("Order with ID $orderId not found")

class PaymentFailedException(orderId: String, reason: String) :
    RetailException("Payment failed for order $orderId: $reason")

class ProductOutOfStockException(productId: String) :
    RetailException("Product $productId is out of stock")

// ------------------- API Error -------------------
data class ApiError(
    val code: Int,
    val message: String,
    val details: String? = null
)

// ------------------- Services -------------------
class InventoryService {
    private val stock = mutableMapOf(
        "PROD1" to 10,
        "PROD2" to 0, // out of stock
        "PROD3" to 5
    )

    fun isAvailable(productId: String, quantity: Int): Boolean {
        return stock.getOrDefault(productId, 0) >= quantity
    }

    fun reduceStock(productId: String, quantity: Int) {
        val current = stock.getOrDefault(productId, 0)
        stock[productId] = current - quantity
    }
}

class PaymentService {
    fun processPayment(orderId: String, amount: Double): Boolean {
        // Simulate random payment failure for amounts > 1000
        if (amount > 1000) return false
        return true
    }
}

class OrderService(
    private val inventoryService: InventoryService,
    private val paymentService: PaymentService
) {
    // the code germ is we crete aggregation in order to execute the code 

    fun placeOrder(orderId: String, productId: String, quantity: Int, amount: Double) {
        // Check inventory
        if (!inventoryService.isAvailable(productId, quantity)) {
            throw ProductOutOfStockException(productId)
        }

        // Process payment
        if (!paymentService.processPayment(orderId, amount)) {
            throw PaymentFailedException(orderId, "Insufficient balance")
        }

        // Reduce stock
        inventoryService.reduceStock(productId, quantity)
        println("Order $orderId placed successfully for product $productId, quantity $quantity")
    }
}

// ------------------- Global Error Handler -------------------
object GlobalErrorHandler {
    fun handle(ex: Exception) {
        val apiError = when (ex) {
            is ProductOutOfStockException -> ApiError(409, ex.message ?: "Out of stock")
            is PaymentFailedException -> ApiError(402, ex.message ?: "Payment failed")
            is OrderNotFoundException -> ApiError(404, ex.message ?: "Order not found")
            else -> ApiError(500, "Internal error", ex.localizedMessage)
        }

        println("ERROR: Code=${apiError.code}, Message=${apiError.message}, Details=${apiError.details}")
    }
}

// ------------------- Main Simulation -------------------
fun main() {

    val inventoryService = InventoryService()
    val paymentService = PaymentService()
    val orderService = OrderService(inventoryService, paymentService)

    val orders = listOf(
        Triple("ORD1", "PROD1", 2),
        Triple("ORD2", "PROD2", 1),  // Out of stock
        Triple("ORD3", "PROD3", 10), // Exceeds stock
        Triple("ORD4", "PROD1", 3)   // Should succeed
    )

    for ((orderId, productId, qty) in orders) {
        try {
            val amount = qty * 500.0 // price per unit
            orderService.placeOrder(orderId, productId, qty, amount)
        } catch (ex: Exception) {
            GlobalErrorHandler.handle(ex)
        }
    }
}
