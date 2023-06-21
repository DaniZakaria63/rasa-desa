package com.shapeide.rasadesa.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.adapters.HomeCategoryAdapter
import com.shapeide.rasadesa.adapters.HomeMealAdapter
import com.shapeide.rasadesa.databinding.FragmentHomeBinding
import com.shapeide.rasadesa.domains.FilterMeal
import com.shapeide.rasadesa.ui.activities.DetailActivity
import com.shapeide.rasadesa.ui.activities.MainActivity
import com.shapeide.rasadesa.ui.listener.MealDetailListener
import com.shapeide.rasadesa.utills.RasaApplication
import com.shapeide.rasadesa.viewmodels.HomeVM
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var mMealDetailListener: MealDetailListener
    private var mFragmentHomeBinding: FragmentHomeBinding? = null
    private lateinit var rvCategoryAdapter: HomeCategoryAdapter
    private lateinit var rvMealAdapter: HomeMealAdapter
    private val homeViewModel: HomeVM by viewModels()
    private var mealModels = ArrayList<FilterMeal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMealDetailListener = (activity as MainActivity)
        rvCategoryAdapter = HomeCategoryAdapter(requireContext(), 1) { mealName ->
            homeViewModel.syncFilterMealByMealName(mealName)
        }
        rvMealAdapter = HomeMealAdapter(requireContext(), mealModels){ id ->
            mMealDetailListener.onDetailMeal(DetailFragment.VAL_TYPE_MEAL, id.toInt())
        }

        /* It's already synchronized, while in initialized, now just need to be observed */
        homeViewModel.categoryData.observe(this) { category ->
            rvCategoryAdapter.updateCategoryList(ArrayList(category))
        }

        /* Observe the data filter-meals and update the data from adapter */
        homeViewModel.filterMealData.observe(this) { filterMeals ->
            rvMealAdapter.updateCategoryList(ArrayList(filterMeals))
        }

        homeViewModel.selectedMealCategory.observe(this) { name ->
            try {
                mFragmentHomeBinding?.tvListmeals?.text = "The Meals: $name"
            } catch (err: NullPointerException) {
                err.printStackTrace()
            }
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

    }

    override fun onDestroyView() {
        mFragmentHomeBinding = null
        homeViewModel.filterMealData.removeObservers(this)
        homeViewModel.categoryData.removeObservers(this)
        homeViewModel.selectedMealCategory.removeObservers(this)
        super.onDestroyView()
    }
}