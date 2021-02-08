package com.vhontar.bookify.aaa.repository.volume.networkonly

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.vhontar.bookify.aaa.constants.NETWORK_PAGE_SIZE
import com.vhontar.bookify.aaa.db.BookifyDatabase
import com.vhontar.bookify.aaa.db.SearchRequestEntity
import com.vhontar.bookify.aaa.db.asDomainModel
import com.vhontar.bookify.aaa.domain.SearchRequest
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.aaa.network.BookifyService
import com.vhontar.bookify.aaa.network.asDomainModel
import com.vhontar.bookify.aaa.network.asDomainModels
import com.vhontar.bookify.aaa.network.resource.wrapEspressoIdlingResource
import com.vhontar.bookify.aaa.network.response.Response
import com.vhontar.bookify.aaa.repository.DefaultRepository
import com.vhontar.bookify.aaa.repository.SharedPreferencesRepository
import com.vhontar.bookify.aaa.utilities.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
@Singleton
class VolumeNetworkOnlyRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferences: SharedPreferencesRepository,
    private val bookifyService: BookifyService,
    private val bookifyDatabase: BookifyDatabase
): DefaultRepository {

    private val searchRequestDao = bookifyDatabase.searchRequestDao()
    private val volumeDao = bookifyDatabase.volumeDao()

    suspend fun getVolumes(query: String): Flow<PagingData<Volume>> {

        var searchRequest = searchRequestDao.getSearchRequest(query)?.asDomainModel()
        if (searchRequest == null) {
            val searchRequestEntity = SearchRequestEntity(qSearch = query, booksCount = 0,)
            searchRequestDao.insert(searchRequestEntity)
            searchRequest = searchRequestDao.getSearchRequest(query)!!.asDomainModel()
        }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                VolumePagingSource(
                    searchRequest,
                    preferences.getAccessToken(),
                    bookifyService,
                    bookifyDatabase
                )
            }
        ).flow
            .map { pagingData -> pagingData.map { it.asDomainModel() } }
    }

    suspend fun getPopularVolumes(query: String, popularCount: Int = 10): Response<List<Volume>> {
        // more reliable for testing with espresso
        wrapEspressoIdlingResource {
            val response: Response<List<Volume>> = Response()
            if (!NetworkUtils.isNetworkAvailable(context)) {
                response.setNetworkOff()
                return response
            }

            try {
                val volumeResponse = bookifyService.getVolumes(preferences.getAccessToken(), query, 0, popularCount)
                if (volumeResponse.isSuccessful) {
                    response.setSuccess()
                    response.data = volumeResponse.body()?.volumes?.asDomainModels()
                } else {
                    response.setResponseError()
                    response.errorMessage = volumeResponse.errorBody().toString()
                }
            } catch (e: SocketTimeoutException) {
                response.setServerDown()
            } catch (e: Throwable) {
                response.setServerError()
            }

            return response
        }
    }

    suspend fun getVolume(volumeId: String): Response<Volume> {
        // more reliable for testing with espresso
        wrapEspressoIdlingResource {
            val response: Response<Volume> = Response()
            if (!NetworkUtils.isNetworkAvailable(context)) {
                response.setNetworkOff()
                return response
            }

            try {
                val volumeResponse = bookifyService.getVolume(volumeId, preferences.getAccessToken())
                if (volumeResponse.isSuccessful) {
                    response.setSuccess()
                    response.data = volumeResponse.body()?.asDomainModel()
                } else {
                    response.setResponseError()
                    response.errorMessage = volumeResponse.errorBody().toString()
                }
            } catch (e: SocketTimeoutException) {
                response.setServerDown()
            } catch (e: Throwable) {
                response.setServerError()
            }

            return response
        }
    }

    suspend fun getLocalVolume(volumeId: String): Volume = volumeDao.getById(volumeId).asDomainModel()

    override fun isNetworkOnly() = preferences.isNetworkOnly()
}