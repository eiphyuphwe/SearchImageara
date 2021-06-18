package com.example.searchimageara.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.searchimageara.domain.model.ImageDataKeys

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplaceRemoteKeys(imageDataKeys: ImageDataKeys)

    @Query("SELECT * FROM imageDataKeys WHERE `query` = :query ORDER BY id DESC")
    suspend fun remoteKeyByQuery(query:String): List<ImageDataKeys>

    @Query("SELECT * FROM imageDataKeys")
    suspend fun selectAllKeys(): List<ImageDataKeys>

    @Query("DELETE FROM imageDataKeys WHERE `query` = :query")
    suspend fun deleteByQuery(query: String)

}