package android.rezndm.test_lightit.model

import android.content.SharedPreferences

interface TokenRepository {
    fun getToken():String
    fun saveToken(token: String?)
}

class TokenRepositorySharedPrefs (private val preferences: SharedPreferences): TokenRepository {

    companion object {
        const val PREFS_TOKEN_KEY = "PREFS_TOKEN_KEY"
        const val TOKEN_DEFAULT_VALUE = "TOKEN_UNAVAILABLE"
    }

    override fun getToken(): String {
        return preferences.getString(PREFS_TOKEN_KEY, TOKEN_DEFAULT_VALUE)
    }

    override fun saveToken(token: String?) {
        val editor = preferences.edit()
        editor.putString(PREFS_TOKEN_KEY, token)
        editor.apply()
    }
}