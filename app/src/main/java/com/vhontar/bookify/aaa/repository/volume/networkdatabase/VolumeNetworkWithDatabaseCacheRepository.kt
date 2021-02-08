package com.vhontar.bookify.aaa.repository.volume.networkdatabase

import androidx.paging.*
import com.vhontar.bookify.aaa.constants.NETWORK_PAGE_SIZE
import com.vhontar.bookify.aaa.db.*
import com.vhontar.bookify.aaa.domain.SearchRequest
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.aaa.network.BookifyService
import com.vhontar.bookify.aaa.repository.DefaultRepository
import com.vhontar.bookify.aaa.repository.SharedPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Vladyslav Hontar (vhontar) on 02.02.21.
 */
@ExperimentalPagingApi
@Singleton
class VolumeNetworkWithDatabaseCacheRepository @Inject constructor(
    private val bookifyService: BookifyService,
    private val bookifyDatabase: BookifyDatabase,
    private val preferences: SharedPreferencesRepository
) : DefaultRepository {

    private val searchRequestDao = bookifyDatabase.searchRequestDao()
    private val volumeDao = bookifyDatabase.volumeDao()

    suspend fun getVolumes(query: String): Flow<PagingData<Volume>> {

        var searchRequest = searchRequestDao.getSearchRequest(query)?.asDomainModel()
        if (searchRequest == null) {
            val searchRequestEntity = SearchRequestEntity(qSearch = query, booksCount = 0)
            searchRequestDao.insert(searchRequestEntity)
            searchRequest = searchRequestDao.getSearchRequest(query)!!.asDomainModel()
        }

        val pagingSourceFactory: () -> PagingSource<Int, VolumeEntity> = {
            bookifyDatabase
                .volumeDao()
                .getVolumesForSearchRequest(searchRequest.id)
        }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = VolumePageKeyedRemoteMediator(
                searchRequest,
                bookifyService,
                bookifyDatabase,
                preferences.getAccessToken()
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .map { pagingData -> pagingData.map { it.asDomainModel() } }
    }

    suspend fun getVolume(volumeId: String) = volumeDao.getById(volumeId).asDomainModel()
    suspend fun getLastSearchRequests(): List<SearchRequest> =
        searchRequestDao.getSearchRequests().asDomainModels()

    suspend fun getPopularVolumesForSearchRequest(searchRequest: SearchRequest) =
        volumeDao.getPopularVolumesForSearchRequest(searchRequest.id).asDomainModels()

    override fun isNetworkOnly() = preferences.isNetworkOnly()
}
