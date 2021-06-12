package com.example.searchimageara.network.model


import com.google.gson.annotations.SerializedName

data class ImageDataDto(
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("height")
    val height: Int? = 0,
    @SerializedName("width")
    val width: Int? = 0,
    @SerializedName("thumbnail")
    val thumbnail: String? = null,
    @SerializedName("thumbnailHeight")
    val thumbnailHeight: Int? = 0,
    @SerializedName("thumbnailWidth")
    val thumbnailWidth: Int? = 0,
    @SerializedName("base64Encoding")
    val base64Encoding: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("provider")
    val provider: ProviderDto? = null,
    @SerializedName("imageWebSearchUrl")
    val imageWebSearchUrl: String? = null,
    @SerializedName("webpageUrl")
    val webpageUrl: String? = null
) {}

data class ProviderDto(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("favIcon")
    val favIcon: String? = null,
    @SerializedName("favIconBase64Encoding")
    val favIconBase64Encoding: String? = null
)