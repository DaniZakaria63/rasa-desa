package com.shapeide.rasadesa.room.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListDietConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromDiets(diets: List<String>) : String{
        return gson.toJson(diets)
    }

    @TypeConverter
    fun toDiets(dietsString: String): List<String> {
        val objectType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(dietsString, objectType)
    }
}