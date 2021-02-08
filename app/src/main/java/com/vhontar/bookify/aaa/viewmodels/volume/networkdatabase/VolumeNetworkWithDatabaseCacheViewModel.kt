package com.vhontar.bookify.aaa.viewmodels.volume.networkdatabase

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.vhontar.bookify.aaa.domain.SearchRequest
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.aaa.repository.volume.networkdatabase.VolumeNetworkWithDatabaseCacheRepository
import com.vhontar.bookify.aaa.viewmodels.DefaultViewModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Vladyslav Hontar (vhontar) on 08.02.21.
 */
@ExperimentalPagingApi
class VolumeNetworkWithDatabaseCacheViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val networkWithDatabaseCacheRepository: VolumeNetworkWithDatabaseCacheRepository
) : ViewModel(), DefaultViewModel {

    private val _isServerDown = MutableLiveData<Boolean>()
    override val isServerDown: LiveData<Boolean> = _isServerDown

    private val _isNetworkOff = MutableLiveData<Boolean>()
    override val isNetworkOff: LiveData<Boolean> = _isNetworkOff

    private val _dataLoading = MutableLiveData<Boolean>()
    override val dataLoading: LiveData<Boolean> = _dataLoading

    private val _snackbarText = MutableLiveData<Int>()
    override val snackbarText: LiveData<Int> = _snackbarText

    private val _volume = MutableLiveData<Volume>()
    val volume: LiveData<Volume> = _volume

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Volume>>? = null

    init {
        savedStateHandle
    }

    suspend fun getVolumes(query: String): Flow<PagingData<Volume>> {
        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null)
            return lastResult

        currentQueryValue = query
        val newResult: Flow<PagingData<Volume>> = networkWithDatabaseCacheRepository.getVolumes(query)
        currentSearchResult = newResult
        return newResult
    }

    suspend fun getVolume(volumeId: String) {
        _volume.value = networkWithDatabaseCacheRepository.getVolume(volumeId)
    }

    suspend fun getLastSearchRequests() = networkWithDatabaseCacheRepository.getLastSearchRequests()

    suspend fun getPopularVolumesForSearchRequest(searchRequest: SearchRequest) =
        networkWithDatabaseCacheRepository.getPopularVolumesForSearchRequest(searchRequest)

    override fun isNetworkOnly() = networkWithDatabaseCacheRepository.isNetworkOnly()
}