
// Base sealed class for different promotions
sealed class Promotion {

    data class FlatDiscount(val amount: Double) : Promotion()
    data class PercentageDiscount(val percent: Double) : Promotion()
    object NoPromotion : Promotion()
}

// Open class for extensibility
open class Product(val id: Int, val name: String, var price: Double) {

    open fun applyPromotion(promotion: Promotion) {

        when (promotion) {
            is Promotion.FlatDiscount -> price -= promotion.amount
            is Promotion.PercentageDiscount -> price -= price * (promotion.percent / 100)
            Promotion.NoPromotion -> {} // Do nothing
        }
    }
}

// Final class cannot be extended
final class GroceryProduct(id: Int, name: String, price: Double) : Product(id, name, price)

// MAIN
fun main() {

    val laptop = Product(201, "Laptop", 50000.0)
    val apple = GroceryProduct(202, "Apple", 200.0)

    laptop.applyPromotion(Promotion.PercentageDiscount(10.0)) // passing object 
    apple.applyPromotion(Promotion.FlatDiscount(20.0))        // passing object 

    println("Laptop final price: ${laptop.price}")
    println("Apple final price: ${apple.price}")
}
