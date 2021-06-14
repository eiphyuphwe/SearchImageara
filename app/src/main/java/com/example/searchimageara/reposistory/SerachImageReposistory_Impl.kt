package com.example.searchimageara.reposistory

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.searchimageara.database.entity.DatabaseService
import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.network.SearchImageService
import com.example.searchimageara.network.NetworkConstants
import com.example.searchimageara.network.model.ImageDataDtoMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SerachImageReposistory_Impl
@Inject
constructor(
    private val imageService: SearchImageService,
    private val networkMapper: ImageDataDtoMapper,
    private val databaseService: DatabaseService,
) : SearchImageReposistory {

    override suspend fun search(
        query: String,
        pageNumber: Int,
        pageSize: Int,
        autoCorrect: Boolean
    ): Flow<PagingData<ImageData>> {
        return Pager(
            PagingConfig(pageSize = 10, enablePlaceholders = false, prefetchDistance = 1),
            remoteMediator = SearchImageRemoteMediator(imageService,networkMapper, databaseService,query,pageNumber,pageSize,autoCorrect),
            pagingSourceFactory = {
                databaseService.imageDao().selectAll(query)
            }
        ).flow
    }

    override suspend fun insertAll(imageDataEntityList: List<ImageData>) {
       // val dataModelList = dbMapper.fromDomainList(imageDataEntityList)
        imageDataEntityList.forEach {
            databaseService.imageDao().insertImageData(it)
        }

    }

    override suspend fun selectByImageWebSearchUrl(url: String?): List<ImageData> {
       //return dbMapper.toDomainList(databaseService.imageDao().selectImagesByImageWebSearchUrl(url))
        return databaseService.imageDao().selectImagesByImageWebSearchUrl(url)
    }



}