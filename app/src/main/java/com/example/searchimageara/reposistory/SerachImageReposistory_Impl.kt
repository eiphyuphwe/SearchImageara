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
        autoCorrect: Boolean
    ): Flow<PagingData<ImageData>> {
        return Pager(
            PagingConfig(pageSize = NetworkConstants.DEFAULT_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = SearchImageRemoteMediator(imageService,networkMapper, databaseService,query,autoCorrect),
            pagingSourceFactory = {
                databaseService.imageDao().selectAll(query)
            }
        ).flow
    }

}