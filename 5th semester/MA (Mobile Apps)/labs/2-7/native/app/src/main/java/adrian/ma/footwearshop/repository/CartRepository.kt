package adrian.ma.footwearshop.repository

import adrian.ma.footwearshop.R
import adrian.ma.footwearshop.model.*
import java.io.Serializable

class CartRepository : Serializable {
    var cartItems: MutableList<CartItem> = mutableListOf()

    fun total(): Int {
        return cartItems.map { cartItem -> cartItem.subtotal() }.sum();
    }

    fun populate(productRepository: ProductRepository) {
        var products = productRepository.products

        for (index in (products.size - 1) downTo 1) {
            var cartItem = CartItem(products.get(index), index)
            cartItems.add(cartItem)
        }
    }
}
