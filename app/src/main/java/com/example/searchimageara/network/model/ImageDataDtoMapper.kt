package com.example.searchimageara.network.model


import com.example.searchimageara.domain.model.ImageData
import com.example.searchimageara.domain.model.Provider
import com.example.searchimageara.domain.utility.DomainMapper

class ImageDataDtoMapper : DomainMapper<ImageDataDto, ImageData> {
    val providerDtoMapper = ProviderDtoMapper()
    override fun mapToDomainModel(model: ImageDataDto): ImageData {

        return ImageData(
            id = 0,
            url = model.url,
            height = model.height,
            width = model.width,
            thumbnail = model.thumbnail,
            thumbnailHeight = model.thumbnailHeight,
            thumbnailWidth = model.thumbnailWidth,
            base64Encoding = model.base64Encoding,
            name = model.name,
            title = model.title,
            provider = model.provider?.let { providerDtoMapper.mapToDomainModel(it) },
            imageWebSearchUrl = model.imageWebSearchUrl,
            webpageUrl = model.webpageUrl

        )
    }

    override fun mapFromDomainModel(domainModel: ImageData): ImageDataDto {
        return ImageDataDto(
            url = domainModel.url,
            height = domainModel.height,
            width = domainModel.width,
            thumbnail = domainModel.thumbnail,
            thumbnailHeight = domainModel.thumbnailHeight,
            thumbnailWidth = domainModel.thumbnailWidth,
            base64Encoding = domainModel.base64Encoding,
            name = domainModel.name,
            title = domainModel.title,
            provider = domainModel.provider?.let { providerDtoMapper.mapFromDomainModel(it) },
            imageWebSearchUrl = domainModel.imageWebSearchUrl,
            webpageUrl = domainModel.webpageUrl

        )
    }

    fun toDomainList(initial: List<ImageDataDto>): List<ImageData> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<ImageData>): List<ImageDataDto> {
        return initial.map { mapFromDomainModel(it) }
    }

    class ProviderDtoMapper : DomainMapper<ProviderDto, Provider> {
        override fun mapToDomainModel(model: ProviderDto): Provider {
            return Provider(
                name = model.name,
                favIcon = model.favIcon,
                favIconBase64Encoding = model.favIconBase64Encoding
            )
        }

        override fun mapFromDomainModel(domainModel: Provider): ProviderDto {
            return ProviderDto(
                name = domainModel.name,
                favIcon = domainModel.favIcon,
                favIconBase64Encoding = domainModel.favIconBase64Encoding
            )
        }

    }
}