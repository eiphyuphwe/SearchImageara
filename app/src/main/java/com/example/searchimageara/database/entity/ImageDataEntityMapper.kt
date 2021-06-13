package com.example.searchimageara.database.entity


import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.domain.utility.DomainMapper
/*

class ImageDataEntityMapper : DomainMapper<com.example.searchimageara.database.entity.ImageData, ImageData> {


    override fun mapToDomainModel(model: com.example.searchimageara.database.entity.ImageData): ImageData {

        return ImageData(
            url = model.url,
            height = model.height,
            width = model.width,
            thumbnail = model.thumbnail,
            thumbnailHeight = model.thumbnailHeight,
            thumbnailWidth = model.thumbnailWidth,
            base64Encoding = model.base64Encoding,
            name = model.name,
            title = model.title,
            provider = model.provider,
            imageWebSearchUrl = model.imageWebSearchUrl,
            webpageUrl = model.webpageUrl

        )
    }

    override fun mapFromDomainModel(domainModel: ImageData): com.example.searchimageara.database.entity.ImageData {
        return com.example.searchimageara.database.entity.ImageData(
            id = 0,
            url = domainModel.url,
            height = domainModel.height,
            width = domainModel.width,
            thumbnail = domainModel.thumbnail,
            thumbnailHeight = domainModel.thumbnailHeight,
            thumbnailWidth = domainModel.thumbnailWidth,
            base64Encoding = domainModel.base64Encoding,
            name = domainModel.name,
            title = domainModel.title,
            provider = domainModel.provider,
            imageWebSearchUrl = domainModel.imageWebSearchUrl,
            webpageUrl = domainModel.webpageUrl
        )
    }

    fun toDomainList(initial: List<com.example.searchimageara.database.entity.ImageData>): List<ImageData> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<ImageData>): List<com.example.searchimageara.database.entity.ImageData> {
        return initial.map { mapFromDomainModel(it) }
    }

}*/
