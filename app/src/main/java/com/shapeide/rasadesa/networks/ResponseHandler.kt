package com.shapeide.rasadesa.networks

import com.shapeide.rasadesa.domain.Category
import com.shapeide.rasadesa.local.entity.CategoryEntity
import com.shapeide.rasadesa.networks.models.CategoryModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseCategory<T>(val categories: List<T>)

@JsonClass(generateAdapter = true)
data class ResponseMeals<T>(val meals: List<T>)

fun ResponseCategory<CategoryModel>.asDatabaseModel() : List<CategoryEntity>{
    return categories.map {
        CategoryEntity(
            idCategory = it.idCategory,
            strCategory = it.strCategory,
            strCategoryThumb = it.strCategoryThumb,
            strCategoryDescription = it.strCategoryDescription
        )
    }
}

fun ResponseCategory<CategoryModel>.asDomainModel() : List<Category>{
    return categories.map {
        Category(
            id = it.idCategory,
            name = it.strCategory,
            thumb = it.strCategoryThumb,
            description = it.strCategoryDescription
        )
    }
}