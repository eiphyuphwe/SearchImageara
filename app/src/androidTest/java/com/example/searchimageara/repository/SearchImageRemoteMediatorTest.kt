package com.example.searchimageara.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.searchimageara.database.RemoteKeyDao
import com.example.searchimageara.database.DatabaseService
import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.network.MockSearchImageService
import com.example.searchimageara.network.model.ImageDataDtoMapper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named


@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@SmallTest
class SearchImageRemoteMediatorTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Inject
    @Named("test_db")
    lateinit var database: DatabaseService
    lateinit var remoteKeyDao: RemoteKeyDao
    lateinit var searchImageRepo: SearchImageRepository
    lateinit var networkService:MockSearchImageService
    @Inject
    @Named("test_mapper")
    lateinit var dtoMapper: ImageDataDtoMapper

    @Before
    fun setup() {
        hiltRule.inject()
        networkService = MockSearchImageService()
        searchImageRepo = SearchImageRepository_Impl(networkService, dtoMapper, database)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        // Add mock results for the API to return.

        val remoteMediator = SearchImageRemoteMediator(
                databaseService = database,
                networkService = networkService,
                networkMapper = dtoMapper,
                query = "cats",
                autoCorrect = true
        )
        val pagingState = PagingState<Int, ImageData>(
                listOf(),
                null,
                PagingConfig(10),
                10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue (result is RemoteMediator.MediatorResult.Success )
        assertFalse ((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runBlocking {
        // To test endOfPaginationReached, don't set up the mockApi to return post
        // data here.
        val remoteMediator = SearchImageRemoteMediator(
            databaseService = database,
            networkService = networkService,
            networkMapper = dtoMapper,
            query = "testEmptySearch",
            autoCorrect = true
        )
        val pagingState = PagingState<Int, ImageData>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue (result is RemoteMediator.MediatorResult.Success )
        assertTrue ( (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached )
    }
}