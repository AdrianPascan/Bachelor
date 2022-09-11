package adrian.ma.footwearshop.model

import java.io.Serializable

enum class Brand(val string: String) : Serializable {
    DR_MARTENS("Dr. Martens"),
    VALENTINO("Valentino Garavani"),
    LOUBOUTIN("Christian Louboutin")
}
