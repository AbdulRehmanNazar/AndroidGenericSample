package com.ar.sample.recievers

import android.content.Context
import android.content.Intent
import android.util.Log
import com.ar.sample.data.repository.MainRepository
import com.ar.sample.security.CredentialData
import com.ar.sample.security.CredentialManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class ADBCommandReceiver : HiltBroadcastReceiver() {
    private val TAG = "ADBCommandReceiver"
    private lateinit var broadcastReceiverContext: Context

    @Inject
    lateinit var mainRepository: MainRepository


    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        broadcastReceiverContext = context
        val action = intent?.action
        Log.d(TAG, "Received intent with action: $action")
        when (action) {
            "io.lorikeet.ENCRYPT_AND_STORE" -> {
                val username = intent.getStringExtra("username")
                val password = intent.getStringExtra("password")
                val clientId = intent.getStringExtra("clientId")
                val result = encryptAndStoreData(username, password, clientId)
                Log.i(TAG, "ENCRYPT_AND_STORE: $result")
            }

            "io.lorikeet.AUTH_TEST" -> {
                authTest()
            }
        }
    }

    private fun encryptAndStoreData(
        username: String?,
        password: String?,
        clientId: String?
    ): Boolean {
        if (username != null && password != null && clientId != null) {
            val credentialData = CredentialData(username, password, clientId)
            val crdentialManager = CredentialManager(broadcastReceiverContext)
            crdentialManager.saveCredentials(credentialData)
            return true;
        }
        return false
    }

    private fun authTest() {
        runBlocking {
            //Call your api
        }
    }
}