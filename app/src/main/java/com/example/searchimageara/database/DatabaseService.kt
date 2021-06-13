package com.example.searchimageara.database.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.searchimageara.domain.model.ImageData
import javax.inject.Singleton

@Singleton
@Database(entities = [ImageData::class],
    exportSchema = false,
    version = 1
    )
@TypeConverters(DataConverter::class)
abstract class DatabaseService : RoomDatabase() {

    abstract fun imageDao() : ImageDataDao
}