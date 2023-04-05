package com.cotesa.common.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromStringToInt(value: String?): Int? {
        return if (value != null) {
            val listType: Type = object : TypeToken<Int?>() {}.type
            return Gson().fromJson(value, listType)
        } else null
    }

    @TypeConverter
    fun fromIntToString(list: Int?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return if (value != null) {
            val listType: Type = object : TypeToken<List<String?>?>() {}.type
            return Gson().fromJson(value, listType)
        } else null
    }

    @TypeConverter
    fun fromArrayList(list: List<String?>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringtoInt(value: String?): List<Int>? {
        return if (value != null) {
            val listType: Type = object : TypeToken<List<Int?>?>() {}.type
            Gson().fromJson(value, listType)
        } else
            null
    }

    @TypeConverter
    fun fromIntstoString(list: List<Int?>?): String {
        return Gson().toJson(list)
    }

}