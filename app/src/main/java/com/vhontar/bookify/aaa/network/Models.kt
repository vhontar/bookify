package com.vhontar.bookify.aaa.network

import com.google.gson.annotations.SerializedName
import com.vhontar.bookify.aaa.domain.*
import org.joda.time.DateTime

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
data class NetworkVolumesResponse(
    @field:SerializedName("items") val volumes: List<NetworkVolume> = arrayListOf()
)

fun List<NetworkVolume>.asDomainModels(): List<Volume> {
    return map { it.asDomainModel() }
}

data class NetworkVolume(
    @field:SerializedName("id")
    val id: String = "",
    @field:SerializedName("saleInfo")
    val saleInfo: NetworkSaleInfo = NetworkSaleInfo(),
    @field:SerializedName("volumeInfo")
    val volumeInfo: NetworkVolumeInfo = NetworkVolumeInfo()
)

fun NetworkVolume.asDomainModel(): Volume {
    return Volume(id, saleInfo.asDomainModel(), volumeInfo.asDomainModel())
}

data class NetworkSaleInfo(
    @field:SerializedName("buyLink")
    val buyLink: String = "",
    @field:SerializedName("country")
    val country: String = "",
    @field:SerializedName("isEbook")
    val isEbook: Boolean = false,
    @field:SerializedName("retailPrice")
    val retailPrice: NetworkRetailPrice = NetworkRetailPrice(),
    @field:SerializedName("saleability")
    val saleability: String = ""
)

fun NetworkSaleInfo.asDomainModel(): SaleInfo {
    return SaleInfo(buyLink, country, isEbook, retailPrice.asDomainModel(), saleability)
}

data class NetworkVolumeInfo(
    @field:SerializedName("authors")
    val authors: List<String> = arrayListOf(),
    @field:SerializedName("averageRating")
    val averageRating: Double = 0.0,
    @field:SerializedName("categories")
    val categories: List<String> = arrayListOf(),
    @field:SerializedName("description")
    val description: String = "",
    @field:SerializedName("imageLinks")
    val imageLinks: NetworkImageLinks = NetworkImageLinks(),
    @field:SerializedName("pageCount")
    val pageCount: Int = 0,
    @field:SerializedName("printedPageCount")
    val printedPageCount: Int = 0,
    @field:SerializedName("publishedDate")
    val publishedDate: String = "",
    @field:SerializedName("publisher")
    val publisher: String = "",
    @field:SerializedName("ratingsCount")
    val ratingsCount: Int = 0,
    @field:SerializedName("title")
    val title: String = ""
)

fun NetworkVolumeInfo.asDomainModel(): VolumeInfo {
    return VolumeInfo(
        authors, averageRating, categories, description, imageLinks.asDomainModel(),
        pageCount, printedPageCount, publishedDate, publisher, ratingsCount, title
    )
}

data class NetworkRetailPrice(
    @field:SerializedName("amount")
    val amount: Double = 0.0,
    @field:SerializedName("currencyCode")
    val currencyCode: String = ""
)

fun NetworkRetailPrice.asDomainModel(): RetailPrice {
    return RetailPrice(amount, currencyCode)
}

data class NetworkImageLinks(
    @field:SerializedName("medium")
    val medium: String = "",
    @field:SerializedName("small")
    val small: String = "",
    @field:SerializedName("smallThumbnail")
    val smallThumbnail: String = "",
    @field:SerializedName("thumbnail")
    val thumbnail: String = ""
)

fun NetworkImageLinks.asDomainModel(): ImageLinks {
    return ImageLinks(medium, small, smallThumbnail, thumbnail)
}
