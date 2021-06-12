package com.example.searchimageara.database.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImageData(imageDataEntity: ImageDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg  images:ImageDataEntity)

    @Query("SELECT * FROM image WHERE image_websearch_url LIKE :q")
    suspend fun selectImagesByImageWebSearchUrl(q:String) : List<ImageDataEntity>

}