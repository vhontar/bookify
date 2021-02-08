package com.vhontar.bookify.aaa.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vhontar.bookify.aaa.db.converter.ArrayListConverter
import com.vhontar.bookify.aaa.db.converter.DateTimeConverter

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
@Database(
    version = 3,
    entities = [VolumeEntity::class, SearchRequestEntity::class, RemoteKeyEntity::class]
)
@TypeConverters(DateTimeConverter::class, ArrayListConverter::class)
abstract class BookifyDatabase : RoomDatabase() {
    abstract fun searchRequestDao(): SearchRequestDao
    abstract fun volumeDao(): VolumeDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
        private const val DATABASE_NAME = "bookifyDB"

        // For Singleton instantiation
        @Volatile
        var instance: BookifyDatabase? = null

        fun getInstance(context: Context): BookifyDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): BookifyDatabase {
            return Room.databaseBuilder(context, BookifyDatabase::class.java, DATABASE_NAME).build()
        }
    }
}