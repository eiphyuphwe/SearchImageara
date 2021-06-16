package com.example.searchimageara.reposistory

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.searchimageara.domain.model.ImageData
import kotlinx.coroutines.flow.Flow

interface SearchImageReposistory {
    suspend fun search(query:String,autoCorrect:Boolean) : Flow<PagingData<ImageData>>
    suspend fun insertAll(imageDataList:List<ImageData>)
    suspend fun selectByImageWebSearchUrl(url:String?) : List<ImageData>
}