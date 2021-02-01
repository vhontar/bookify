package com.vhontar.bookify.aaa.domain

import org.joda.time.DateTime

/**
 * Created by Vladyslav Hontar (vhontar) on 31.01.21.
 */
data class SearchRequest(
    var qSearch: String = "",
    var booksCount: Int = 0,
    var coverId: Int = 0,
    var volumes: List<Volume> = arrayListOf(),
    var createdAt: DateTime = DateTime.now()
)
