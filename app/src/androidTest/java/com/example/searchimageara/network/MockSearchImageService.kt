package com.example.searchimageara.network

import com.example.searchimageara.network.responses.SearchImageResponse
import com.example.searchimageara.network.responses.SearchImageResponseFactory

class MockSearchImageService:SearchImageService {
    override suspend fun searchImages(
            apiKey: String,
            host: String,
            q: String,
            pageNumber: Int,
            pageSize: Int,
            autoCorrect: Boolean): SearchImageResponse {

        return SearchImageResponseFactory().createSearchImageResponse(
                q,
                pageNumber,
                pageSize
        )

    }
}