
data class Product(val id: Int, val name: String, val stock: Int, val price: Double)

fun main() {
    
    val products = listOf(
        Product(1, "Laptop", 10, 60000.0),
        Product(2, "Phone", 0, 25000.0),
        Product(3, "Headphones", 5, 2000.0),
        Product(4, "Keyboard", 2, 1500.0)
    )

    // Filter → products out of stock
    val outOfStock = products.filter { it.stock == 0 }

    // Map → apply discount
    val discounted = products.map { it.copy(price = it.price * 0.9) }

    // Lambda → stock check inline
    val lowStock = products.filter { it.stock in 1..3 }

    println("Out of stock: $outOfStock")
    println("Discounted products: $discounted")
    println("Low stock: $lowStock")
}
