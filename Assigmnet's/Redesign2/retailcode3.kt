
// Step 1: Sealed class for promotions
sealed class Promotion {

    data class FlatDiscount(val amount: Double) : Promotion()
    data class PercentageDiscount(val percent: Double) : Promotion()
    data class BuyOneGetOneFree(val productId: Int) : Promotion()
    object NoPromotion : Promotion()
}

// Step 2: Product class with promotion logic
data class Product(val id: Int, val name: String, var price: Double, var quantity: Int = 1) {

    fun applyPromotion(promotion: Promotion) {

        when (promotion) {
            is Promotion.FlatDiscount -> price -= promotion.amount
            is Promotion.PercentageDiscount -> price -= price * (promotion.percent / 100)
            is Promotion.BuyOneGetOneFree -> if (promotion.productId == id) quantity += 1
            Promotion.NoPromotion -> {}
        }
    }
}

// Step 3: Promotion Engine
object PromotionEngine {

    fun applyPromotions(products: List<Product>, promotions: Map<Int, Promotion>): List<Product> {
        
        products.forEach { product ->

            //  Product(101, "T-Shirt", 1500.0)  --> 101 
            // promotions -> key 101  --> Promotion.PercentageDiscount

            val promo = promotions[product.id] ?: Promotion.NoPromotion
            product.applyPromotion(promo)
        }
        return products
    }
}

// MAIN: Simulation
fun main() {

    val products = listOf(
        Product(101, "T-Shirt", 1500.0),
        Product(102, "Shoes", 3000.0),
        Product(103, "Watch", 5000.0)
    )

    val promotions = mapOf(
        101 to Promotion.PercentageDiscount(20.0), // 20% off on T-Shirt
        102 to Promotion.FlatDiscount(500.0),     // ₹500 off on Shoes
        103 to Promotion.BuyOneGetOneFree(103)    // BOGO on Watch
    )

    val finalProducts = PromotionEngine.applyPromotions(products, promotions)

    println("=== Final Bill ===")
    finalProducts.forEach {
        println("${it.name} | Price: ${it.price} | Quantity: ${it.quantity}")
    }
}
