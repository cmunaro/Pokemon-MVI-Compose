package com.example.pokemon.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.pokemon.data.domainmodel.StatInfo
import com.example.pokemon.data.domainmodel.Type
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@ProvidedTypeConverter
class RoomTypeConverter(val moshi: Moshi) {
    @TypeConverter
    fun fromListStatInfo(data: List<StatInfo>): String {
        val type = Types.newParameterizedType(List::class.java, StatInfo::class.java)
        return moshi.adapter<List<StatInfo>>(type).toJson(data)
    }

    @TypeConverter
    fun toListStatInfo(json: String): List<StatInfo>? {
        val type = Types.newParameterizedType(List::class.java, StatInfo::class.java)
        return moshi.adapter<List<StatInfo>>(type).fromJson(json)
    }

    @TypeConverter
    fun fromListType(data: List<Type>): String {
        val type = Types.newParameterizedType(List::class.java, Type::class.java)
        return moshi.adapter<List<Type>>(type).toJson(data)
    }

    @TypeConverter
    fun toListType(json: String): List<Type>? {
        val type = Types.newParameterizedType(List::class.java, Type::class.java)
        return moshi.adapter<List<Type>>(type).fromJson(json)
    }
}
