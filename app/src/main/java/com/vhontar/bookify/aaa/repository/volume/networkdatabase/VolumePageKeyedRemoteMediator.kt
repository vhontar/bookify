package com.vhontar.bookify.aaa.repository.volume.networkdatabase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.vhontar.bookify.aaa.constants.SAVED_SEARCH_REQUESTS_COUNT
import com.vhontar.bookify.aaa.db.SearchRequestDao
import com.vhontar.bookify.aaa.db.SearchRequestEntity
import com.vhontar.bookify.aaa.db.VolumeDao
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.aaa.network.BookifyService

/**
 * Created by Vladyslav Hontar (vhontar) on 02.02.21.
 */
//@ExperimentalPagingApi
//class VolumePageKeyedRemoteMediator(
//    private val query: String,
//    private val searchRequestDao: SearchRequestDao,
//    private val volumeDao: VolumeDao,
//    private val bookifyService: BookifyService
//): RemoteMediator<Int, Volume>() {
//
//    override suspend fun load(loadType: LoadType, state: PagingState<Int, Volume>): MediatorResult {
//        val searchRequests = searchRequestDao.getSearchRequests()
//        if (searchRequests.size == SAVED_SEARCH_REQUESTS_COUNT) {
//            searchRequestDao.delete(searchRequests[0])
//        }
//
//        var alreadyUsedSearchRequest: SearchRequestEntity? = null
//        searchRequests.forEach {
//            if (it.qSearch == query) {
//                alreadyUsedSearchRequest = it
//            }
//        }
//
//        if (alreadyUsedSearchRequest == null) {
//            val savedSearchRequest =
//                searchRequestDao.insert(SearchRequestEntity(qSearch = query, booksCount = 0))
//        } else {
//
//        }
//
//        val loadKey = when(loadType) {
//            LoadType.REFRESH -> null
//            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//            LoadType.APPEND -> {
//                // Query DB for BookifyDatabase for the volumes.
//            }
//        }
//    }
//}