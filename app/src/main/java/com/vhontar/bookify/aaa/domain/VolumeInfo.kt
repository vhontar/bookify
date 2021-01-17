package com.vhontar.bookify.aaa.domain

import com.vhontar.bookify.aaa.domain.ImageLinks
import org.joda.time.DateTime

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
data class VolumeInfo(
    val authors: List<String> = arrayListOf(),
    val averageRating: Double = 0.0,
    val categories: List<String> = arrayListOf(),
    val description: String = "",
    val imageLinks: ImageLinks = ImageLinks(),
    val pageCount: Int = 0,
    val printedPageCount: Int = 0,
    val publishedDate: DateTime = DateTime.now(),
    val publisher: String = "",
    val ratingsCount: Int = 0,
    val title: String = ""
)
