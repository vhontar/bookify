package com.vhontar.bookify.aaa.repository.volume.networkdatabase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vhontar.bookify.aaa.constants.NETWORK_PAGE_SIZE
import com.vhontar.bookify.aaa.constants.SAVED_SEARCH_REQUESTS_COUNT
import com.vhontar.bookify.aaa.db.*
import com.vhontar.bookify.aaa.domain.RemoteKey
import com.vhontar.bookify.aaa.domain.SearchRequest
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.aaa.network.BookifyService
import com.vhontar.bookify.aaa.network.asDomainModels
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

/**
 * Created by Vladyslav Hontar (vhontar) on 02.02.21.
 */
@ExperimentalPagingApi
class VolumePageKeyedRemoteMediator(
    private val searchRequest: SearchRequest,
    private val bookifyService: BookifyService,
    private val bookifyDatabase: BookifyDatabase,
    private val accessToken: String
): RemoteMediator<Int, VolumeEntity>() {

    private val volumeDao = bookifyDatabase.volumeDao()
    private val remoteKeyDao = bookifyDatabase.remoteKeyDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, VolumeEntity>): MediatorResult {
        val loadKey: Int = when(loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 0
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?:
                    // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote keys
                    // If the remoteKeys are null, then we're an invalid state and we have a bug
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                // If the previous key is null, then we can't request more data
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey ?: 0
            }
        }

        try {
            val apiResponse = bookifyService.getVolumes(accessToken, searchRequest.qSearch, loadKey)

            val domainVolumes = apiResponse.body()?.volumes?.asDomainModels() ?: arrayListOf()
            val endOfPaginationReached = domainVolumes.isEmpty()
            bookifyDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearRemoteKeysForSearchRequest(searchRequest.id)
                    volumeDao.clearVolumesForSearchRequest(searchRequest.id)
                }
                val prevKey = if (loadKey == NETWORK_PAGE_SIZE) null else loadKey - 1
                val nextKey = if (endOfPaginationReached) null else loadKey + 1
                val keys = domainVolumes.map {
                    it.searchRequestId = searchRequest.id
                    RemoteKey(it.id, it.searchRequestId, prevKey, nextKey)
                }
                remoteKeyDao.insertAll(keys.asDatabaseModels())
                volumeDao.insertAll(domainVolumes.asDatabaseModels())
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, VolumeEntity>): RemoteKey? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { item ->
                // Get the remote keys of the last item retrieved
                remoteKeyDao.findRemoteKey(item.volumeId, searchRequest.id)?.asDomainModel()
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, VolumeEntity>): RemoteKey? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { item ->
                // Get the remote keys of the first items retrieved
                remoteKeyDao.findRemoteKey(item.volumeId, searchRequest.id)?.asDomainModel()
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, VolumeEntity>): RemoteKey? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.volumeId?.let { volumeId ->
                remoteKeyDao.findRemoteKey(volumeId, searchRequest.id)?.asDomainModel()
            }
        }
    }
}