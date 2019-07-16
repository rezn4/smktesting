package android.rezndm.test_lightit.auth.login

import android.rezndm.test_lightit.model.LoginDataAnswer

interface LoginView {
    fun logIn(loginDataAnswer: LoginDataAnswer)
}