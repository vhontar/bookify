package com.vhontar.bookify.aaa.viewmodels

import androidx.lifecycle.ViewModel
import com.vhontar.bookify.aaa.repository.VolumeRepository
import androidx.hilt.lifecycle.ViewModelInject

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
class VolumeViewModel @ViewModelInject constructor(
    private val repository: VolumeRepository
): ViewModel() {

}