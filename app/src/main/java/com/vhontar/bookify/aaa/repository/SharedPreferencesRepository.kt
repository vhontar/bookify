package com.vhontar.bookify.aaa.repository

import android.content.Context
import androidx.preference.PreferenceManager
import javax.inject.Inject

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
class SharedPreferencesRepository @Inject constructor(context: Context) {

    private val preference = PreferenceManager.getDefaultSharedPreferences(context)

    fun getAccessToken() = preference.getString(KEY_ACCESS_TOKEN, "")
    fun setAccessToken(accessToken: String) {
        preference
            .edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .apply()
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "key_access_token"
    }
}