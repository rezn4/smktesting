package android.rezndm.test_lightit.products

import android.rezndm.test_lightit.model.ApiService
import android.rezndm.test_lightit.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsPresenterImpl(private val productsView: ProductsView): ProductsPresenter {
    override fun loadProducts() {
        val apiService = ApiService.initialize()
        val products = apiService.getAllProducts()

        products.enqueue(object: Callback<List<Product>>{
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                val products = response.body()

                if (products != null) {
                    productsView.showProducts(products)
                }
            }

        })
    }
}