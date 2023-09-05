package com.shapeide.rasadesa.ui.old.discover

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shapeide.rasadesa.ui.MainActivityOld
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.adapters.CountryAdapter
import com.shapeide.rasadesa.adapters.HomeCategoryAdapter
import com.shapeide.rasadesa.adapters.IngredientsAdapter
import com.shapeide.rasadesa.databinding.FragmentDiscoverBinding
import com.shapeide.rasadesa.ui.old.detail.DetailFragment
import com.shapeide.rasadesa.ui.old.listener.MealDetailListener
import com.shapeide.rasadesa.presenter.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {
    private lateinit var categoryAdapter: HomeCategoryAdapter
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private lateinit var mCallbackListener: MealDetailListener
    private var mBinding: FragmentDiscoverBinding? = null
    private val discoverViewModel : com.shapeide.rasadesa.presenter.main.viewmodel.MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCallbackListener = activity as com.shapeide.rasadesa.ui.MainActivityOld

        categoryAdapter = HomeCategoryAdapter(requireContext(), type = 2) { name ->
            mCallbackListener.onNeedIntent("c", "Category", name)
        }

        //TODO: country will be showing list of country and also the flags, onClicked will be show list of meals based on country
        countryAdapter = CountryAdapter { name ->
            mCallbackListener.onNeedIntent("a", "Area", name)
        }

        ingredientsAdapter = IngredientsAdapter{ name ->
            mCallbackListener.onNeedIntent("i", "Ingredients", name)
        }

        discoverViewModel.syncDiscovery()

        discoverViewModel.categoryData.observe(this){ categories ->
            categoryAdapter.updateCategoryList(ArrayList(categories))
        }

        discoverViewModel.areaData.observe(this){ areas ->
            countryAdapter.updateAreaList(ArrayList(areas))
        }

        discoverViewModel.ingredientData.observe(this){ ingredients ->
            ingredientsAdapter.updateIngredientList(ArrayList(ingredients))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDiscoverBinding.bind(view)
        mBinding = binding
        binding.rvBycountry.layoutManager =
            StaggeredGridLayoutManager(3, GridLayoutManager.HORIZONTAL)
        binding.rvBycountry.adapter = countryAdapter

        binding.rvBycategories.layoutManager =
            StaggeredGridLayoutManager(2, GridLayoutManager.HORIZONTAL)
        binding.rvBycategories.adapter = categoryAdapter

        binding.rvIngredients.layoutManager =
            StaggeredGridLayoutManager(5, GridLayoutManager.HORIZONTAL)
        binding.rvIngredients.adapter = ingredientsAdapter
        binding.swipeRefresh.isRefreshing = false

        /* This random meal will through the detail meal,
        *  right after entering its time to get the data
        */
        binding.btnRandomdish.setOnClickListener {
            mCallbackListener.onDetailMeal(DetailFragment.VAL_TYPE_RANDOM,0)
        }
    }

    override fun onDestroyView() {
        mBinding = null
        discoverViewModel.areaData.removeObservers(this)
        discoverViewModel.ingredientData.removeObservers(this)
        discoverViewModel.categoryData.removeObservers(this)
        super.onDestroyView()
    }

}