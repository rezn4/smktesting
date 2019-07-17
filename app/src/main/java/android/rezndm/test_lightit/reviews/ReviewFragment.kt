package android.rezndm.test_lightit.reviews

import android.app.AlertDialog
import android.os.Bundle
import android.preference.PreferenceManager
import android.rezndm.test_lightit.R
import android.rezndm.test_lightit.model.Const
import android.rezndm.test_lightit.model.TokenRepositorySharedPrefs
import android.rezndm.test_lightit.model.Review
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_reviews.*

class ReviewFragment: Fragment(), ReviewView, RatingBar.OnRatingBarChangeListener {
    private var productId: Int = 0
    private lateinit var reviewPresenter: ReviewPresenter
    private lateinit var dialog: AlertDialog

    override fun updateReviews(success: Boolean){
        if (success) {
            reviewAdapter.reviews.clear()
            reviewPresenter.loadReviews(productId)
            review_edt.text.clear()
            review_rating.rating = 0F
            Toast.makeText(activity, getString(R.string.comment_added), Toast.LENGTH_SHORT).show()
            unblockUi()
        } else {
            Toast.makeText(activity, getString(R.string.review_token_expired), Toast.LENGTH_SHORT).show()
            unblockUi()
        }
    }

    private val reviewAdapter = ReviewAdapter()

    override fun showReviews(reviews: List<Review>){
        reviewAdapter.reviews.addAll(reviews)
        reviewAdapter.notifyDataSetChanged()
        unblockUi()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productId = arguments?.getInt(Const.BUNDLE_PRODUCT_ID_KEY) ?: 0
        reviewPresenter = ReviewPresenterImpl(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    private fun blockUi(){
        dialog = SpotsDialog.Builder()
            .setContext(activity)
            .setMessage(getString(R.string.progress_bar_loading))
            .setCancelable(false)
            .build()
            .apply {
                show()
            }

        review_post.isEnabled = false
    }

    private fun unblockUi(){
        dialog.dismiss()
        review_post.isEnabled = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        blockUi()
        reviewPresenter.loadReviews(productId)

        val layoutManager = LinearLayoutManager(context)
        reviews_recycler_view.layoutManager = layoutManager
        reviews_recycler_view.adapter = reviewAdapter
        review_rating.onRatingBarChangeListener = this


        val repository = TokenRepositorySharedPrefs(PreferenceManager.getDefaultSharedPreferences(activity))
        val token = repository.getToken()

        if (token != TokenRepositorySharedPrefs.TOKEN_DEFAULT_VALUE){
            review_post.setOnClickListener {
                val text = review_edt.text.toString()
                val rate = review_rating.rating.toInt()

                if (text.isNotEmpty()){
                    if (rate > 0){
                        blockUi()
                        reviewPresenter.postReview(text, rate, "Token $token", productId)
                    } else {
                        Toast.makeText(activity, getString(R.string.review_rating_warning), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(activity, getString(R.string.review_empty_warning), Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            review_comment_layout.visibility = View.GONE
        }
    }

    override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {

    }
}