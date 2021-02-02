package com.vhontar.bookify.aaa.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.aaa.network.resource.wrapEspressoIdlingResource
import com.vhontar.bookify.aaa.network.response.ResponseStatus
import com.vhontar.bookify.aaa.repository.volume.networkonly.VolumeNetworkOnlyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.vhontar.bookify.R

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
class VolumeViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val repository: VolumeNetworkOnlyRepository
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

    fun getVolumes(query: String): Flow<PagingData<Volume>> {
        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null)
            return lastResult

        currentQueryValue = query
        val newResult: Flow<PagingData<Volume>> = repository.getVolumes(query).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    fun getVolume(volumeId: String) {
        _dataLoading.value = true
        _isNetworkOff.value = false

        wrapEspressoIdlingResource {
            viewModelScope.launch {
                val response = repository.getVolume(volumeId)

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
}