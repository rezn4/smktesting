package android.rezndm.test_lightit.auth.login

import android.os.Bundle
import android.rezndm.test_lightit.R
import android.rezndm.test_lightit.auth.register.RegisterFragment
import android.rezndm.test_lightit.model.ApiService
import android.rezndm.test_lightit.model.Product
import android.rezndm.test_lightit.model.Review
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment: Fragment(), LoginView {

    companion object {
        val LOG_TAG: String = LoginFragment::class.java.name
    }

    private val loginPresenter: LoginPresenter = LoginPresenterImpl(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginButton()
        setupSignUpButton()
    }

    private fun setupLoginButton(){
        button_login.setOnClickListener {
            val username: String = username_edt.text.toString()
            val password: String = password_edt.text.toString()

            if (username.isNotEmpty()){
                if (password.isNotEmpty()){
                    loginPresenter.requestLogin(username, password)
                } else {
                    makeToast(getString(R.string.warning_password_empty))
                }
            } else if (username.isEmpty() && password.isEmpty()){
                makeToast(getString(R.string.warning_data_empty))
            } else {
                makeToast(getString(R.string.warning_username_empty))
            }
        }
    }

    private fun setupSignUpButton(){
        btn_signup.setOnClickListener {
            val tx = activity?.supportFragmentManager?.beginTransaction()
            tx?.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.enter, R.anim.exit)
            tx?.replace(R.id.container, RegisterFragment())?.addToBackStack(null)?.commit()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_login, container, false)


        val apiService = ApiService.initialize()

        val qwe = apiService.getAllProducts()

        qwe.enqueue(object: Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                Log.d(LOG_TAG, response.body().toString())
            }

        })

        val ewq = apiService.getReviews()

        ewq.enqueue(object: Callback<List<Review>>{
            override fun onFailure(call: Call<List<Review>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<List<Review>>, response: Response<List<Review>>) {
                Log.d(LOG_TAG, response.body().toString())
            }

        })

        return layout
    }

    override fun logIn(success: Boolean) {
        if (success){
            makeToast(getString(R.string.result_success_login))
        } else {
            makeToast(getString(R.string.result_not_success_login))
        }
    }

    fun makeToast(text: String){
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }


}