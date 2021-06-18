package com.example.searchimageara.di

import android.content.Context
import androidx.room.Room
import com.example.searchimageara.database.entity.DatabaseService
import com.example.searchimageara.network.NetworkConstants
import com.example.searchimageara.network.SearchImageService
import com.example.searchimageara.network.model.ImageDataDtoMapper
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context, DatabaseService::class.java
        ).allowMainThreadQueries()
            .build()

    @Provides
    @Named("test_mapper")
    fun provideImageDataDtoMapper() : ImageDataDtoMapper {
        return ImageDataDtoMapper()
    }

    @Provides
    @Named("test_network")
    fun provideImageSerachService() : SearchImageService {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(SearchImageService::class.java)
    }
}