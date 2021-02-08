package com.vhontar.bookify.aaa.repository.volume.networkonly

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vhontar.bookify.aaa.db.BookifyDatabase
import com.vhontar.bookify.aaa.db.asDatabaseModel
import com.vhontar.bookify.aaa.domain.SearchRequest
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.aaa.network.BookifyService
import com.vhontar.bookify.aaa.network.VolumeNetwork
import com.vhontar.bookify.aaa.network.asDomainModels
import com.vhontar.bookify.aaa.network.response.ResponseError
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
class VolumePagingSource(
    private val searchRequest: SearchRequest,
    private val accessToken: String,
    private val bookifyService: BookifyService,
    bookifyDatabase: BookifyDatabase
): PagingSource<Int, VolumeNetwork>() {

    private val searchRequestDao = bookifyDatabase.searchRequestDao()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VolumeNetwork> {
        val position = params.key ?: 0

        return try {
            val response = bookifyService.getVolumes(accessToken, searchRequest.qSearch, position)

            if (response.isSuccessful) {
                val volumes = response.body()?.volumes ?: arrayListOf()

                searchRequest.booksCount = response.body()?.totalItems ?: 0
                searchRequestDao.update(searchRequest.asDatabaseModel())

                val nextKey = if (volumes.isNullOrEmpty()) {
                    null
                } else {
                    position + volumes.size
                }

                LoadResult.Page(
                    data = volumes,
                    prevKey = if (position == 0) null else position - 1,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(ResponseError(response.message()))
            }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, VolumeNetwork>): Int? {
        return state.anchorPosition
    }
}