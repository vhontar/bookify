package com.vhontar.bookify.aaa.repository.volume.networkonly

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vhontar.bookify.aaa.constants.NETWORK_PAGE_SIZE
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.aaa.network.BookifyService
import com.vhontar.bookify.aaa.network.asDomainModel
import com.vhontar.bookify.aaa.network.resource.wrapEspressoIdlingResource
import com.vhontar.bookify.aaa.network.response.Response
import com.vhontar.bookify.aaa.repository.SharedPreferencesRepository
import com.vhontar.bookify.aaa.utilities.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
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
    private val service: BookifyService
) {

    fun getVolumes(query: String): Flow<PagingData<Volume>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                VolumePagingSource(
                    service,
                    preferences.getAccessToken(),
                    query
                )
            }
        ).flow
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
                val volumeResponse = service.getVolume(volumeId, preferences.getAccessToken())
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
}