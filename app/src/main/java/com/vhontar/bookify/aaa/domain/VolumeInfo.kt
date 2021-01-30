package com.vhontar.bookify.aaa.domain

import android.content.Context
import com.vhontar.bookify.R

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
    val publishedDate: String = "",
    val publisher: String = "",
    val ratingsCount: Int = 0,
    val title: String = ""
) {
    fun getAuthor(context: Context) = if (authors.isEmpty()) context.getString(R.string.not_found) else authors[0]
}
