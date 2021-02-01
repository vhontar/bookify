package com.vhontar.bookify.aaa.network

import com.vhontar.bookify.aaa.constants.BASE_URL
import com.vhontar.bookify.aaa.constants.NETWORK_PAGE_SIZE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Vladyslav Hontar (vhontar) on 17.01.21.
 */
interface BookifyService {

    @GET("volumes")
    suspend fun getVolumes(
        @Query("key") accessToken: String,
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int = 0,
        @Query("maxResults") maxResults: Int = NETWORK_PAGE_SIZE,
        @Query("printType") printType: String = "BOOKS",
        @Query("langRestrict") langRestrict: String = "en"
    ): Response<VolumesNetwork>

    @GET("volumes/{id}")
    suspend fun getVolume(
        @Path("id") volumeId: String,
        @Query("key") accessToken: String
    ): Response<VolumeNetwork>

    companion object {
        fun create(): BookifyService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()


            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BookifyService::class.java)
        }
    }
}