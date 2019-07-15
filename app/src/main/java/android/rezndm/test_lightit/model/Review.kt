package android.rezndm.test_lightit.model

import com.google.gson.annotations.SerializedName

class Review(
    var id: Int,
    @SerializedName("product")
    var productId: Int,
    var rate: Int,
    var text: String,
    @SerializedName("created_by")
    var user: User,
    @SerializedName("created_at")
    var createDate: String

){
    class User(
        var id: Int,
        var username: String,
        var email: String
    )
}