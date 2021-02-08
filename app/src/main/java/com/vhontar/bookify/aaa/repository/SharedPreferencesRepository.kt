package com.vhontar.bookify.aaa.repository

import android.content.Context
import androidx.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
@Singleton
class SharedPreferencesRepository @Inject constructor(context: Context) {

    private val preference = PreferenceManager.getDefaultSharedPreferences(context)

    fun getAccessToken(): String = preference.getString(KEY_ACCESS_TOKEN, "") ?: ""
    fun setAccessToken(accessToken: String) {
        preference
            .edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .apply()
    }

    fun isNetworkOnly(): Boolean = preference.getBoolean(KEY_NETWORK_ONLY, false)
    fun setNetworkOnly(isNetworkOnly: Boolean) {
        preference
            .edit()
            .putBoolean(KEY_NETWORK_ONLY, isNetworkOnly)
            .apply()
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "key_access_token"
        private const val KEY_NETWORK_ONLY = "key_network_only"
    }
}