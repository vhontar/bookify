package com.vhontar.bookify.aaa.repository

import com.vhontar.bookify.aaa.network.BookifyService
import javax.inject.Inject

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
class VolumeRepository @Inject constructor(
    private val repository: SharedPreferencesRepository,
    private val service: BookifyService
) {

    suspend fun getVolumes() {
    }
}