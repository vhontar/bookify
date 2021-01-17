package com.vhontar.bookify.aaa.domain

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
data class Volume(
    val id: String = "",
    val saleInfo: SaleInfo = SaleInfo(),
    val volumeInfo: VolumeInfo = VolumeInfo()
)
