package com.example.searchimageara.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.domain.model.ImageDataKeys
import javax.inject.Singleton

@Singleton
@Database(
    entities = [ImageData::class, ImageDataKeys::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(DataConverter::class)
abstract class DatabaseService : RoomDatabase() {

    abstract fun imageDao(): ImageDataDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}