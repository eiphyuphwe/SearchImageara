package com.example.searchimageara.network

import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.domain.model.Provider
import com.example.searchimageara.network.model.ImageDataDto
import com.example.searchimageara.network.model.ImageDataDtoMapper
import com.example.searchimageara.network.model.ProviderDto
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ImageDataDtoMapperTest {

    @Test
    fun testDtoMapper_mapToDomainModel_ReturnImageData() {
        val dtoMapper = ImageDataDtoMapper()
        val imageDataDto = ImageDataDto("url",100,100,"thumbnail",40,40,"",
        "name","title", ProviderDto(),"imageWebSearchUrl","searchUrl")
        val imageData = dtoMapper.mapToDomainModel(imageDataDto,"cats")
        assertNotNull(imageData)
        assertTrue(imageData is ImageData)
    }

    @Test
    fun testDtoMapper_mapFromDomainModel_ReturnImageDataDto(){
        val dtoMapper = ImageDataDtoMapper()
        val imageData = ImageData("url",100,100,"thumbnail",40,40,"",
            "name","title", Provider(),"imageWebSearchUrl","searchUrl","cats")
        val imageDataDto = dtoMapper.mapFromDomainModel(imageData)
        assertNotNull(imageDataDto)
        assertTrue(imageDataDto is ImageDataDto)
    }
}