package com.vhontar.bookify.aaa.di

import android.content.Context
import com.vhontar.bookify.aaa.db.BookifyDatabase
import com.vhontar.bookify.aaa.db.SearchRequestDao
import com.vhontar.bookify.aaa.db.VolumeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideBookifyDatabase(@ApplicationContext context: Context): BookifyDatabase {
        return BookifyDatabase.getInstance(context)
    }

    @Provides
    fun provideSearchResultDao(bookifyDatabase: BookifyDatabase): SearchRequestDao {
        return bookifyDatabase.searchRequestDao()
    }

    @Provides
    fun provideVolumeDao(bookifyDatabase: BookifyDatabase): VolumeDao {
        return bookifyDatabase.volumeDao()
    }
}