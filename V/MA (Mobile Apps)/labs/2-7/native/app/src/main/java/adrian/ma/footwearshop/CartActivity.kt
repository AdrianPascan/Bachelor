package adrian.ma.footwearshop

import adrian.ma.footwearshop.repository.CartRepository
import adrian.ma.footwearshop.view.adapters.CartItemAdapter
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_cart.*

const val CART_CHILD = "cart_child"

class CartActivity () : AppCompatActivity() {
    private lateinit var cartRepository : CartRepository

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartRepository = intent.extras?.getSerializable(CART_MAIN) as CartRepository

        recyclerView = cartRecyclerView

        viewAdapter = CartItemAdapter(this, cartRepository, cartTotalTextView)
        recyclerView.adapter = viewAdapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        cartTotalTextView.text = "Total: ${cartRepository.total()}€"
    }

    override fun onBackPressed() {
        var data = Intent()
        var bundle = Bundle()
        bundle.putSerializable(CART_CHILD, cartRepository)
        data.putExtras(bundle)

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}
