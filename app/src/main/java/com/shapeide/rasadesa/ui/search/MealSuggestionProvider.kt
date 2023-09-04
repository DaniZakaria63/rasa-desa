package com.shapeide.rasadesa.ui.search

import android.app.SearchManager
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.provider.BaseColumns

class MealSuggestionProvider : ContentProvider() {
    private val meals = ArrayList<String>()
//    lateinit var apiEndpoint : APIEndpoint

    override fun onCreate(): Boolean {
//        apiEndpoint = APIEndpoint.create()
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        val cursor: MatrixCursor = MatrixCursor(arrayOf(
            BaseColumns._ID,
            SearchManager.SUGGEST_COLUMN_TEXT_1,
            SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
        ))

        /* Retrofit ways
        val response = apiEndpoint.getSearchMeal(uri.lastPathSegment.toString())
        val mealSearch : ResponseMeals<MealModel>? = response.body()
        if(!response.isSuccessful) return cursor
        if(mealSearch?.meals == null) return cursor
         */

        /* Basic Http method
        val response = StringBuffer()
        val kueri = uri.lastPathSegment.toString()
        val url = URL("http://www.themealdb.com/api/json/v1/1/search.php?s=$kueri")
        val connection = url.openConnection() as HttpURLConnection
        connection.apply {
            requestMethod = "GET"
            readTimeout = 10000
            connectTimeout = 11000
            setRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.3 Mobile/15E148 Safari/604.1\n")
        }

        try {
            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            reader.use {
                var line: String? = it.readLine()
                while (line != null){
                    response.append("$line\n")
                }
            }
            reader.close()

            Log.i(TAG, "query: $response")
        }catch (err: Exception){
            err.printStackTrace()
        }finally {
            connection.disconnect()
        }
         */

/*
        meals.clear()
        meals.addAll(meals)
        mealSearch.meals.mapIndexed { index, meal ->
            cursor.addRow(arrayOf(index, meal.strMeal, index))
        }
 */

//        cursor.addRow(arrayOf(0, uri.lastPathSegment.toString(), 0))
        return cursor
    }

    override fun getType(p0: Uri): String = "meal"

    override fun insert(p0: Uri, p1: ContentValues?): Uri = p0

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int = 1

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int = 1
}