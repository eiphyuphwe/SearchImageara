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
    private var pageNumber: Int,
    private val pageSize: Int,
    private val autoCorrect: Boolean

) : RemoteMediator<Int, ImageData>() {
    var totalCount = 0
    var maxPageNumber = 0
    var networkTotalCount = 0;
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
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    val keys = getImageDataKeys()
                    keys?.let {

                        if (networkTotalCount > 0) {
                            if (networkTotalCount % keys.pageSize != 0) {
                                maxPageNumber = networkTotalCount / keys.pageSize + 1
                            } else {
                                maxPageNumber = networkTotalCount / keys.pageSize
                            }
                        }

                        Log.e("TAG", "Page Number=${keys.pageNumber}")
                        Log.e("TAG", "maxPageNumber=$maxPageNumber")
                        if (keys.pageNumber > maxPageNumber) {
                            Log.e("TAG", "page number>maxPageNumber")
                            return MediatorResult.Success(endOfPaginationReached = true)
                        } else {
                            Log.e("TAG", "page number<=maxPageNumber")
                        }
                    }
                    keys
                }
            }

            pageNumber = loadKey?.pageNumber ?: 1 //page number is 1 0r lastest page
            Log.e(TAG, "pageNumber 1111 = $pageNumber")


            var imageDataList = databaseService.imageDao().selectAllByQuery(query)
            Log.e(TAG, "dbsize = ${imageDataList.size}")

            if (imageDataList.size <= 0 || networkTotalCount > imageDataList.size) //for testing purpose
            {
                val networkResponse = networkService.searchImages(
                    NetworkConstants.API_VALUE,
                    NetworkConstants.API_HOST_VALUE,
                    query,
                    pageNumber,
                    pageSize,
                    autoCorrect
                )

                val imageDataDto = networkResponse?.imageList
                imageDataList = networkMapper.toDomainList(imageDataDto, query)
                if (pageNumber == 1) {
                    totalCount = networkResponse.count
                    networkTotalCount = networkResponse.count
                    Log.e(TAG, "NETWORK TOTAL COUNT $networkTotalCount , pageNumber = $pageNumber")
                    networkTotalCount = 100
                } else {
                    Log.e(
                        TAG,
                        "NETWORK TOTAL COUNT ${networkResponse.count} , pageNumber = $pageNumber"
                    )
                }
                //get data from network
                // Store loaded data, and next key in transaction, so that
                // they're always consistent
                /*databaseService.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        databaseService.imageDataKeysDao().deleteByQuery(query)
                        databaseService.imageDao().deleteImageDataByQuery(query)

                    }
                }*/

                if (imageDataList != null) {
                    databaseService.withTransaction {
                        databaseService.imageDataKeysDao()
                            .saveImageDataKeys(
                                ImageDataKeys(
                                    0,
                                    pageNumber + 1,
                                    pageSize,
                                    query,
                                    totalCount
                                )
                            )

                        databaseService.imageDao().saveAllImageData(imageDataList)
                    }
                }
            } else {
                Log.e(TAG, "I am db no need to do")
            }
            Log.e(TAG, "networkTotalCount = $networkTotalCount")

            /*if(loadKey!=null && loadKey?.totalCount > 0)
            {*/
            if (networkTotalCount > 0) {
                if (networkTotalCount % pageSize != 0) {
                    maxPageNumber = networkTotalCount / pageSize + 1
                } else {
                    maxPageNumber = networkTotalCount / pageSize
                }
            }
            //  }
            Log.e(TAG, " \"PageNumber 222\" $pageNumber")
            Log.e(TAG, "Max Page Number = $maxPageNumber")
            if (pageNumber >= maxPageNumber) {
                Log.e(TAG, "pageNumber>=maxPageNumber $pageNumber >= $maxPageNumber")
            } else {
                Log.e(TAG, "pageNumber<maxPageNumber $pageNumber < $maxPageNumber")
            }
            MediatorResult.Success(endOfPaginationReached = pageNumber >= maxPageNumber) //we need to calculate totalCount/pageSize max


        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }

    }

    private suspend fun getImageDataKeys(): ImageDataKeys? {
        return databaseService.imageDataKeysDao().getImageDataKeys(query).firstOrNull()

    }

    companion object {
        val TAG = "Search"
    }
}