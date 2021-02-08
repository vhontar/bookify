package com.vhontar.bookify.aaa.domain

import com.vhontar.bookify.R
import org.joda.time.DateTime
import kotlin.random.Random

/**
 * Created by Vladyslav Hontar (vhontar) on 31.01.21.
 */
data class SearchRequest(
    val id: Long = 0,
    var qSearch: String = "",
    var booksCount: Int = 0,
    var volumes: List<Volume> = arrayListOf(),
    var createdAt: DateTime = DateTime.now(),
    var isNewRequest: Boolean = false
) {
    private val coverIds = arrayListOf(
        R.drawable.cover_background_1,
        R.drawable.cover_background_2,
        R.drawable.cover_background_3,
        R.drawable.cover_background_4
    )

    fun getCoverId() = coverIds[Random.nextInt(0, 4)]
}
