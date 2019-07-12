package android.rezndm.test_lightit.auth.register

import android.rezndm.test_lightit.model.ApiService
import android.rezndm.test_lightit.model.LoginDataAnswer
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterPresenterImpl(private val registerView: RegisterView):RegisterPresenter {
    override fun requestRegister(username: String, password: String) {
        val apiService = ApiService.initialize()
        val loginAnswer = apiService.registerRequest(username, password)

        loginAnswer.enqueue(object: Callback<LoginDataAnswer> {
            override fun onFailure(call: Call<LoginDataAnswer>, t: Throwable) {

            }

            override fun onResponse(call: Call<LoginDataAnswer>, response: Response<LoginDataAnswer>) {
                val loginDataAnswer = response.body()
                val success = loginDataAnswer?.success

                if (success != null) {
                    registerView.handleRegistrationResult(success)
                    Log.d("QWEQWE", loginDataAnswer.token)
                }
            }

        })
    }
}