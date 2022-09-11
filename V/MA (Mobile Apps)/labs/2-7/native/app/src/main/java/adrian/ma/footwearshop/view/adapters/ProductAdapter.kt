package adrian.ma.footwearshop.view.adapters

import adrian.ma.footwearshop.CartActivity
import adrian.ma.footwearshop.R
import adrian.ma.footwearshop.model.CartItem
import adrian.ma.footwearshop.model.Product
import adrian.ma.footwearshop.repository.CartRepository
import adrian.ma.footwearshop.repository.ProductRepository
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter (private val context: Context,
                      private val productRepository: ProductRepository,
                      private val cartRepository: CartRepository) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val productView: View) : RecyclerView.ViewHolder(productView) {
        var brandTextView: TextView = itemView.findViewById(R.id.productBrandTextView)
        var descriptionTextView: TextView = itemView.findViewById(R.id.productDescriptionTextView)
        var priceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)
        var image: ImageView = itemView.findViewById(R.id.productImage)
        var cardView: View = itemView.findViewById(R.id.productCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.view_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productRepository.products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product: Product = productRepository.products.get(position)

        holder.brandTextView.text = product.brand.string
        holder.descriptionTextView.text = "${product.gender.string} ${product.type.string}"
        holder.priceTextView.text = "${product.price}€"
        holder.image.setImageResource(product.imageResource)

        holder.cardView.setOnClickListener(View.OnClickListener {
            AlertDialog.Builder(context)
                .setTitle("${product.brand.string}, ${product.gender.string} ${product.type.string}")
                .setMessage("Add this product to your cart?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                    if (cartRepository.cartItems
                            .asSequence()
                            .any { cartItem -> cartItem.product.equals(product) }) {
                        Toast.makeText(
                            context,
                            "${product.brand.string}, ${product.gender.string} ${product.type.string}, is already in your cart",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else {
                        var cartItem: CartItem = CartItem(product, 1)
                        cartRepository.cartItems.add(cartItem)

                        Toast.makeText(
                            context,
                            "${product.brand.string}, ${product.gender.string} ${product.type.string}, was added to your cart",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener() { dialog, id ->
                })
                .create().show()
        })
    }
}
