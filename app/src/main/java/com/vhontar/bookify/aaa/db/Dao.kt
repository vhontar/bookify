package com.vhontar.bookify.aaa.db

import androidx.paging.PagingSource
import androidx.room.*
import com.vhontar.bookify.aaa.domain.Volume

/**
 * Created by Vladyslav Hontar (vhontar) on 01.02.21.
 */

@Dao
interface VolumeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg volumes: VolumeEntity)

    @Delete
    fun delete(volume: VolumeEntity)

    @Query("SELECT * FROM volumes")
    fun getAll(): List<VolumeEntity>

    @Query("SELECT * FROM volumes WHERE id = :volumeId")
    fun getById(volumeId: String): VolumeEntity

    @Query("SELECT * FROM volumes WHERE search_request_id = :searchRequestId ORDER BY ratings_count, average_rating desc LIMIT :popularCount")
    fun getPopularVolumesForSearchRequest(searchRequestId: Int, popularCount: Int = 10): List<SearchRequestEntity>

    @Query("SELECT * FROM volumes WHERE search_request_id = :searchRequestId")
    fun getVolumesForSearchRequest(searchRequestId: Int): PagingSource<Int, Volume>
}

@Dao
interface SearchRequestDao {
    @Insert
    fun insert(searchRequest: SearchRequestEntity)

    @Delete
    fun delete(searchRequest: SearchRequestEntity)

    @Transaction
    @Query("SELECT * FROM search_requests WHERE q_search = :qSearch LIMIT 1")
    fun getSearchRequest(qSearch: String): SearchRequestEntity

    @Transaction
    @Query("SELECT * FROM search_requests")
    fun getSearchRequestsWithVolumes(): List<SearchRequestEntity>

    @Transaction
    @Query("SELECT * FROM search_requests WHERE q_search = :qSearch")
    fun getSearchRequestWithVolumes(qSearch: String): List<SearchRequestEntity>
}

@Dao
interface LikedVolumeDao {
    @Insert(entity = LikedVolumeEntity::class)
    fun insert(volume: VolumeEntity)

    @Delete(entity = LikedVolumeEntity::class)
    fun delete(volume: VolumeEntity)

    @Query("SELECT * FROM liked_volumes")
    fun getLikedVolumes(): List<VolumeEntity>
}