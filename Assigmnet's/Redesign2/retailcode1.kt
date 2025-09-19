

// Basic class representing a Product

class Product(val id: Int, val name: String, var price: Double) {

    fun displayInfo() {
        println("Product: $name | Price: $price")
    }
    /* 
     fun applyDiscount( discount: Double) {
        this.price -= discount
        println("Discount of $discount applied! New Price: ${this.price}")
    }
    */
}

//data class Products(val id: Int, val name: String, var price: Double)

// Object for static promotions (singleton)
object PromotionEngine {

    fun applyDiscount(product: Product, discount: Double) {
        product.price -= discount
        println("Discount of $discount applied! New Price: ${product.price}")
    }

}

// MAIN
fun main() {

    val product = Product(101, "Shoes", 2000.0)
    product.displayInfo()

    // Apply discount
    PromotionEngine.applyDiscount(product, 200.0)
}
