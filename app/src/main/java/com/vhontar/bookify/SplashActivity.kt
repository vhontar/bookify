package com.vhontar.bookify

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vhontar.bookify.aaa.viewmodels.volume.networkonly.VolumeNetworkOnlyViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
@AndroidEntryPoint
class SplashActivity: AppCompatActivity() {
    private val viewModel: VolumeNetworkOnlyViewModel by viewModels()
}