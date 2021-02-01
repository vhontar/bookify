package com.vhontar.bookify.aaa.network

import com.google.gson.annotations.SerializedName
import com.vhontar.bookify.aaa.domain.*

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
data class VolumesNetwork(
    @field:SerializedName("items") val volumes: List<VolumeNetwork> = arrayListOf()
)

fun List<VolumeNetwork>.asDomainModels(): List<Volume> {
    return map { it.asDomainModel() }
}

data class VolumeNetwork(
    @field:SerializedName("id")
    val id: String = "",
    @field:SerializedName("saleInfo")
    val saleInfo: SaleInfoNetwork = SaleInfoNetwork(),
    @field:SerializedName("volumeInfo")
    val volumeInfo: VolumeInfoNetwork = VolumeInfoNetwork()
)

fun VolumeNetwork.asDomainModel(): Volume {
    return Volume(id, saleInfo.asDomainModel(), volumeInfo.asDomainModel())
}

data class SaleInfoNetwork(
    @field:SerializedName("buyLink")
    val buyLink: String = "",
    @field:SerializedName("country")
    val country: String = "",
    @field:SerializedName("isEbook")
    val isEbook: Boolean = false,
    @field:SerializedName("retailPrice")
    val retailPrice: RetailPriceNetwork = RetailPriceNetwork(),
    @field:SerializedName("saleability")
    val saleability: String = ""
)

fun SaleInfoNetwork.asDomainModel(): SaleInfo {
    return SaleInfo(buyLink, country, isEbook, retailPrice.asDomainModel(), saleability)
}

data class VolumeInfoNetwork(
    @field:SerializedName("authors")
    val authors: List<String> = arrayListOf(),
    @field:SerializedName("averageRating")
    val averageRating: Double = 0.0,
    @field:SerializedName("categories")
    val categories: List<String> = arrayListOf(),
    @field:SerializedName("description")
    val description: String = "",
    @field:SerializedName("imageLinks")
    val imageLinks: ImageLinksNetwork = ImageLinksNetwork(),
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

fun VolumeInfoNetwork.asDomainModel(): VolumeInfo {
    return VolumeInfo(
        authors, averageRating, categories, description, imageLinks.asDomainModel(),
        pageCount, printedPageCount, publishedDate, publisher, ratingsCount, title
    )
}

data class RetailPriceNetwork(
    @field:SerializedName("amount")
    val amount: Double = 0.0,
    @field:SerializedName("currencyCode")
    val currencyCode: String = ""
)

fun RetailPriceNetwork.asDomainModel(): RetailPrice {
    return RetailPrice(amount, currencyCode)
}

data class ImageLinksNetwork(
    @field:SerializedName("medium")
    val medium: String = "",
    @field:SerializedName("small")
    val small: String = "",
    @field:SerializedName("smallThumbnail")
    val smallThumbnail: String = "",
    @field:SerializedName("thumbnail")
    val thumbnail: String = ""
)

fun ImageLinksNetwork.asDomainModel(): ImageLinks {
    return ImageLinks(medium, small, smallThumbnail, thumbnail)
}
