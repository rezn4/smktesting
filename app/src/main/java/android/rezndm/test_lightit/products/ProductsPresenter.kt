package android.rezndm.test_lightit.products

import android.rezndm.test_lightit.model.Product

interface ProductsPresenter {
    fun getProducts():List<Product>
}