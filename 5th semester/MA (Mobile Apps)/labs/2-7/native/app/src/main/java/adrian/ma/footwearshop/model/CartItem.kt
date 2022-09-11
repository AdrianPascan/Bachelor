package adrian.ma.footwearshop.model

import java.io.Serializable

class CartItem (var product: Product,
                var quantity: Int) : Serializable
{
    fun subtotal(): Int {
        return product.price * quantity
    }
}
