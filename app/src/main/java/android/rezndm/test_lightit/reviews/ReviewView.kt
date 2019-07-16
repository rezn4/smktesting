package android.rezndm.test_lightit.reviews

import android.rezndm.test_lightit.model.Review

interface ReviewView {
    fun showReviews(reviews: List<Review>)
    fun updateReviews()
}