package adrian.ma.footwearshop.model

import java.io.Serializable

class Product(var brand: Brand,
               var gender: Gender,
               var type: Type,
               var price: Int,
               var imageResource: Int) : Serializable {

    override fun equals(other: Any?): Boolean {
        return (other is Product)
                && brand.equals(other.brand)
                && gender.equals(other.gender)
                && type.equals(other.type)
    }
}
