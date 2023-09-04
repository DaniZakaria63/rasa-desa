package com.shapeide.rasadesa.search.data.repository

import android.app.Application
import android.os.Build
import androidx.appsearch.app.*
import androidx.appsearch.localstorage.LocalStorage
import androidx.appsearch.platformstorage.PlatformStorage
import com.shapeide.rasadesa.search.data.source.SearchDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

class SearchAppManager(val context: Application): SearchDataSource {
    private val isInitialized: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val coroutineScope = CoroutineScope(Dispatchers.Default + Job())

    /*
    * The session serving all the process of database
    * one session handle all the transaction across the application
    * */
    private lateinit var appSearchSession: AppSearchSession
    private lateinit var searchSpec: SearchSpec

    init {
        coroutineScope.launch {
            appSearchSession = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PlatformStorage.createSearchSessionAsync(
                    PlatformStorage.SearchContext.Builder(context, SEARCH_DB_NAME).build()
                ).get()

            } else {
                LocalStorage.createSearchSessionAsync(
                    LocalStorage.SearchContext.Builder(context, SEARCH_DB_NAME).build()
                ).get()
            }

            /*SearchSpec will provide query result for 10 results
            * in the order they were created first*/
            searchSpec = SearchSpec.Builder()
                .setRankingStrategy(SearchSpec.RANKING_STRATEGY_CREATION_TIMESTAMP)
                .setSnippetCount(10)
                .build()

            try {
                /*The schema will have the structure as MealSearch
                * schema type in the overall database schema*/
                val schemaRequest = SetSchemaRequest.Builder()
                    .addDocumentClasses(com.shapeide.rasadesa.search.domain.MealSearch::class.java)
                    .build()
                appSearchSession.setSchemaAsync(schemaRequest).get()

                isInitialized.value = true
                awaitCancellation()
            } finally {
                appSearchSession.close()
            }
        }
    }

    override suspend fun initialize() = withContext(Dispatchers.IO) {
        return@withContext true
    }

    /* Search returning SearchResult based on query */
    override suspend fun queryMealSearch(query: String): List<SearchResult> {
        awaitInitialization()

        val searchResult = appSearchSession.search(query, searchSpec)
        return searchResult.nextPageAsync.get()
    }

    /* Add document with its own object */
    override suspend fun addMealSearch(mealSearch: com.shapeide.rasadesa.search.domain.MealSearch): AppSearchBatchResult<String, Void> {
        awaitInitialization()

        val request = PutDocumentsRequest.Builder().addDocuments(mealSearch).build()
        return appSearchSession.putAsync(request).get()
    }

    /* Remove data based on namespace and id */
    override suspend fun removeMealSearch(
        namespace: String,
        id: String
    ): AppSearchBatchResult<String, Void> {
        awaitInitialization()

        val request = RemoveByDocumentIdRequest.Builder(namespace).addIds(id).build()
        return appSearchSession.removeAsync(request).get()
    }

    /* Need to be done for creating session */
    override suspend fun awaitInitialization() {
        if (!isInitialized.value) {
            isInitialized.first { it }
        }
    }

    companion object {
        private const val SEARCH_DB_NAME = "search_meal"
    }
}