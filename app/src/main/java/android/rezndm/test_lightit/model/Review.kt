package android.rezndm.test_lightit.model

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("id")
    var id: Int,
    @SerializedName("rate")
    var rate: Int,
    @SerializedName("text")
    var comment: String,
    @SerializedName("Id_user")
    var userId: Int,
    @SerializedName("Id_entry")
    var productId: Int
)