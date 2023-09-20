package com.shapeide.rasadesa.search

import androidx.appsearch.app.AppSearchBatchResult
import androidx.appsearch.app.SearchResult

interface SearchDataSource {
    suspend fun initialize(): Any?

    /* Search returning SearchResult based on query */
    suspend fun queryMealSearch(query: String): List<SearchResult>

    /* Add document with its own object */
    suspend fun addMealSearch(mealSearch: MealSearch): AppSearchBatchResult<String, Void>

    /* Remove data based on namespace and id */
    suspend fun removeMealSearch(
        namespace: String,
        id: String
    ): AppSearchBatchResult<String, Void>

    /* Need to be done for creating session */
    suspend fun awaitInitialization()
}