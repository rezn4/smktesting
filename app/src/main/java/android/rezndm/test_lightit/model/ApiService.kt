package android.rezndm.test_lightit.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @POST("api/register/")
    @FormUrlEncoded
    fun registerRequest(@Field("username") username: String,
                        @Field("password") password: String): Call<LoginDataAnswer>

    @POST("api/login/")
    @FormUrlEncoded
    fun loginRequest(@Field("username") username: String,
                     @Field("password") password: String): Call<LoginDataAnswer>

    @GET("api/products/")
    fun getAllProducts(): Call<List<Product>>

    @GET("api/reviews/{product_id}")
    fun getReviewsByProductId(@Path("product_id") productId: Int): Call<List<Review>>

    @POST("api/reviews/{product_id}")
    @FormUrlEncoded
    fun postReview(@Header("Authorization") token: String,
                   @Path("product_id") productId: Int,
                   @Field("text") text: String,
                   @Field("rate") rate: Int): Call<ReviewAnswer>

    companion object RetrofitInstance {
        const val BASE_URL = "http://smktesting.herokuapp.com/"
        const val IMAGE_STORAGE_PATH = "static/"

        fun initialize(): ApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}