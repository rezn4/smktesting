package android.rezndm.test_lightit.auth.register

interface RegisterView {
    fun handleRegistrationResult(success: Boolean, username: String, password: String)
}