package com.shapeide.rasadesa.networks

import com.shapeide.rasadesa.databases.category.CategoryEntity
import com.shapeide.rasadesa.databases.meals.FilterMealEntity
import com.shapeide.rasadesa.domains.Category
import com.shapeide.rasadesa.networks.models.CategoryModel
import com.shapeide.rasadesa.networks.models.FilterMealModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseCategory<T>(val categories: List<T>)

@JsonClass(generateAdapter = true)
data class ResponseMeals<T>(val meals: List<T>)

fun ResponseCategory<CategoryModel>.asDatabaseModel() : List<CategoryEntity>{
    return categories.map {
        CategoryEntity(
            id = it.idCategory,
            idCategory = it.idCategory,
            strCategory = it.strCategory,
            strCategoryThumb = it.strCategoryThumb,
            strCategoryDescription = it.strCategoryDescription
        )
    }
}

fun ResponseMeals<FilterMealModel>.asDatabaseModel() : List<FilterMealEntity>{
    return meals.map {
        FilterMealEntity(
            id = it.idMeal,
            idMeal = it.idMeal,
            strMeal = it.strMeal,
            strMealThumb = it.strMealThumb
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