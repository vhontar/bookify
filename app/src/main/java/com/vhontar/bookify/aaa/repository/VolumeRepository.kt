package com.vhontar.bookify.aaa.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vhontar.bookify.aaa.constants.NETWORK_PAGE_SIZE
import com.vhontar.bookify.aaa.network.BookifyService
import com.vhontar.bookify.aaa.network.NetworkVolume
import com.vhontar.bookify.aaa.pagingsource.VolumePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
@Singleton
class VolumeRepository @Inject constructor(
    private val preferences: SharedPreferencesRepository,
    private val service: BookifyService
) {

    fun getVolumes(query: String): Flow<PagingData<NetworkVolume>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { VolumePagingSource(service, preferences.getAccessToken(), query) }
        ).flow
    }
}