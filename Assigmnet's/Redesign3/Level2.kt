
// Define Product model first
data class Product(
    val id: Int,
    val name: String,
    val stock: Int,
    val price: Double
)

// Higher-order function to process stock
fun processStock(products: List<Product>, rule: (Product) -> Boolean): List<Product> {
    return products.filter(rule)
}

fun main() {
    
    val products = listOf(
        Product(1, "Laptop", 10, 60000.0),
        Product(2, "Phone", 0, 25000.0),
        Product(3, "Headphones", 5, 2000.0),
        Product(4, "Keyboard", 2, 1500.0)
    )

    val outOfStock = processStock(products) { it.stock == 0 }
    val criticalStock = processStock(products) { it.stock < 3 }
    val premiumProducts = processStock(products) { it.price > 20000 }

    println("Out of stock: $outOfStock")
    println("Critical stock: $criticalStock")
    println("Premium: $premiumProducts")
}
