package com.vhontar.bookify.aaa.db

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.vhontar.bookify.aaa.domain.*
import org.joda.time.DateTime

/**
 * Created by Vladyslav Hontar (vhontar) on 01.02.21.
 */

data class RetailPriceEntity(
    @ColumnInfo(name = "amount") var amount: Double,
    @ColumnInfo(name = "currency_code") var currencyCode: String
)

fun RetailPriceEntity.asDomainModel() = RetailPrice(amount, currencyCode)
fun RetailPrice.asDatabaseModel() = RetailPriceEntity(amount, currencyCode)

data class SaleInfoEntity(
    @ColumnInfo(name = "buy_link") var buyLink: String,
    @ColumnInfo(name = "country") var country: String,
    @ColumnInfo(name = "is_ebook") var isEbook: Boolean,
    @Embedded var retailPrice: RetailPriceEntity,
    @ColumnInfo(name = "saleability") var saleability: String
)

fun SaleInfoEntity.asDomainModel() =
    SaleInfo(buyLink, country, isEbook, retailPrice.asDomainModel(), saleability)
fun SaleInfo.asDatabaseModel() =
    SaleInfoEntity(buyLink, country, isEbook, retailPrice.asDatabaseModel(), saleability)

data class ImageLinksEntity(
    @ColumnInfo(name = "medium") var medium: String,
    @ColumnInfo(name = "small") val small: String,
    @ColumnInfo(name = "small_thumbnail") var smallThumbnail: String,
    @ColumnInfo(name = "thumbnail") var thumbnail: String
)

fun ImageLinksEntity.asDomainModel() = ImageLinks(medium, small, smallThumbnail, thumbnail)
fun ImageLinks.asDatabaseModel() = ImageLinksEntity(medium, small, smallThumbnail, thumbnail)

data class VolumeInfoEntity(
    @ColumnInfo(name = "authors") var authors: List<String>,
    @ColumnInfo(name = "average_rating") var averageRating: Double,
    @ColumnInfo(name = "categories") val categories: List<String>,
    @ColumnInfo(name = "description") var description: String,
    @Embedded val imageLinks: ImageLinks,
    @ColumnInfo(name = "page_count") val pageCount: Int,
    @ColumnInfo(name = "printed_page_count") var printedPageCount: Int,
    @ColumnInfo(name = "published_date") var publishedDate: String,
    @ColumnInfo(name = "publisher") var publisher: String,
    @ColumnInfo(name = "ratings_count") var ratingsCount: Int,
    @ColumnInfo(name = "title") var title: String
)

fun VolumeInfoEntity.asDomainModel(): VolumeInfo {
    return VolumeInfo(
        authors, averageRating, categories, description, imageLinks, pageCount,
        printedPageCount, publishedDate, publisher, ratingsCount, title
    )
}
fun VolumeInfo.asDomainModel(): VolumeInfoEntity {
    return VolumeInfoEntity(
        authors, averageRating, categories, description, imageLinks, pageCount,
        printedPageCount, publishedDate, publisher, ratingsCount, title
    )
}

@Entity(tableName = "volumes",
    primaryKeys = ["id", "search_request_id"],
    foreignKeys = [ForeignKey(onDelete = CASCADE, entity = SearchRequestEntity::class, parentColumns = ["id"], childColumns = ["search_request_id"])],
    indices = [Index("search_request_id")]
)
data class VolumeEntity(
    @ColumnInfo(name = "id") var volumeId: String,
    @ColumnInfo(name = "search_request_id") var searchRequestId: Long,
    @Embedded var saleInfo: SaleInfoEntity,
    @Embedded var volumeInfo: VolumeInfoEntity
)

fun VolumeEntity.asDomainModel() =
    Volume(volumeId, searchRequestId, saleInfo.asDomainModel(), volumeInfo.asDomainModel())
fun List<VolumeEntity>.asDomainModels(): List<Volume> {
    val volumes = arrayListOf<Volume>()
    forEach { volumes.add(it.asDomainModel()) }
    return volumes
}
fun Volume.asDatabaseModel() =
    VolumeEntity(id, searchRequestId, saleInfo.asDatabaseModel(), volumeInfo.asDomainModel())
@JvmName("asDatabaseModelsVolumes")
fun List<Volume>.asDatabaseModels(): List<VolumeEntity> {
    val entities = arrayListOf<VolumeEntity>()
    forEach { entities.add(it.asDatabaseModel()) }
    return entities
}

@Entity(tableName = "search_requests")
data class SearchRequestEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "q_search") var qSearch: String,
    @ColumnInfo(name = "books_count") var booksCount: Int,
    @ColumnInfo(name = "created_at") var createdAt: DateTime = DateTime.now()
)

fun SearchRequestEntity.asDomainModel() = SearchRequest(id, qSearch, booksCount, createdAt = createdAt)
@JvmName("asDatabaseModelsSearchRequests")
fun List<SearchRequestEntity>.asDomainModels(): List<SearchRequest> {
    val entities = arrayListOf<SearchRequest>()
    forEach { entities.add(it.asDomainModel()) }
    return entities
}
fun SearchRequest.asDatabaseModel() = SearchRequestEntity(id, qSearch, booksCount, createdAt)

// one to many relation
data class SearchRequestWithVolumesEntity(
    @Embedded val searchRequestEntity: SearchRequestEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "search_request_id"
    )
    val volumes: List<VolumeEntity> = emptyList()
)

fun SearchRequestWithVolumesEntity.asDomainModel(): SearchRequest {
    return SearchRequest(
        searchRequestEntity.id,
        searchRequestEntity.qSearch,
        searchRequestEntity.booksCount,
        volumes = volumes.asDomainModels(),
        createdAt = searchRequestEntity.createdAt
    )
}

@Entity(tableName = "remote_keys", primaryKeys = ["volume_id", "search_request_id"])
data class RemoteKeyEntity(
    @ColumnInfo(name = "volume_id") val volumeId: String,
    @ColumnInfo(name = "search_request_id") val searchRequestId: Long,
    @ColumnInfo(name = "prev_key") val prevKey: Int?,
    @ColumnInfo(name = "next_key") val nextKey: Int?
)

fun RemoteKeyEntity.asDomainModel() = RemoteKey(volumeId, searchRequestId, prevKey, nextKey)
fun RemoteKey.asDatabaseModel() = RemoteKeyEntity(volumeId, searchRequestId, prevKey, nextKey)
@JvmName("asDatabaseModelsRemoteKeys")
fun List<RemoteKey>.asDatabaseModels(): List<RemoteKeyEntity> {
    val entities = arrayListOf<RemoteKeyEntity>()
    this.forEach {
        entities.add(RemoteKeyEntity(it.volumeId, it.searchRequestId, it.prevKey, it.nextKey))
    }
    return entities
}