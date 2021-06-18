package com.example.searchimageara.reposistory

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.example.searchimageara.database.entity.DatabaseService
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
    private val networkMapper: ImageDataDtoMapper,
    private val databaseService: DatabaseService,
    private val query: String,
    private val autoCorrect: Boolean

) : RemoteMediator<Int, ImageData>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageData>
    ): MediatorResult {
        return try {
            Log.e("TAG", "Start of Load:${loadType.name}")
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    Log.e(TAG,"state = ${state.lastItemOrNull()}")
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    val keys = getRemoteKeys(query)
                    keys
                }
            }
            var pageNumber = 0
            pageNumber = loadKey?.pageNumber?.plus(1)
                ?: 1 //page number is 1 0r latest page//page number is 1 0r lastest page
            Log.e(TAG, "pageNumber = $pageNumber")


            val networkResponse = networkService.searchImages(
                NetworkConstants.API_VALUE,
                NetworkConstants.API_HOST_VALUE,
                query,
                pageNumber,
                NetworkConstants.DEFAULT_PAGE_SIZE,
                autoCorrect
            )
            val isEndOfList: Boolean
            if (networkResponse.imageList.size == 0) {
                isEndOfList = true
            } else {
                isEndOfList = false
            }
            Log.e(TAG,"NetworkCount Here = ${networkResponse.count} $isEndOfList,== ${networkResponse.imageList.size}")
            val imageDataDto = networkResponse?.imageList
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

            if (imageDataList != null) {
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
            val totalCountFromNetwork = networkResponse.count
            Log.e(TAG, "networkTotalCount = ${networkResponse.count}")

            var maxPageNumber:Int =0
            if (totalCountFromNetwork > 0) {
                if (totalCountFromNetwork %  NetworkConstants.DEFAULT_PAGE_SIZE != 0) {
                    maxPageNumber = totalCountFromNetwork /  NetworkConstants.DEFAULT_PAGE_SIZE + 1
                } else {
                    maxPageNumber = totalCountFromNetwork /  NetworkConstants.DEFAULT_PAGE_SIZE
                }
            }

            Log.e(TAG, " \"PageNumber 222\" $pageNumber")
            Log.e(TAG, "Max Page Number = $maxPageNumber")
            Log.e(TAG, "isEndofList = $isEndOfList")

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
        val TAG = "Search"
    }
}