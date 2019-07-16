package android.rezndm.test_lightit.products

import android.app.AlertDialog
import android.app.ProgressDialog
import android.app.ProgressDialog.show
import android.os.Bundle
import android.rezndm.test_lightit.R
import android.rezndm.test_lightit.model.Const
import android.rezndm.test_lightit.model.Product
import android.rezndm.test_lightit.reviews.ReviewFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_products_list.*

class ProductsFragment : Fragment(), ProductsView {
    private val productsAdapter = ProductsAdapter()
    private lateinit var productsPresenter: ProductsPresenter
    private lateinit var dialog: AlertDialog

    override fun showProducts(products: List<Product>) {
        productsAdapter.products.addAll(products)
        productsAdapter.notifyDataSetChanged()
        dialog.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productsPresenter = ProductsPresenterImpl(this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }

    private fun blockUi() {
        dialog = SpotsDialog.Builder()
            .setContext(activity)
            .setMessage(getString(R.string.progress_bar_loading))
            .setCancelable(false)
            .build()
            .apply {
                show()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        products_recycler_view.layoutManager = layoutManager
        products_recycler_view.adapter = productsAdapter

        if (productsAdapter.products.isEmpty()) {
            productsPresenter.loadProducts()
            blockUi()
        }

        productsAdapter.setOnClickListener(object : ProductsAdapter.ClickListener {
            override fun onItemClick(id: Int) {
                val tx = activity?.supportFragmentManager?.beginTransaction()
                val reviewFragment = ReviewFragment()
                val args = Bundle()
                args.putInt(Const.BUNDLE_PRODUCT_ID_KEY, id)
                reviewFragment.arguments = args
                tx?.replace(R.id.container, reviewFragment)?.addToBackStack(null)?.commit()
            }
        })
    }
}