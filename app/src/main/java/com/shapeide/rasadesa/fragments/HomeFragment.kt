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
import com.shapeide.rasadesa.adapters.HomeCategoryAdapter
import com.shapeide.rasadesa.models.CategoryModel
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var rv_categories : RecyclerView
    private lateinit var apiEndpoint: APIEndpoint
    private lateinit var rvAdapter: HomeCategoryAdapter
    private var itemModel = ArrayList<CategoryModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiEndpoint = APIEndpoint.create()
        rvAdapter = HomeCategoryAdapter(requireContext(), itemModel)
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

        //TODO: Load Category Data From an API
        apiEndpoint.getCategories().enqueue(object : Callback<ResponseCategory<CategoryModel>> {
            override fun onResponse(
                call: Call<ResponseCategory<CategoryModel>>,
                response: Response<ResponseCategory<CategoryModel>>
            ) {
                if(response.isSuccessful){
                    itemModel.clear()
                    val theDatas : List<CategoryModel>? = response.body()?.categories
                    itemModel.addAll(theDatas?.toList()!!)
                    rvAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseCategory<CategoryModel>>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
            }

        })
    }
}