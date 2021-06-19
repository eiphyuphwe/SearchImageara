package com.example.searchimageara.network.responses

import com.example.searchimageara.network.model.ImageDataDtoListFactory

class SearchImageResponseFactory {
    fun createSearchImageResponse(
      query:String,
      pageNumber:Int,
      pageSize:Int

    ): SearchImageResponse {
        val count=110
        println("createSearchImageResponse")

        val response= SearchImageResponse(
              type = "type",
              count= count,
                imageList = ImageDataDtoListFactory().createImageList(
                        query=query,
                        pageNumber=pageNumber,
                        pageSize=pageSize,
                        count=count
                )
        )
        println("response is ${response}")
        return response
    }
}