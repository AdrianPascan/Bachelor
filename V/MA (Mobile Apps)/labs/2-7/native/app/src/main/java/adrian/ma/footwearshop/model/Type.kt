package adrian.ma.footwearshop.model

import java.io.Serializable

enum class Type(val string: String) : Serializable {
    BOOTS("boots"),
    HIGH_HEELS("high heels")
}
