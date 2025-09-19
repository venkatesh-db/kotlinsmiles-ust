
// Abstract Entity
abstract class Product(val id: Int, val name: String, val price: Double) {
    abstract fun calculateFinalPrice(): Double
}

// Interfaces for extensibility
interface Discountable {
    fun applyDiscount(): Double
}
interface StockCheck {
    fun checkAvailability(): Boolean
}

// Concrete Implementation
class GroceryProduct(id: Int, name: String, price: Double, private val discountRate: Double) :
    Product(id, name, price), Discountable, StockCheck {

    override fun applyDiscount(): Double = price * (1 - discountRate)

    override fun checkAvailability(): Boolean = true // Imagine DB check

    override fun calculateFinalPrice(): Double = applyDiscount()
}

// Repository Layer (DB simulation)
object ProductRepository {
    private val products = mutableListOf<Product>()

    fun addProduct(product: Product) = products.add(product)

    fun getAllProducts() = products
}

// Service Layer
class CheckoutService private constructor() {
    
    companion object {
        private val instance = CheckoutService()
        fun getInstance() = instance
    }

    fun checkout(cart: List<Product>) {
        var total = 0.0
        for (p in cart) {
            if ((p as StockCheck).checkAvailability()) {
                total += p.calculateFinalPrice()
            }
        }
        println("Total Payable Amount: $total")
    }
}

// Usage
fun main() {
    val apple = GroceryProduct(1, "Apple", 100.0, 0.1)
    val milk = GroceryProduct(2, "Milk", 50.0, 0.05)

    ProductRepository.addProduct(apple)
    ProductRepository.addProduct(milk)

    val checkoutService = CheckoutService.getInstance()
    checkoutService.checkout(ProductRepository.getAllProducts())
}
