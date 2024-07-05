package com.ar.sample.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

data class CredentialData(
    val username: String,
    val password: String,
    val clientId: String
)

class CredentialManager(context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val credentialPrefs = EncryptedSharedPreferences.create(
        "credential_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveCredentials(credentialData: CredentialData) {
        val preferences = credentialPrefs.edit();
        preferences.putString("username", credentialData.username)
        preferences.putString("password", credentialData.password)
        preferences.putString("clientId", credentialData.clientId)
        preferences.apply()
    }

    fun getCredentialData(): CredentialData? {
        val username = credentialPrefs.getString("username", null)
        val password = credentialPrefs.getString("password", null)
        val clientId = credentialPrefs.getString("clientId", null)

        if (username != null && password != null && clientId != null) {
            return CredentialData(username, password, clientId)
        }

        return null
    }
}