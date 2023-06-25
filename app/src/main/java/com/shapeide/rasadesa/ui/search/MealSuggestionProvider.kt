package com.shapeide.rasadesa.ui.search

import android.app.SearchManager
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.provider.BaseColumns
import android.util.Log
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.MealModel
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

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

        /*
        val buffer = StringBuffer()
        val kueri = uri.lastPathSegment.toString()
        val url = URL("http://www.themealdb.com/api/json/v1/1/search.php?s=$kueri")
        val connection = url.openConnection() as HttpURLConnection
        try {
            connection.requestMethod = "GET"
            connection.readTimeout = 10000
            connection.connectTimeout = 11000
            connection.doOutput = false
            connection.doInput = true
            connection.connect()

            val input = BufferedReader(InputStreamReader(url.openStream()))
            var line = ""
            while (input.readLine().also { line = it } != null){
                buffer.append("$line\n")
            }
            input.close()

            Log.i(TAG, "query: $buffer")
        }catch (err: Exception){
            err.printStackTrace()
        }finally {
            connection.disconnect()
        }

        meals.clear()
        meals.addAll(meals)
         */
/*
        mealSearch.meals.mapIndexed { index, meal ->
            cursor.addRow(arrayOf(index, meal.strMeal, index))
        }
 */

        return cursor
    }

    override fun getType(p0: Uri): String = "meal"

    override fun insert(p0: Uri, p1: ContentValues?): Uri = p0

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int = 1

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int = 1
}