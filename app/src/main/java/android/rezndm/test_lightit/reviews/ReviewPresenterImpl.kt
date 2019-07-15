package android.rezndm.test_lightit.reviews

import android.rezndm.test_lightit.model.ApiService
import android.rezndm.test_lightit.model.Review
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewPresenterImpl(private val reviewView: ReviewView): ReviewPresenter {
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