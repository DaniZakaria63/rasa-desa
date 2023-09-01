package com.shapeide.rasadesa.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.adapters.HomeCategoryAdapter
import com.shapeide.rasadesa.adapters.HomeMealAdapter
import com.shapeide.rasadesa.databinding.FragmentHomeBinding
import com.shapeide.rasadesa.ui.main.MainActivity
import com.shapeide.rasadesa.ui.detail.DetailFragment
import com.shapeide.rasadesa.ui.listener.HomeSearchListener
import com.shapeide.rasadesa.ui.listener.MealDetailListener
import com.shapeide.rasadesa.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home){
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rvCategoryAdapter: HomeCategoryAdapter
    private lateinit var rvMealAdapter: HomeMealAdapter
    private lateinit var searchListener: HomeSearchListener
    private lateinit var mMealDetailListener: MealDetailListener
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMealDetailListener = (activity as MainActivity)
        searchListener = (activity as MainActivity)
        rvCategoryAdapter = HomeCategoryAdapter(requireContext(), 1) { mealName ->
            mainViewModel.syncFilterMealByMealName(mealName)
        }
        rvMealAdapter = HomeMealAdapter(requireContext()){ id ->
            mMealDetailListener.onDetailMeal(DetailFragment.VAL_TYPE_MEAL, id.toInt())
        }

        /* It's already synchronized, while in initialized, now just need to be observed */
        mainViewModel.categoryData.observe(this) { category ->
            rvCategoryAdapter.updateCategoryList(ArrayList(category))
        }

        /* Observe the data filter-meals and update the data from adapter */
        mainViewModel.filterMealData.observe(this) { filterMeals ->
            rvMealAdapter.updateCategoryList(ArrayList(filterMeals))
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        binding.rvCategories.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.adapter = rvCategoryAdapter

        binding.rvListmeals.layoutManager =
            StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
        binding.rvListmeals.adapter = rvMealAdapter

        binding.divSearch.setOnClickListener {
            searchListener.onSearchClicked()
        }

        mainViewModel.selectedMealCategory.observe(viewLifecycleOwner) { name ->
            try {
                binding.tvListmeals.text = "The Meals: $name"
            } catch (err: NullPointerException) {
                err.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        mainViewModel.filterMealData.removeObservers(this)
        mainViewModel.categoryData.removeObservers(this)
        mainViewModel.selectedMealCategory.removeObservers(this)
        super.onDestroyView()
    }
}