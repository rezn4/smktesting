package android.rezndm.test_lightit.products

import android.rezndm.test_lightit.model.Product

interface ProductsView {
    fun showProducts(products: List<Product>)
}