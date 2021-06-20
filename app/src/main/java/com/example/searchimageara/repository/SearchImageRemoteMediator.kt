package com.example.searchimageara.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.searchimageara.database.DatabaseService
import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.domain.model.ImageDataKeys
import com.example.searchimageara.network.NetworkConstants
import com.example.searchimageara.network.SearchImageService
import com.example.searchimageara.network.model.ImageDataDtoMapper
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class SearchImageRemoteMediator @Inject constructor(
    private val networkService: SearchImageService,
    private val databaseService: DatabaseService,
    private val query: String,
    private val autoCorrect: Boolean,
    private val networkMapper: ImageDataDtoMapper
) : RemoteMediator<Int, ImageData>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageData>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    val keys = getRemoteKeys(query)
                    keys
                }
            }
            val pageNumber = loadKey?.pageNumber?.plus(1)
                ?: 1 //page number is 1 0r latest page//page number is 1 0r latest page

            val networkResponse = networkService.searchImages(
                NetworkConstants.API_VALUE,
                NetworkConstants.API_HOST_VALUE,
                query,
                pageNumber,
                NetworkConstants.DEFAULT_PAGE_SIZE,
                autoCorrect
            )
            val isEndOfList: Boolean = networkResponse.imageList.isEmpty()
            Log.e(
                TAG,
                "NetworkCount Here = ${networkResponse.count} $isEndOfList,== ${networkResponse.imageList.size}"
            )
            val imageDataDto = networkResponse.imageList
            val imageDataList = networkMapper.toDomainList(imageDataDto, query)
            //get data from network
            // Store loaded data, and next key in transaction, so that
            // they're always consistent

            databaseService.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    databaseService.remoteKeyDao().deleteByQuery(query)
                    databaseService.imageDao().deleteImageDataByQuery(query)
                }
            }

            if (imageDataList.isNotEmpty()) {
                databaseService.withTransaction {
                    databaseService.remoteKeyDao()
                        .insertOrReplaceRemoteKeys(
                            ImageDataKeys(
                                0,
                                pageNumber,
                                NetworkConstants.DEFAULT_PAGE_SIZE,
                                query,
                                networkResponse.count
                            )
                        )

                    databaseService.imageDao().saveAllImageData(imageDataList)
                }
            }

            return MediatorResult.Success(endOfPaginationReached = isEndOfList)

        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }

    }

    private suspend fun getRemoteKeys(query: String): ImageDataKeys? {
        return databaseService.remoteKeyDao().remoteKeyByQuery(query).firstOrNull()
    }

    companion object {
        val TAG = SearchImageRemoteMediator::class.simpleName
    }
}