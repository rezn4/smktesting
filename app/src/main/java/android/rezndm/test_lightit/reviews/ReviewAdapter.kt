package android.rezndm.test_lightit.reviews

import android.rezndm.test_lightit.R
import android.rezndm.test_lightit.model.Review
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewAdapter: RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    val reviews: MutableList<Review> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_review,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.username.text = review.user.username
        holder.comment.text = review.text
        holder.date.text = review.createDate
        holder.ratingBar.rating = review.rate.toFloat()
    }


    class ReviewViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val username: TextView = item.review_username
        val comment: TextView = item.review_comment
        val date: TextView = item.review_date
        val ratingBar: RatingBar = item.review_rating
    }
}