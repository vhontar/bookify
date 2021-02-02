package com.vhontar.bookify.aaa.repository.volume.networkdatabase

import androidx.paging.PagingData
import com.vhontar.bookify.aaa.db.SearchRequestDao
import com.vhontar.bookify.aaa.db.VolumeDao
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.aaa.network.BookifyService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Vladyslav Hontar (vhontar) on 02.02.21.
 */
//@Singleton
//class VolumeNetworkWithCacheRepository @Inject constructor(
//    private val searchRequestDao: SearchRequestDao,
//    private val volumeDao: VolumeDao,
//    private val bookifyService: BookifyService
//) {
//    fun getVolumes(query: String): Flow<PagingData<Volume>> {
//
//    }
//}
