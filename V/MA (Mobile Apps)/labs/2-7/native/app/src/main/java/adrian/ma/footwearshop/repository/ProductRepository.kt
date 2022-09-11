package adrian.ma.footwearshop.repository

import adrian.ma.footwearshop.R
import adrian.ma.footwearshop.model.*
import java.io.Serializable

class ProductRepository : Serializable {
    var products: MutableList<Product> = mutableListOf()

    fun populate() {
        var product: Product

        product = Product(Brand.DR_MARTENS, Gender.UNISEX, Type.BOOTS, 189, R.drawable.dr_martens)
        products.add(product)

        product = Product(Brand.LOUBOUTIN, Gender.FEMALE, Type.HIGH_HEELS, 699, R.drawable.louboutin)
        products.add(product)

        product = Product(Brand.VALENTINO, Gender.FEMALE, Type.HIGH_HEELS, 749, R.drawable.valentino)
        products.add(product)
    }
}
