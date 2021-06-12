package com.example.searchimageara.network

import com.example.searchimageara.network.responses.ImageSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ImageSearchService {
    @GET("ImageSearchAPI")
    suspend fun searchImages(
        @Header("x-rapidapi-key") apiKey:String,
        @Header("x-rapidapi-host") host:String,
        @Query("q") q:String,
        @Query("pageNumber") pageNumber:Int,
        @Query("pageSize") pageSize:Int,
        @Query("autoCorrect") autoCorrect:Boolean
    ) : ImageSearchResponse
}