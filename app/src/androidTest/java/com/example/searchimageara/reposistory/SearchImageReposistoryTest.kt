package com.example.searchimageara.reposistory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.map
import androidx.test.filters.SmallTest
import com.example.searchimageara.database.entity.DatabaseService
import com.example.searchimageara.database.entity.ImageDataDao
import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.network.SearchImageService
import com.example.searchimageara.network.model.ImageDataDtoMapper
import com.nhaarman.mockitokotlin2.doReturn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.internal.matchers.Any
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@SmallTest
@RunWith(MockitoJUnitRunner::class)
class SearchImageReposistoryTest {

    @get:Rule
    var hitRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    val mockWebServer = MockWebServer()

    @Inject
    @Named("test_db")
    lateinit var database: DatabaseService
    lateinit var imageDataDao: ImageDataDao

    @Inject
    @Named("test_network")
    lateinit var networkService: SearchImageService

    @Inject
    @Named("test_mapper")
    lateinit var dtoMapper: ImageDataDtoMapper

    lateinit var searchImageRepo: SearchImageReposistory

    @Before
    fun setup() {

        hitRule.inject()
        imageDataDao = database.imageDao()
        searchImageRepo = SerachImageReposistory_Impl(networkService, dtoMapper, database)
        mockWebServer.start()


    }

    @After
    fun tearDown() {
        database.close()
        mockWebServer.shutdown()
    }

    @Test
    fun test_searchImages_returnSuccess() = runBlocking {
        var list = mutableListOf<ImageData>()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(NetworkResponseConstantData.successResponse)
        mockWebServer.enqueue(response)
        searchImageRepo.search("test1234", true).collectLatest {
            it.map {
                list.add(it)
            }
          println(it.toString())
        }
        println(list.toString())
        assertNotNull(list)
        assertTrue(list.size > 1)

    }

}