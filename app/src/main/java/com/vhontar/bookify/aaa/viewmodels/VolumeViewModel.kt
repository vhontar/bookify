package com.vhontar.bookify.aaa.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vhontar.bookify.aaa.domain.Volume
import com.vhontar.bookify.aaa.network.asDomainModel
import com.vhontar.bookify.aaa.repository.VolumeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
class VolumeViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val repository: VolumeRepository
): ViewModel() {

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
        val newResult: Flow<PagingData<Volume>> = repository.getVolumes(query)
            .map { data -> data.map { it.asDomainModel() } }
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}