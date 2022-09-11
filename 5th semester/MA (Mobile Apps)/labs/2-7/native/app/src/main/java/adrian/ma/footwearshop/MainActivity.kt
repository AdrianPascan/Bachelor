package adrian.ma.footwearshop

import adrian.ma.footwearshop.repository.CartRepository
import adrian.ma.footwearshop.repository.ProductRepository
import adrian.ma.footwearshop.view.adapters.ProductAdapter
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

const val CART_MAIN = "cart_main"
const val REQUEST_CODE = 1

class MainActivity : AppCompatActivity() {
    private var productRepository: ProductRepository = ProductRepository()
    private var cartRepository: CartRepository = CartRepository()

    init {
        productRepository.populate()
        cartRepository.populate(productRepository)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var temporary = intent.getSerializableExtra(CART_MAIN)
        if (temporary != null) {
            cartRepository = temporary as CartRepository
        }

        recyclerView = mainRecyclerView

        viewAdapter = ProductAdapter(this, productRepository, cartRepository)
        recyclerView.adapter = viewAdapter

        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun switchToCart(view: View) {
        var mainIntent = Intent(this, CartActivity::class.java)
        var bundle = Bundle()
        bundle.putSerializable(CART_MAIN, cartRepository)
        mainIntent.putExtras(bundle)
        startActivityForResult(mainIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                cartRepository = data?.extras?.getSerializable(CART_CHILD) as CartRepository
            }
        }
    }
}
