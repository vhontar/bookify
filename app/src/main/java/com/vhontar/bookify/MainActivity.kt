package com.vhontar.bookify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vhontar.bookify.aaa.repository.SharedPreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var preference: SharedPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preference.setAccessToken(getString(R.string.book_api_token))
    }
}