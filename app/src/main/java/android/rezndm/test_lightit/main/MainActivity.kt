package android.rezndm.test_lightit.main

import android.os.Bundle
import android.preference.PreferenceManager
import android.rezndm.test_lightit.R
import android.rezndm.test_lightit.auth.login.LoginFragment
import android.rezndm.test_lightit.model.TokenRepository
import android.rezndm.test_lightit.model.TokenRepositorySharedPrefs
import android.rezndm.test_lightit.products.ProductsFragment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {
    private lateinit var tokenRepository: TokenRepository
    private val fragmentTransaction = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        tokenRepository = TokenRepositorySharedPrefs(PreferenceManager.getDefaultSharedPreferences(this))

        if (isUserLoggedIn()){
            fragmentTransaction.replace(R.id.container, ProductsFragment()).commit()
        } else {
            fragmentTransaction.replace(R.id.container, LoginFragment()).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.navigation_activity_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_logout){
            if (isUserLoggedIn()){
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.container, LoginFragment()).commit()
                tokenRepository.saveToken(TokenRepositorySharedPrefs.TOKEN_DEFAULT_VALUE)
            } else {
                Toast.makeText(this, getString(R.string.logout_warning), Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun isUserLoggedIn(): Boolean {
        val token = tokenRepository.getToken()
        return token != TokenRepositorySharedPrefs.TOKEN_DEFAULT_VALUE
    }
}
