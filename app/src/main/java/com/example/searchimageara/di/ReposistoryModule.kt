package com.example.searchimageara.di

import com.example.searchimageara.database.entity.DatabaseService
import com.example.searchimageara.database.entity.ImageDataEntityMapper
import com.example.searchimageara.network.SearchImageService
import com.example.searchimageara.network.model.ImageDataDtoMapper
import com.example.searchimageara.reposistory.SearchImageReposistory
import com.example.searchimageara.reposistory.SerachImageReposistory_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ReposistoryModule {

    @Provides
    fun provideSearchImageRepository(service:SearchImageService,mapper:ImageDataDtoMapper,
    dbService:DatabaseService,dbMapper:ImageDataEntityMapper) : SearchImageReposistory {
        return SerachImageReposistory_Impl(service,mapper,dbService,dbMapper)
    }
}