package com.vhontar.bookify.aaa.viewmodels.volume.networkonly

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vhontar.bookify.R
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.aaa.network.resource.wrapEspressoIdlingResource
import com.vhontar.bookify.aaa.network.response.ResponseStatus
import com.vhontar.bookify.aaa.repository.volume.networkonly.VolumeNetworkOnlyRepository
import com.vhontar.bookify.aaa.viewmodels.DefaultViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */

@HiltViewModel
class VolumeNetworkOnlyViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val networkOnlyRepository: VolumeNetworkOnlyRepository
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

    private val _popularVolumes = MutableLiveData<List<Volume>>()
    val popularVolumes: LiveData<List<Volume>> = _popularVolumes

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
        val newResult: Flow<PagingData<Volume>> = networkOnlyRepository.getVolumes(query).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    suspend fun getPopularVolumes(query: String): List<Volume> {
        _dataLoading.value = true
        _isNetworkOff.value = false

        val response = networkOnlyRepository.getPopularVolumes(query)

        when (response.status) {
            ResponseStatus.NETWORK_OFF -> {
                _snackbarText.value = R.string.error_message_network
                _isNetworkOff.value = true
            }
            ResponseStatus.SERVER_ERROR -> _snackbarText.value =
                R.string.error_something_went_wrong
            ResponseStatus.RESPONSE_ERROR -> _snackbarText.value =
                R.string.error_response_without_success
            ResponseStatus.SERVER_DOWN -> {
                _isServerDown.value = true
            }
            ResponseStatus.SUCCESS -> {
                _isServerDown.value = false
                return response.data ?: emptyList()
            }
            else -> {
            }
        }

        _dataLoading.value = false

        return emptyList()
    }

    // Another method of loading data to live data and use it in data-binding
    fun getVolume(volumeId: String) {
        _dataLoading.value = true
        _isNetworkOff.value = false

        wrapEspressoIdlingResource {
            viewModelScope.launch {
                val response = networkOnlyRepository.getVolume(volumeId)

                when (response.status) {
                    ResponseStatus.NETWORK_OFF -> {
                        _snackbarText.value = R.string.error_message_network
                        _isNetworkOff.value = true
                    }
                    ResponseStatus.SERVER_ERROR -> _snackbarText.value =
                        R.string.error_something_went_wrong
                    ResponseStatus.RESPONSE_ERROR -> _snackbarText.value =
                        R.string.error_response_without_success
                    ResponseStatus.SERVER_DOWN -> {
                        _isServerDown.value = true
                    }
                    ResponseStatus.SUCCESS -> {
                        _isServerDown.value = false
                        _volume.value = response.data
                    }
                    else -> {
                    }
                }

                _dataLoading.value = false
            }
        }
    }

    override fun isNetworkOnly() = networkOnlyRepository.isNetworkOnly()
}