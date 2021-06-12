package com.example.searchimageara.database.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import javax.inject.Singleton

@Singleton
@Database(entities = [ImageDataEntity::class],
    exportSchema = false,
    version = 1
    )
@TypeConverters(DataConverter::class)
abstract class DatabaseService : RoomDatabase() {

    abstract fun imageDao() : ImageDataDao
}