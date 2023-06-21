package com.shapeide.rasadesa.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.ui.activities.MainActivity
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.adapters.CountryAdapter
import com.shapeide.rasadesa.adapters.HomeCategoryAdapter
import com.shapeide.rasadesa.adapters.IngredientsAdapter
import com.shapeide.rasadesa.databinding.FragmentDiscoverBinding
import com.shapeide.rasadesa.domains.Category
import com.shapeide.rasadesa.networks.APIEndpoint
import com.shapeide.rasadesa.networks.ResponseMeals
import com.shapeide.rasadesa.networks.models.AreaModel
import com.shapeide.rasadesa.networks.models.CategoryModel
import com.shapeide.rasadesa.networks.models.IngredientsModel
import com.shapeide.rasadesa.networks.models.MealModel
import com.shapeide.rasadesa.ui.listener.MealDetailListener
import com.shapeide.rasadesa.viewmodels.DiscoverVM
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {
    private lateinit var categoryAdapter: HomeCategoryAdapter
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private lateinit var mCallbackListener: MealDetailListener
    private var mBinding: FragmentDiscoverBinding? = null
    private val discoverVM : DiscoverVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCallbackListener = activity as MainActivity

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

        discoverVM.categoryData.observe(this){ categories ->
            categoryAdapter.updateCategoryList(ArrayList(categories))
        }

        discoverVM.areaData.observe(this){ areas ->
            countryAdapter.updateAreaList(ArrayList(areas))
        }

        discoverVM.ingredientData.observe(this){ ingredients ->
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
        discoverVM.areaData.removeObservers(this)
        discoverVM.ingredientData.removeObservers(this)
        discoverVM.categoryData.removeObservers(this)
        super.onDestroyView()
    }

}