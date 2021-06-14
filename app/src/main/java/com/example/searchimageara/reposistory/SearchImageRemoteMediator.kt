/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.example.searchimageara.reposistory

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
                    getImageDataKeys()
                }
            }

            val response = networkService.searchImages(
                NetworkConstants.API_VALUE,
                NetworkConstants.API_HOST_VALUE,
                query,
                pageNumber,
                pageSize,
                autoCorrect
            )
            val totalCount = response?.count
            val imageDataDto = response?.imageList
            val imageDataList = networkMapper.toDomainList(imageDataDto,query)
            if (imageDataList != null) {
                databaseService.withTransaction {
                    databaseService.imageDataKeysDao()
                        .saveImageDataKeys(ImageDataKeys(0, pageNumber++, pageSize, totalCount))

                    databaseService.imageDao().saveAllImageData(imageDataList)
                }
            }
            val maxPageNumber = totalCount / pageSize + 1
            MediatorResult.Success(endOfPaginationReached = pageNumber == maxPageNumber) //we need to calculate totalCount/pageSize max


        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }

    }

    private suspend fun getImageDataKeys(): ImageDataKeys? {
        return databaseService.imageDataKeysDao().getImageDataKeys().firstOrNull()

    }
}