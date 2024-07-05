package com.ar.sample.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.inject.Inject

class TokenManager @Inject constructor(context: Context) {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val tokenPrefs = EncryptedSharedPreferences.create(
        "token_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveAccessToken(token: String) {
        tokenPrefs.edit().putString("access_token", token).apply()
    }

    fun getAccessToken(): String? {
        return tokenPrefs.getString("access_token", null)
    }

    fun clearAccessToken() {
        tokenPrefs.edit().remove("access_token").apply()
    }
}