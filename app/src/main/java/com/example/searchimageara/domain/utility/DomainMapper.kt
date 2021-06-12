package com.example.searchimageara.domain.utility

interface DomainMapper<T,DomainModel> {
    fun mapToDomainModel(model:T) : DomainModel
    fun mapFromDomainModel(domainModel: DomainModel) : T
}