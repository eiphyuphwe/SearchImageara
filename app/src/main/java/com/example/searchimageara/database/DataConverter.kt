package com.example.searchimageara.database.entity

import androidx.room.TypeConverter
import com.example.searchimageara.domain.model.Provider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DataConverter {

    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun convertJsonStringToProvider(data: String?): Provider? {

        val itemType = object : TypeToken<Provider>() {}.type
        var provider: Provider = gson.fromJson(data, itemType)
        return provider
    }

    @TypeConverter
    @JvmStatic
    fun convertProviderToJsonString(provider: Provider): String? {
        val jsonString = gson.toJson(provider)
        return jsonString
    }

}