package com.example.searchimageara.domain.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_data")
data class ImageData(

   // @PrimaryKey(autoGenerate = true) val id: Int,
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "url")
    val url: String = "",
    @ColumnInfo(name = "height")
    val height: Int? = 0,
    @ColumnInfo(name = "width")
    val width: Int? = 0,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String? = null,
    @ColumnInfo(name = "thumbnail_height")
    val thumbnailHeight: Int? = 0,
    @ColumnInfo(name = "thumbnail_width")
    val thumbnailWidth: Int? = 0,
    @ColumnInfo(name = "base64_encoding")
    val base64Encoding: String? = null,
    @ColumnInfo(name = "name")
    val name: String? = null,
    @ColumnInfo(name = "title")
    val title: String? = null,
    @ColumnInfo(name = "provider")
    val provider: Provider? = null,
    @ColumnInfo(name = "image_websearch_url")
    val imageWebSearchUrl: String? = null,
    @ColumnInfo(name = "webpage_url")
    val webpageUrl: String? = null,
    @ColumnInfo(name = "query")
    val query: String? = null


)

data class Provider(
    val name: String? = null,
    val favIcon: String? = null,
    val favIconBase64Encoding: String? = null
)
