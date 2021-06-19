package com.example.searchimageara.ui.imagedetail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchimageara.domain.model.ImageData

class ImageDetailViewModel : ViewModel(){
    private val imageLiveData : MutableLiveData<ImageData> = MutableLiveData<ImageData>()
    companion object{
        private const val IMAGE_ARG = "image"
        fun createArguments(imageData: ImageData): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(IMAGE_ARG, imageData)
            return bundle
        }
    }

    fun loadArguments(arguments: Bundle?) {
        if (arguments == null) {
            return
        }

        val image: ImageData? = arguments.get(IMAGE_ARG) as ImageData?
        image?.let {
            imageLiveData.value = it
        }
    }
    fun getImageDetailData() : LiveData<ImageData> {
        return imageLiveData
    }
}