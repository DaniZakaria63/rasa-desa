package com.shapeide.rasadesa.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.adapters.HomeCategoryAdapter
import com.shapeide.rasadesa.adapters.HomeMealAdapter
import com.shapeide.rasadesa.databinding.FragmentHomeBinding
import com.shapeide.rasadesa.domains.Category
import com.shapeide.rasadesa.networks.models.CategoryModel
import com.shapeide.rasadesa.networks.models.FilterMealModel
import com.shapeide.rasadesa.utills.RasaApplication
import com.shapeide.rasadesa.viewmodels.HomeVM
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var mFragmentHomeBinding: FragmentHomeBinding? = null
    private lateinit var application: RasaApplication
    private lateinit var rvCategoryAdapter: HomeCategoryAdapter
    private lateinit var rvMealAdapter: HomeMealAdapter
    private val homeViewModel: HomeVM by viewModels {
        HomeVM.HomeFactoryVM(application)
    }
    private var categoryModels = ArrayList<Category>()
    private var mealModels = ArrayList<FilterMealModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application = requireActivity().application as RasaApplication
        rvCategoryAdapter = HomeCategoryAdapter(
            requireContext(),
            categoryModels,
            type = 1
        ) { mealName ->
            // callMealsByCategoriesAPI(mealName)
        }
        rvMealAdapter = HomeMealAdapter(requireContext(), mealModels)

        /* It's already synchronized, while in initialized, now just need to be observed */
        homeViewModel.categoryData.observe(this) { category ->
            rvCategoryAdapter.updateCategoryList(ArrayList(category))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)
        mFragmentHomeBinding = binding
        binding.rvCategories.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.adapter = rvCategoryAdapter

        binding.rvListmeals.layoutManager =
            StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
        binding.rvListmeals.adapter = rvMealAdapter

        binding.delete.setOnClickListener {
            lifecycleScope.launch { homeViewModel.deleteLocalCategory() }
        }
/*
        TODO: Observe for probability network error
        callMealsByCategoriesAPI()
 */
    }

    override fun onDestroyView() {
        mFragmentHomeBinding = null
        super.onDestroyView()
    }

    /*

    internal fun callMealsByCategoriesAPI(category: String = "Beef") {
        application.apiEndpoint.getMealsByCategory(category)
            .enqueue(object : Callback<ResponseMeals<FilterMealModel>> {
                override fun onResponse(
                    call: Call<ResponseMeals<FilterMealModel>>,
                    response: Response<ResponseMeals<FilterMealModel>>
                ) {
                    if (response.isSuccessful) {
                        mealModels.clear()
                        val theDatas: List<FilterMealModel>? = response.body()?.meals
                        mealModels.addAll(theDatas?.toList()!!)
                        rvMealAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<ResponseMeals<FilterMealModel>>, t: Throwable) {
                    Log.e(TAG, "onFailure, callMealsByCategoriesAPI : ", t)
                }

            })
    }
     */
}