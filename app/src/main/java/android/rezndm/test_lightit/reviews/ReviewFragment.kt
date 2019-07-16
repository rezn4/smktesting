package android.rezndm.test_lightit.reviews

import android.os.Bundle
import android.preference.PreferenceManager
import android.rezndm.test_lightit.R
import android.rezndm.test_lightit.model.Const
import android.rezndm.test_lightit.model.Repository
import android.rezndm.test_lightit.model.Review
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_reviews.*

class ReviewFragment: Fragment(), ReviewView, RatingBar.OnRatingBarChangeListener {

    private var productId: Int = 0
    private lateinit var reviewPresenter: ReviewPresenter

    override fun updateReviews() {
        reviewAdapter.reviews.clear()
        reviewPresenter.loadReviews(productId)
        Toast.makeText(activity, getString(R.string.comment_added), Toast.LENGTH_SHORT).show()
    }

    private val reviewAdapter = ReviewAdapter()

    override fun showReviews(reviews: List<Review>) {
        reviewAdapter.reviews.addAll(reviews)
        reviewAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        reviews_recycler_view.layoutManager = layoutManager
        reviews_recycler_view.adapter = reviewAdapter
        review_rating.onRatingBarChangeListener = this

        productId = arguments?.getInt(Const.BUNDLE_PRODUCT_ID_KEY) ?: 0
        reviewPresenter = ReviewPresenterImpl(this)
        reviewPresenter.loadReviews(productId)

        val repository = Repository(PreferenceManager.getDefaultSharedPreferences(activity))
        val token = repository.getToken()

        if (token != Repository.TOKEN_DEFAULT_VALUE){
            review_post.setOnClickListener {
                val text = review_edt.text.toString()
                val rate = review_rating.rating.toInt()

                if (text.isNotEmpty()){
                    reviewPresenter.postReview(text, rate, "Token $token", productId)
                } else {
                    Toast.makeText(activity, getString(R.string.review_empty_warning), Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            review_post_a_comment.visibility = View.GONE
        }
    }

    override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {

    }
}