package com.vhontar.bookify.aaa.domain

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
data class SaleInfo(
    val buyLink: String = "",
    val country: String = "",
    val isEbook: Boolean = false,
    val retailPrice: RetailPrice = RetailPrice(),
    val saleability: String = ""
)
