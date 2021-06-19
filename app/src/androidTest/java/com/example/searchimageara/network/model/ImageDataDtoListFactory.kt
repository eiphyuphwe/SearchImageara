package com.example.searchimageara.network.model

class ImageDataDtoListFactory {
    fun createImageList(
            query: String,
            pageNumber: Int,
            pageSize: Int,
            count:Int
    ): List<ImageDataDto> {

        if(query.isEmpty() ||
            query=="testEmptySearch"){
            return emptyList()
        }
        val startIndex= calcStartIndex(pageNumber,pageSize)
        var endIndex=calcEndIndex(pageNumber,pageSize)
        val list=generateData(query,count)
        if(list.size < endIndex){
            endIndex= list.size
        }
        return list.subList(startIndex,endIndex)
    }

    private fun calcStartIndex(
            pageNumber: Int,
            pageSize: Int): Int {
       return (pageNumber-1)*pageSize
    }

    private fun calcEndIndex(
            pageNumber: Int,
            pageSize: Int): Int {
        return (pageNumber*pageSize)
    }

    fun generateData(
            query: String,
            count: Int
    ): List<ImageDataDto> {
        val list = mutableListOf<ImageDataDto>()
        for (i in 1..count) {
            val imageDataDto = ImageDataDto(
                    "url $query$i",
                    100,
                    100,
                    "thumbnail",
                    40,
                    40,
                    "",
                    "name$i",
                    "title$i",
                    ProviderDto(),
                    "imageWebSearchUrl$i",
                    "searchUrl$i"
            )
            list.add(imageDataDto)
        }
        return list
    }
}