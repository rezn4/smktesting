package android.rezndm.test_lightit.navigation

import android.os.Bundle
import android.preference.PreferenceManager
import android.rezndm.test_lightit.R
import android.rezndm.test_lightit.auth.login.LoginFragment
import android.rezndm.test_lightit.model.Repository
import android.rezndm.test_lightit.products.ProductsFragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val repository = Repository(PreferenceManager.getDefaultSharedPreferences(this))
        val token = repository.getToken()
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (token == Repository.TOKEN_DEFAULT_VALUE){
            fragmentTransaction.replace(R.id.container, LoginFragment()).commit()
        } else {
            fragmentTransaction.replace(R.id.container, ProductsFragment()).commit()
        }
    }
}
