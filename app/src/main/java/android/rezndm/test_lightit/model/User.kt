package android.rezndm.test_lightit.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("Id")
    var id: Int,
    @SerializedName("Username")
    var username: String,
    @SerializedName("Password")
    var password: String
)