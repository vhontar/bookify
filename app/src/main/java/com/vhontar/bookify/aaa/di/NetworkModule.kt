package com.vhontar.bookify.aaa.di

import com.vhontar.bookify.aaa.network.BookifyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideBookifyService(): BookifyService = BookifyService.create()
}