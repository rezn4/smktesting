package android.rezndm.test_lightit.products

import android.rezndm.test_lightit.R
import android.rezndm.test_lightit.model.ApiService
import android.rezndm.test_lightit.model.Product
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product.view.*

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    val products: MutableList<Product> = mutableListOf()
    lateinit var clickListener: ClickListener

    interface ClickListener {
        fun onItemClick(id: Int)
    }

    fun setOnClickListener(clickListener: ClickListener){
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val id = products[position].id
        val description = products[position].description
        val image = products[position].image
        val imageUrl = ApiService.BASE_URL + ApiService.IMAGE_STORAGE_PATH + image
        holder.description.text = description
        Glide.with(holder.description.context).load(imageUrl).into(holder.image)

        holder.layout.setOnClickListener {
            clickListener.onItemClick(id)
        }
    }


    class ProductViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val layout: ConstraintLayout = v.product_layout
        val image: ImageView = v.product_image
        val description: TextView = v.product_description
    }
}