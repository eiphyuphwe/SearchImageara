package com.example.searchimageara.presentation.searchimage

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.reposistory.SearchImageReposistory
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchImageViewModel
@ViewModelInject
constructor(
    private val searchImageReposistory: SearchImageReposistory
) : ViewModel() {
    private var imageDataLists: MutableLiveData<List<ImageData>> =
        MutableLiveData<List<ImageData>>()

    suspend fun search(q: String, page: Int, pageSize: Int, autoCorrect: Boolean) : Flow<PagingData<ImageData>>{

           return searchImageReposistory.search(
                query = q,
                page = page,
                pageSize = pageSize,
                autoCorrect = autoCorrect
            ).cachedIn(viewModelScope)

    }

    fun getSearchResults(): LiveData<List<ImageData>> {
        return imageDataLists
    }
}