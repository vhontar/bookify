package com.vhontar.bookify.aaa.pagingsource

import androidx.paging.PagingSource
import com.vhontar.bookify.aaa.network.BookifyService
import com.vhontar.bookify.aaa.network.NetworkVolume
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
class VolumePagingSource(
    private val service: BookifyService,
    private val accessToken: String,
    private val query: String
): PagingSource<Int, NetworkVolume>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NetworkVolume> {
        val position = params.key ?: 0

        return try {
            val response = service.getVolumes(accessToken, query, position)
            val volumes = response.body()?.volumes ?: arrayListOf()

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
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}