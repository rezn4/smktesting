package android.rezndm.test_lightit.products

import android.os.Bundle
import android.rezndm.test_lightit.R
import android.rezndm.test_lightit.auth.register.RegisterFragment
import android.rezndm.test_lightit.model.Product
import android.rezndm.test_lightit.reviews.ReviewFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_products_list.*

class ProductsFragment : Fragment(), ProductsView {

    companion object {
        const val BUNDLE_PRODUCT_ID = "BUNDLE_PRODUCT_ID"
    }

    private val productsAdapter = ProductsAdapter()

    override fun showProducts(products: List<Product>) {
        productsAdapter.products.addAll(products)
        productsAdapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        productsAdapter.products.clear()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        products_recycler_view.layoutManager = layoutManager
        products_recycler_view.adapter = productsAdapter

        val productsPresenter :ProductsPresenter = ProductsPresenterImpl(this)
        productsPresenter.loadProducts()

        productsAdapter.setOnClickListener(object: ProductsAdapter.ClickListener {
            override fun onItemClick(id: Int) {
                val tx = activity?.supportFragmentManager?.beginTransaction()
                val reviewFragment = ReviewFragment()
                val args = Bundle()
                args.putInt(BUNDLE_PRODUCT_ID, id)
                reviewFragment.arguments = args
                tx?.replace(R.id.container, reviewFragment)?.addToBackStack(null)?.commit()
            }
        })
    }
}