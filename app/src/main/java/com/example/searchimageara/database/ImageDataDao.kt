package com.example.searchimageara.database.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.searchimageara.domain.model.ImageData

@Dao
interface ImageDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImageData(imageData: ImageData)

  /*  @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg images: List<ImageDataEntity>)*/

    @Query("SELECT * FROM image_data WHERE image_websearch_url LIKE :q")
    suspend fun selectImagesByImageWebSearchUrl(q:String?) : List<ImageData>

    @Query("SELECT * FROM image_data ")
    suspend fun selectAll() : List<ImageData>

}