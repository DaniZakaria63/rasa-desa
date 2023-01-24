package com.shapeide.rasadesa.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.adapters.CountryAdapter
import com.shapeide.rasadesa.adapters.HomeCategoryAdapter
import com.shapeide.rasadesa.adapters.IngredientsAdapter
import com.shapeide.rasadesa.models.*
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscoverFragment : Fragment() {
    private lateinit var apiEndpoint: APIEndpoint
    private lateinit var rv_bycountry : RecyclerView
    private lateinit var rv_bycategories : RecyclerView
    private lateinit var rv_ingredients : RecyclerView
    private lateinit var categoryAdapter : HomeCategoryAdapter
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private var areaModel = ArrayList<AreaModel>()
    private var categoryModel = ArrayList<CategoryModel>()
    private var ingredientsModel = ArrayList<IngredientsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: Load Saved Internal Category, Country, and Ingredients Data

        apiEndpoint = APIEndpoint.create()

        //TODO: category will be showing list of categories, same as home fragment, onClicked will be shown list of meals based on categories
        categoryAdapter = HomeCategoryAdapter(requireContext(), categoryModel, 2)

        //TODO: country will be showing list of country and also the flags, onClicked will be show list of meals based on country
        countryAdapter = CountryAdapter(areaModel)

        //TODO: ingredients will be showing list of ingredients, onClicked will be show list of meals that include the ingredients
        ingredientsAdapter = IngredientsAdapter(ingredientsModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding(view)

        getDataArea()
        getDataCategories()
        getDataIngredients()
    }

    private fun getDataArea(){
        apiEndpoint.getArea("list").enqueue(object : Callback<ResponseMeals<AreaModel>>{
            override fun onResponse(
                call: Call<ResponseMeals<AreaModel>>,
                response: Response<ResponseMeals<AreaModel>>
            ) {
                if(response.isSuccessful){
                    areaModel.clear()
                    val theDatas : List<AreaModel>? = response.body()?.meals
                    areaModel.addAll(theDatas?.toList()!!)
                    countryAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseMeals<AreaModel>>, t: Throwable) {
                Log.e(TAG, "onFailure: getDataArea", t)
            }

        })
    }

    private fun getDataCategories(){
        apiEndpoint.getCategories("list").enqueue(object : Callback<ResponseMeals<CategoryModel>>{
            override fun onResponse(
                call: Call<ResponseMeals<CategoryModel>>,
                response: Response<ResponseMeals<CategoryModel>>
            ) {
                if(response.isSuccessful){
                    categoryModel.clear()
                    val theDatas : List<CategoryModel>? = response.body()?.meals
                    categoryModel.addAll(theDatas?.toList()!!)
                    categoryAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseMeals<CategoryModel>>, t: Throwable) {
                Log.e(TAG, "onFailure: getDataArea", t)
            }

        })
    }

    private fun getDataIngredients(){
        apiEndpoint.getIngredients("list").enqueue(object: Callback<ResponseMeals<IngredientsModel>>{
            override fun onResponse(
                call: Call<ResponseMeals<IngredientsModel>>,
                response: Response<ResponseMeals<IngredientsModel>>
            ) {
                if(response.isSuccessful){
                    ingredientsModel.clear()
                    val theDatas : List<IngredientsModel>? = response.body()?.meals
                    ingredientsModel.addAll(theDatas?.toList()!!)
                    ingredientsAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseMeals<IngredientsModel>>, t: Throwable) {
                Log.e(TAG, "onFailure: getDataArea", t)
            }

        })
    }

    private fun binding(view : View){
        rv_bycountry = view.findViewById(R.id.rv_bycountry)
        rv_bycountry.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_bycountry.adapter = countryAdapter

        rv_bycategories = view.findViewById(R.id.rv_bycategories)
        rv_bycategories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_bycategories.adapter = categoryAdapter

        rv_ingredients = view.findViewById(R.id.rv_ingredients)
        rv_ingredients.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_ingredients.adapter = ingredientsAdapter
    }
}