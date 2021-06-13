package com.example.searchimageara.presentation.searchImageList

import android.util.Log
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
) : ViewModel() {
    private var imageDataLists: MutableLiveData<List<ImageData>> =
        MutableLiveData<List<ImageData>>()

    fun search(q: String, page: Int, pageSize: Int, autoCorrect: Boolean) {
        viewModelScope.launch {
            val result = searchImageReposistory.search(
                query = q,
                page = page,
                pageSize = pageSize,
                autoCorrect = autoCorrect
            )

            val dbresult =
                searchImageReposistory.selectByImageWebSearchUrl(result.get(0).imageWebSearchUrl)
            if (dbresult.size == 0) {
                searchImageReposistory.insertAll(result)
                imageDataLists?.value = result
            } else {
                // show db r
                imageDataLists?.value = dbresult
                dbresult.forEach {
                    Log.e("DB", "db" + it.toString())
                }
            }
        }
    }

    fun getSearchResults(): LiveData<List<ImageData>> {
        return imageDataLists
    }
}