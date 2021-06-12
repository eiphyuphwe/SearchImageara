package com.example.searchimageara.network.responses

import com.example.searchimageara.network.model.ImageDataDto
import com.google.gson.annotations.SerializedName

data class SearchImageResponse(
    @SerializedName("_type")
    var type:String,
    @SerializedName("totalCount")
    var count : Int,
    @SerializedName("value")
    var imageList : List<ImageDataDto>
) {}