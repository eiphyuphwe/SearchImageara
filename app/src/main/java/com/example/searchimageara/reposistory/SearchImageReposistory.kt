package com.example.searchimageara.reposistory

import com.example.searchimageara.domain.model.ImageData

interface SearchImageReposistory {
    suspend fun search(query:String,page:Int,pageSize:Int,autoCorrect:Boolean) : List<ImageData>
    suspend fun insertAll(imageDataList:List<ImageData>)
    suspend fun selectAll() : List<ImageData>
}