package adrian.ma.footwearshop.view.adapters

import adrian.ma.footwearshop.R
import adrian.ma.footwearshop.model.CartItem
import adrian.ma.footwearshop.repository.CartRepository
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import java.lang.NumberFormatException

class CartItemAdapter (private var context: Context,
                       private var cartRepository: CartRepository,
                       private var totalTextView: TextView
                       ) :
    RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    class CartItemViewHolder(val cartItemView: View) : RecyclerView.ViewHolder(cartItemView) {
        var brandTextView: TextView = itemView.findViewById(R.id.cartItemBrandTextView)
        var descriptionTextView: TextView = itemView.findViewById(R.id.cartItemDescriptionTextView)
        var priceTextView: TextView = itemView.findViewById(R.id.cartItemPriceTextView)
        var quantityEditView: EditText = itemView.findViewById(R.id.cartItemQuantityEditText)
        var subtotalTextView: TextView = itemView.findViewById(R.id.cartItemSubtotalTextView)
        var image: ImageView = itemView.findViewById(R.id.cartItemImage)
        var removeButton: Button = itemView.findViewById(R.id.cartItemRemoveButton)
        var updateButton: Button = itemView.findViewById(R.id.cartItemUpdateButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.view_cart_item, parent, false)
        return CartItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartRepository.cartItems.size
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val cartItem: CartItem = cartRepository.cartItems.get(position)

        holder.brandTextView.text = cartItem.product.brand.string
        holder.descriptionTextView.text = "${cartItem.product.gender.string} ${cartItem.product.type.string}"
        holder.priceTextView.text = "${cartItem.product.price}€"
        holder.quantityEditView.setText("${cartItem.quantity}")
        holder.subtotalTextView.text = "Subtotal: ${cartItem.subtotal()}€"
        holder.image.setImageResource(cartItem.product.imageResource)

        holder.removeButton.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("${cartItem.product.brand.string}, ${cartItem.product.gender.string} ${cartItem.product.type.string}")
                .setMessage("Are you sure you want to remove this product from your cart?")
                .setPositiveButton("Yes") { dialog, id ->
                    cartRepository.cartItems.removeAt(position)
                    notifyItemRemoved(position)
                    totalTextView.text = "Total: ${cartRepository.total()}€"

                    Toast.makeText(
                        context,
                        "${cartItem.product.brand.string}, ${cartItem.product.gender.string} ${cartItem.product.type.string}, removed from your cart",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton("Cancel") { dialog, id ->
                }
                .create().show()
        }

        holder.updateButton.setOnClickListener {
            var text: String = holder.quantityEditView.text.toString()
            try {
                val no = text.toInt()
                if (!(no >= 1 || no <= 100)) {
                    throw NumberFormatException()
                }

                cartItem.quantity = no
                holder.subtotalTextView.text = "Subtotal: ${cartItem.subtotal()}€"
                totalTextView.text = "Total: ${cartRepository.total()}€"
                notifyDataSetChanged()

                Toast.makeText(
                    context,
                    "Quantity updated for ${cartItem.product.brand.string}, ${cartItem.product.gender.string} ${cartItem.product.type.string}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            catch (exception : NumberFormatException) {
                Toast.makeText(context, "Invalid quantity\n(must be an integer between 1 and 100)", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
