package com.example.searchimageara.network

import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SearchImageServiceTest {

    val mockWebServer = MockWebServer()
    lateinit var networkService : SearchImageService

    @Before
    fun setUp() {
        mockWebServer.start()
         networkService = Retrofit.Builder()
            .baseUrl(NetworkConstants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(SearchImageService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
     fun testNetworkService_ValidHeaderInputs_ReturnValidObject() = runBlocking {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(NetworkResponseConstant.successResponse)
        mockWebServer.enqueue(response)
        val networkResponse = networkService.searchImages(
            NetworkConstants.API_VALUE, NetworkConstants.API_HOST_VALUE,
            "cats", 1, 10, true
        )
        assertNotNull(networkResponse.imageList)
    }

    @Test
    fun testNetworkService_InvalidAPIRequest_ThrowsException() = runBlocking {
        val excepted_exception = "HTTP 403 Forbidden"
        val response = MockResponse()
            .setResponseCode(403)
            .setBody(excepted_exception)
        mockWebServer.enqueue(response)

        try {
            val networkResponse = networkService.searchImages(
                "WrongValue", NetworkConstants.API_HOST_VALUE,
                "cats", 1, 10, true
            )
        }catch (e:Exception){
            assertEquals(excepted_exception,e.message)
        }

    }


}