package com.vhontar.bookify.aaa.db

import androidx.paging.PagingSource
import androidx.room.*
import com.vhontar.bookify.aaa.domain.RemoteKey

/**
 * Created by Vladyslav Hontar (vhontar) on 01.02.21.
 */

@Dao
interface VolumeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(volumes: List<VolumeEntity>)

    @Delete
    suspend fun delete(volume: VolumeEntity)

    @Query("SELECT * FROM volumes")
    suspend fun getAll(): List<VolumeEntity>

    @Query("SELECT * FROM volumes WHERE id = :volumeId")
    suspend fun getById(volumeId: String): VolumeEntity

    @Query("SELECT * FROM volumes WHERE search_request_id = :searchRequestId ORDER BY ratings_count, average_rating desc LIMIT :popularCount")
    suspend fun getPopularVolumesForSearchRequest(searchRequestId: Long, popularCount: Int = 10): List<VolumeEntity>

    @Query("SELECT * FROM volumes WHERE search_request_id = :searchRequestId")
    fun getVolumesForSearchRequest(searchRequestId: Long): PagingSource<Int, VolumeEntity>

    @Query("DELETE FROM volumes WHERE search_request_id = :searchRequestId")
    suspend fun clearVolumesForSearchRequest(searchRequestId: Long)
}

@Dao
interface SearchRequestDao {
    @Insert
    suspend fun insert(searchRequest: SearchRequestEntity)

    @Delete
    suspend fun delete(searchRequest: SearchRequestEntity)

    @Update
    suspend fun update(searchRequest: SearchRequestEntity)

    @Query("SELECT * FROM search_requests")
    suspend fun getSearchRequests(): List<SearchRequestEntity>

    @Transaction
    @Query("SELECT * FROM search_requests WHERE q_search = :qSearch LIMIT 1")
    suspend fun getSearchRequest(qSearch: String): SearchRequestEntity?

    @Transaction
    @Query("SELECT * FROM search_requests")
    suspend fun getSearchRequestsWithVolumes(): List<SearchRequestEntity>

    @Transaction
    @Query("SELECT * FROM search_requests WHERE q_search = :qSearch")
    suspend fun getSearchRequestWithVolumes(qSearch: String): List<SearchRequestEntity>
}

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKeys: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_keys WHERE volume_id = :volumeId AND search_request_id = :searchRequestId")
    fun findRemoteKey(volumeId: String, searchRequestId: Long): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys WHERE search_request_id = :searchRequestId")
    fun clearRemoteKeysForSearchRequest(searchRequestId: Long)
}