package com.example.searchimageara.di

import com.example.searchimageara.database.entity.DatabaseService
import com.example.searchimageara.network.SearchImageService
import com.example.searchimageara.network.model.ImageDataDtoMapper
import com.example.searchimageara.repository.SearchImageRepository
import com.example.searchimageara.repository.SearchImageRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    fun provideSearchImageRepository(
        service: SearchImageService, mapper: ImageDataDtoMapper,
        dbService: DatabaseService
    ): SearchImageRepository {
        return SearchImageRepository_Impl(service, mapper, dbService)
    }
}