package adrian.ma.footwearshop.model

import java.io.Serializable

enum class Gender (val string: String) : Serializable {
    MALE("men's"),
    FEMALE("women's"),
    UNISEX("unisex")
}
