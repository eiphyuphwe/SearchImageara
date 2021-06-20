package com.example.searchimageara.repository

import androidx.paging.PagingData
import com.example.searchimageara.domain.model.ImageData
import kotlinx.coroutines.flow.Flow

interface SearchImageRepository {
    suspend fun search(query: String, autoCorrect: Boolean): Flow<PagingData<ImageData>>

}