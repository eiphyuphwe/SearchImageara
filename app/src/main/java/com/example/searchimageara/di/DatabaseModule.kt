package com.example.searchimageara.di

import android.content.Context
import androidx.room.Room
import com.example.searchimageara.database.entity.DatabaseService
import com.example.searchimageara.database.entity.ImageDataDao
import com.example.searchimageara.network.model.ImageDataDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideImageDao(databaseService: DatabaseService) : ImageDataDao {
        return databaseService.imageDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext:Context):DatabaseService{
        return Room.databaseBuilder(
            appContext,
            DatabaseService::class.java,
            "SearchImageara"
        ).build()
    }

    /*@Singleton
    @Provides
    fun provideImageDataDtoMapper() : ImageDataEntityMapper {
        return ImageDataEntityMapper()
    }*/
}