package com.example.searchimageara.di

import com.example.searchimageara.network.SearchImageService
import com.example.searchimageara.network.NetworkConstants
import com.example.searchimageara.network.model.ImageDataDtoMapper
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideImageDataDtoMapper() : ImageDataDtoMapper {
        return ImageDataDtoMapper()
    }

    @Singleton
    @Provides
    fun provideImageSerachService() : SearchImageService {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(SearchImageService::class.java)
    }
}