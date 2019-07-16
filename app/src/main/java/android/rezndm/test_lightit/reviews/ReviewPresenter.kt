package android.rezndm.test_lightit.reviews

interface ReviewPresenter {
    fun loadReviews(id: Int)
    fun postReview(text: String, rate: Int, token: String, productId: Int)
}