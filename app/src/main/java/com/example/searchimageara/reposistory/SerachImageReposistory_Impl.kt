package com.example.searchimageara.reposistory

import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.network.SearchImageService
import com.example.searchimageara.network.NetworkConstants
import com.example.searchimageara.network.model.ImageDataDtoMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SerachImageReposistory_Impl
@Inject
constructor(
    private val imageService: SearchImageService,
    private val mapper: ImageDataDtoMapper
) : SearchImageReposistory {
    override suspend fun search(
        query: String,
        pageNumber: Int,
        pageSize: Int,
        autoCorrect: Boolean
    ): List<ImageData> {
        val response = imageService.searchImages(
            NetworkConstants.API_VALUE,
            NetworkConstants.API_HOST_VALUE,
            query,
            pageNumber,
            pageSize,
            autoCorrect
        )
        return response.let { mapper.toDomainList(it.imageList) }
    }

}