package com.example.searchimageara.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imageDataKeys")
data class ImageDataKeys(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var pageNumber: Int,
    val pageSize: Int,
    val query: String,
    val totalCount: Int
)
