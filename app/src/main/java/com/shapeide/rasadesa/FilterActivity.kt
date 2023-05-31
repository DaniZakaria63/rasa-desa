package com.shapeide.rasadesa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shapeide.rasadesa.adapters.HomeMealAdapter
import com.shapeide.rasadesa.networks.models.CategoryModel
import com.shapeide.rasadesa.networks.models.FilterMealModel
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilterActivity : AppCompatActivity() {
    private lateinit var apiEndpoint: APIEndpoint
    private lateinit var rv_mealslist : RecyclerView
    private lateinit var tv_category_key : TextView
    private lateinit var tv_category_value : TextView
    private lateinit var mealAdapter: HomeMealAdapter
    private var mealModels = ArrayList<FilterMealModel>()
    private val layout_id : Int = R.layout.activity_filter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val key : String? = intent.getStringExtra("key")
        val value : String? = intent.getStringExtra("value")
        val name : String? = intent.getStringExtra("name")
        apiEndpoint = APIEndpoint.create()

        setContentView(layout_id)

        tv_category_key = findViewById(R.id.tv_category_key)
        tv_category_value = findViewById(R.id.tv_category_value)

        tv_category_value.text = name
        tv_category_key.text = value

        mealAdapter = HomeMealAdapter(this, mealModels)

        rv_mealslist = findViewById(R.id.rv_mealslist)
        rv_mealslist.layoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        rv_mealslist.adapter = mealAdapter

        getDataWithFilter(key.toString(), name.toString())
    }

    private fun getDataWithFilter(key: String, value: String){
        apiEndpoint.getDataWithFilter(buildMap {
            put(key, value)
        }).enqueue(object:
            Callback<ResponseMeals<FilterMealModel>>{
            override fun onResponse(
                call: Call<ResponseMeals<FilterMealModel>>,
                response: Response<ResponseMeals<FilterMealModel>>
            ) {
                if(response.isSuccessful){
                    mealModels.clear()
                    val theDatas : List<FilterMealModel>? = response.body()?.meals
                    mealModels.addAll(theDatas?.toList()!!)
                    mealAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseMeals<FilterMealModel>>, t: Throwable) {
                Log.e(BuildConfig.TAG, "onFailure, callCategoriesAPI : ", t)
            }

        })
    }
}