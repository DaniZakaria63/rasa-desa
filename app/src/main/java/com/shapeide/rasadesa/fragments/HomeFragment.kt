package com.shapeide.rasadesa.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.adapters.HomeCategoryAdapter
import com.shapeide.rasadesa.adapters.HomeMealAdapter
import com.shapeide.rasadesa.models.CategoryModel
import com.shapeide.rasadesa.models.FilterMealModel
import com.shapeide.rasadesa.models.MealModel
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseCategory
import com.shapeide.rasadesa.networks.ResponseMeals
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(){
    private lateinit var rv_categories : RecyclerView
    private lateinit var rv_listmeals : RecyclerView
    private lateinit var rvAdapter: HomeCategoryAdapter
    private lateinit var rvMealAdapter: HomeMealAdapter
    private lateinit var apiEndpoint: APIEndpoint
    private var categoryModels = ArrayList<CategoryModel>()
    private var mealModels = ArrayList<FilterMealModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiEndpoint = APIEndpoint.create()
        rvAdapter = HomeCategoryAdapter(requireContext(), categoryModels, type = 1, HomeCategoryAdapter.OnClickListener { mealName->
            callMealsByCategoriesAPI(mealName)
        })
        rvMealAdapter = HomeMealAdapter(requireContext(), mealModels)
        //TODO: Load Saved Internal Category Data
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_categories = view.findViewById(R.id.rv_categories)
        rv_categories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_categories.adapter = rvAdapter
        
        rv_listmeals = view.findViewById(R.id.rv_listmeals)
        rv_listmeals.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_listmeals.adapter = rvMealAdapter

        callCategoriesAPI()

        callMealsByCategoriesAPI()
    }

    internal fun callCategoriesAPI(){
        apiEndpoint.getCategories().enqueue(object : Callback<ResponseCategory<CategoryModel>> {
            override fun onResponse(
                call: Call<ResponseCategory<CategoryModel>>,
                response: Response<ResponseCategory<CategoryModel>>
            ) {
                if(response.isSuccessful){
                    categoryModels.clear()
                    val theDatas : List<CategoryModel>? = response.body()?.categories
                    categoryModels.addAll(theDatas?.toList()!!)
                    rvAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseCategory<CategoryModel>>, t: Throwable) {
                Log.e(TAG, "onFailure, callCategoriesAPI : ", t)
            }

        })
    }

    internal fun callMealsByCategoriesAPI(category: String = "Beef"){
        apiEndpoint.getMealsByCategory(category).enqueue(object : Callback<ResponseMeals<FilterMealModel>>{
            override fun onResponse(
                call: Call<ResponseMeals<FilterMealModel>>,
                response: Response<ResponseMeals<FilterMealModel>>
            ) {
                if(response.isSuccessful){
                    mealModels.clear()
                    val theDatas : List<FilterMealModel>? = response.body()?.meals
                    mealModels.addAll(theDatas?.toList()!!)
                    rvMealAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseMeals<FilterMealModel>>, t: Throwable) {
                Log.e(TAG, "onFailure, callMealsByCategoriesAPI : ", t)
            }

        })
    }
}