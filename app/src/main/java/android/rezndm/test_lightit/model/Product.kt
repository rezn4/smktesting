package android.rezndm.test_lightit.model

import com.google.gson.annotations.SerializedName

data class Product(
    var id: Int,
    var title: String,
    @SerializedName("img")
    var image: String,
    @SerializedName("text")
    var description: String
)