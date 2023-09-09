package com.shapeide.rasadesa.ui.old.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.adapters.FavoriteAdapter
import com.shapeide.rasadesa.ui.MainActivityOld
import com.shapeide.rasadesa.ui.old.detail.DetailFragment
import com.shapeide.rasadesa.ui.old.listener.MealDetailListener
import com.shapeide.rasadesa.presenter.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var rvFavorite: RecyclerView
    private lateinit var mFavoriteAdapter: FavoriteAdapter
    private lateinit var mMealDetailListener: MealDetailListener
    private lateinit var mMainActivityOld: com.shapeide.rasadesa.ui.MainActivityOld
    private val mFavoriteViewModel: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainActivityOld = requireActivity() as com.shapeide.rasadesa.ui.MainActivityOld
        mMealDetailListener = (requireActivity() as com.shapeide.rasadesa.ui.MainActivityOld)
        mFavoriteAdapter = FavoriteAdapter(requireContext()) {  id ->
            mMealDetailListener.onDetailMeal(DetailFragment.VAL_TYPE_MEAL, id.toInt())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_favorite, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFavorite = view.findViewById(R.id.rv_favorite)
        rvFavorite.layoutManager = GridLayoutManager(view.context, 2)
        rvFavorite.adapter = mFavoriteAdapter

        mFavoriteViewModel.favoriteMealData.observe(mMainActivityOld){ meals ->
            mFavoriteAdapter.updateData(ArrayList(meals))
        }
    }
}