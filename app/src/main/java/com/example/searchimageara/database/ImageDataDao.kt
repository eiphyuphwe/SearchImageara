package com.example.searchimageara.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.searchimageara.domain.model.ImageData

@Dao
interface ImageDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllImageData(imageDataList: List<ImageData>)

    @Query("SELECT * FROM image_data WHERE `query` LIKE :q")
    fun selectAll(q: String?): PagingSource<Int, ImageData>

    @Query("DELETE FROM image_data WHERE `query` = :q")
    suspend fun deleteImageDataByQuery(q: String)

    @Query("SELECT * FROM image_data")
    fun selectAllData(): List<ImageData>

}