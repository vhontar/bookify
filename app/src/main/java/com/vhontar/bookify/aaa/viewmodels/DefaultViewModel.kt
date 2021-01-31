package com.vhontar.bookify.aaa.viewmodels

import androidx.lifecycle.LiveData

/**
 * Created by Vladyslav Hontar (vhontar) on 31.01.21.
 */
interface DefaultViewModel {
    val isServerDown: LiveData<Boolean>
    val isNetworkOff: LiveData<Boolean>
    val snackbarText: LiveData<Int>
    val dataLoading: LiveData<Boolean>
}