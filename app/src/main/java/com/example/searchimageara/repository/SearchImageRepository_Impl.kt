package com.example.searchimageara.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.searchimageara.database.DatabaseService
import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.network.NetworkConstants
import com.example.searchimageara.network.SearchImageService
import com.example.searchimageara.network.model.ImageDataDtoMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchImageRepository_Impl
@Inject
constructor(
    private val imageService: SearchImageService,
    private val networkMapper: ImageDataDtoMapper,
    private val databaseService: DatabaseService,
) : SearchImageRepository {

    override suspend fun search(
        query: String,
        autoCorrect: Boolean
    ): Flow<PagingData<ImageData>> {
        return Pager(
            PagingConfig(pageSize = NetworkConstants.DEFAULT_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = SearchImageRemoteMediator(
                networkService = imageService,
                databaseService = databaseService,
                query = query,
                autoCorrect = autoCorrect,
                networkMapper = networkMapper
            ),
            pagingSourceFactory = {
                databaseService.imageDao().selectAll(query)
            }
        ).flow
    }
}