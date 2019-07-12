package android.rezndm.test_lightit.auth.login

import android.rezndm.test_lightit.model.ApiService
import android.rezndm.test_lightit.model.LoginDataAnswer
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenterImpl(private val loginView: LoginView): LoginPresenter {
    override fun requestLogin(username: String, password: String) {
        val apiService: ApiService = ApiService.initialize()
        val loginAnswer = apiService.loginRequest(username, password)

        loginAnswer.enqueue(object: Callback<LoginDataAnswer>{
            override fun onFailure(call: Call<LoginDataAnswer>, t: Throwable) {
                Log.d(LoginFragment.LOG_TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<LoginDataAnswer>, response: Response<LoginDataAnswer>) {
                val loginAnswer = response.body()
                // TODO !!!
                val response: Boolean = loginAnswer!!.success
                loginView.logIn(response)
                Log.d("QWEQWE", loginAnswer.token)
            }
        })
    }
}