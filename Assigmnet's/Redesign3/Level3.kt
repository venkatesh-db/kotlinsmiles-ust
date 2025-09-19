
// Define the Product model first
data class Product(
    val id: Int,
    val name: String,
    val stock: Int,
    val price: Double
)

// Higher-order transformer for discounts
fun applyDiscount(products: List<Product>, discountFn: (Product) -> Product): List<Product> =
    products.map(discountFn)

// Inventory alerts (composed functions)
fun generateAlerts(products: List<Product>): List<String> =
    products.filter { it.stock < 3 }
            .map { "ALERT: ${it.name} has only ${it.stock} left!" }

// Full pipeline
fun main() {
    val products = listOf(
        Product(1, "Laptop", 10, 60000.0),
        Product(2, "Phone", 0, 25000.0),
        Product(3, "Headphones", 5, 2000.0),
        Product(4, "Keyboard", 2, 1500.0)
    )

    // Apply discount using a lambda
    val discounted = applyDiscount(products) { p ->
        if (p.price > 20000) p.copy(price = p.price * 0.85) else p
    }

    // Generate alerts for low stock
    val alerts = generateAlerts(products)

    // Generate final report
    val report = discounted.map { "${it.name} → ₹${it.price}" }

    println("📉 Discounted: $report")
    println("🚨 Alerts: $alerts")
}
