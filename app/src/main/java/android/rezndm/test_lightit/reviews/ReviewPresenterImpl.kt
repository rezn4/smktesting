package android.rezndm.test_lightit.reviews

import android.rezndm.test_lightit.model.ApiService
import android.rezndm.test_lightit.model.LoginDataAnswer
import android.rezndm.test_lightit.model.Review
import android.rezndm.test_lightit.model.ReviewAnswer
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewPresenterImpl(private val reviewView: ReviewView): ReviewPresenter {
    override fun postReview(text: String, rate: Int, token: String, productId: Int) {
        val apiService = ApiService.initialize()
        val request = apiService.postReview(token, productId, text, rate)

        request.enqueue(object: Callback<ReviewAnswer> {
            override fun onFailure(call: Call<ReviewAnswer>, t: Throwable) {

            }

            override fun onResponse(call: Call<ReviewAnswer>, response: Response<ReviewAnswer>) {
                val reviewAnswer = response.body()
                val success: Boolean? = reviewAnswer?.success

                if (success!!){
                    reviewView.updateReviews()
                }
            }
        })
    }

    override fun loadReviews(id: Int) {
        val apiService = ApiService.initialize()
        val request = apiService.getReviewsByProductId(id)

        request.enqueue(object: Callback<List<Review>>{
            override fun onFailure(call: Call<List<Review>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Review>>, response: Response<List<Review>>) {
                val reviews = response.body()

                if (reviews != null) {
                    reviewView.showReviews(reviews)
                }
            }
        })
    }
}