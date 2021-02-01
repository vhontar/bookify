package com.vhontar.bookify.aaa.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vhontar.bookify.aaa.constants.NETWORK_PAGE_SIZE
import com.vhontar.bookify.aaa.network.BookifyService
import com.vhontar.bookify.aaa.network.VolumeNetwork
import com.vhontar.bookify.aaa.network.resource.wrapEspressoIdlingResource
import com.vhontar.bookify.aaa.network.response.Response
import com.vhontar.bookify.aaa.pagingsource.VolumeNetworkPagingSource
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
class VolumeRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferences: SharedPreferencesRepository,
    private val service: BookifyService
) {

    fun getVolumes(query: String): Flow<PagingData<VolumeNetwork>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { VolumeNetworkPagingSource(service, preferences.getAccessToken(), query) }
        ).flow
    }

    suspend fun getVolume(volumeId: String): Response<VolumeNetwork> {
        // more reliable for testing with espresso
        wrapEspressoIdlingResource {
            val response: Response<VolumeNetwork> = Response()
            if (!NetworkUtils.isNetworkAvailable(context)) {
                response.setNetworkOff()
                return response
            }

            try {
                val volumeResponse = service.getVolume(volumeId, preferences.getAccessToken())
                if (volumeResponse.isSuccessful) {
                    response.setSuccess()
                    response.data = volumeResponse.body()
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