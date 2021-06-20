package com.example.searchimageara.ui.searchimage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.repository.SearchImageRepository
import kotlinx.coroutines.flow.Flow

class SearchImageViewModel
@ViewModelInject
constructor(
    private val searchImageRepository: SearchImageRepository
) : ViewModel() {

    suspend fun search(query: String, autoCorrect: Boolean): Flow<PagingData<ImageData>> {
        return searchImageRepository.search(
            query = query,
            autoCorrect = autoCorrect
        ).cachedIn(viewModelScope)
    }
}