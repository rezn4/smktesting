package android.rezndm.test_lightit.reviews

import android.os.Bundle
import android.rezndm.test_lightit.R
import android.rezndm.test_lightit.model.Review
import android.rezndm.test_lightit.products.ProductsFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_reviews.*

class ReviewFragment: Fragment(), ReviewView {

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

        val productId = arguments?.getInt(ProductsFragment.BUNDLE_PRODUCT_ID)
        val reviewPresenter :ReviewPresenter = ReviewPresenterImpl(this)
        productId?.let { id -> reviewPresenter.loadReviews(id) }

    }
}