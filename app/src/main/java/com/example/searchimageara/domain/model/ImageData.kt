package com.example.searchimageara.domain.model

data class ImageData(

    val url: String? = null,
    val height: Int? = 0,
    val width: Int? = 0,
    val thumbnail: String? = null,
    val thumbnailHeight: Int? = 0,
    val thumbnailWidth: Int? = 0,
    val base64Encoding: String? = null,
    val name: String? = null,
    val title: String? = null,
    val provider: Provider? = null,
    val imageWebSearchUrl: String? = null,
    val webpageUrl: String? = null


)

data class Provider(
    val name: String? = null,
    val favIcon: String? = null,
    val favIconBase64Encoding: String? = null
)
