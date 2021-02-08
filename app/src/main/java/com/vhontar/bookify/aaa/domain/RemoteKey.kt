package com.vhontar.bookify.aaa.domain

/**
 * Created by Vladyslav Hontar (vhontar) on 08.02.21.
 */
data class RemoteKey(
    var volumeId: String = "",
    var searchRequestId: Long = 0,
    var prevKey: Int? = null,
    var nextKey: Int? = null
)