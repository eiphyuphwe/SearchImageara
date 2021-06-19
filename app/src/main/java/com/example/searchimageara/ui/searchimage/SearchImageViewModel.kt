package com.example.searchimageara.ui.searchimage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private var imageDataLists: MutableLiveData<List<ImageData>> =
        MutableLiveData<List<ImageData>>()

    suspend fun search(q: String, autoCorrect: Boolean): Flow<PagingData<ImageData>> {

        return searchImageRepository.search(
            query = q,
            autoCorrect = autoCorrect
        ).cachedIn(viewModelScope)

    }

    fun getSearchResults(): LiveData<List<ImageData>> {
        return imageDataLists
    }
}