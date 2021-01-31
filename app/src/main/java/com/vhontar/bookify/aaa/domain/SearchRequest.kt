package com.vhontar.bookify.aaa.domain

/**
 * Created by Vladyslav Hontar (vhontar) on 31.01.21.
 */
data class SearchRequest(
    var qSearch: String = "",
    var booksCount: Int = 0,
    var coverId: Int = 0
)
