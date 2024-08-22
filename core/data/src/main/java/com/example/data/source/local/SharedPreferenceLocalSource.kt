package com.example.data.source.local

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Email
import javax.inject.Inject

class SharedPreferenceLocalSource @Inject constructor(
    private val context: Context
) {

    private val deviceTokenStorage = "DEVICE_TOKEN_STORAGE"

    private val deviceToken = "DEVICE_TOKEN"
    private val bindDeviceTokenStatus = "BIND_DEVICE_TOKEN_STATUS"

    private val authStorage = "AUTH_STORAGE"
    private val emailAuth = "EMAIL"

    fun getEmail(): String? = context.getSharedPreferences(authStorage, Context.MODE_PRIVATE).getString(emailAuth, null)

    fun setEmail(email: String) {
        context.getSharedPreferences(authStorage, Context.MODE_PRIVATE).edit().putString(emailAuth, email).apply()
    }

    fun setDeviceToken(token: String) {
        context.getSharedPreferences(deviceTokenStorage, Context.MODE_PRIVATE).edit().putString(deviceToken, token).apply()
    }

    fun getDeviceToken(): String? = context.getSharedPreferences(deviceTokenStorage, Context.MODE_PRIVATE).getString(deviceToken, null)

    fun setBindDeviceTokenStatus(status: Boolean) {
        context.getSharedPreferences(deviceTokenStorage, Context.MODE_PRIVATE).edit().putBoolean(bindDeviceTokenStatus, status).apply()
    }

    fun getBindDeviceTokenStatus(): Boolean = context.getSharedPreferences(deviceTokenStorage, Context.MODE_PRIVATE).getBoolean(bindDeviceTokenStatus, false)

}