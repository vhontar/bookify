package com.vhontar.bookify.aaa.domain

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
data class Volume(
    val id: String = "",
    var searchRequestId: Long = 0,
    val saleInfo: SaleInfo = SaleInfo(),
    val volumeInfo: VolumeInfo = VolumeInfo()
)
