package com.example.searchimageara.database.entity


import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.domain.model.Provider
import com.example.searchimageara.domain.utility.DomainMapper
import com.example.searchimageara.network.model.ImageDataDto

class ImageDataEntityMapper : DomainMapper<ImageDataEntity, ImageData> {


    override fun mapToDomainModel(model: ImageDataEntity): ImageData {

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

    override fun mapFromDomainModel(domainModel: ImageData): ImageDataEntity {
        return ImageDataEntity(
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

    fun toDomainList(initial: List<ImageDataEntity>): List<ImageData> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<ImageData>): List<ImageDataEntity> {
        return initial.map { mapFromDomainModel(it) }
    }

}