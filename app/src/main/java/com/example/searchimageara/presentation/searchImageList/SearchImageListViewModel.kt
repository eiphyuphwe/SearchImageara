package com.example.searchimageara.presentation.searchImageList

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.reposistory.SearchImageReposistory
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SearchImageListViewModel
    @ViewModelInject
    constructor(
        private val searchImageReposistory: SearchImageReposistory
    ): ViewModel() {
     private var imageDataLists : MutableLiveData<List<ImageData>> = MutableLiveData<List<ImageData>>()

    fun search(q:String,page:Int,pageSize:Int,autoCorrect:Boolean) {
        viewModelScope.launch {
           val result =  searchImageReposistory.search(
                query = q,
                page = page,
                pageSize = pageSize,
                autoCorrect = autoCorrect
            )
            imageDataLists?.value = result
        }
    }
    fun getSearchResults() : LiveData<List<ImageData>> {
        return imageDataLists
    }
}