package com.shapeide.rasadesa.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.data.repository.MealRepository
import com.shapeide.rasadesa.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var mBinding: FragmentDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    @Inject
    lateinit var repository: com.shapeide.rasadesa.data.repository.MealRepository
    private var mType: String = VAL_TYPE_RANDOM
    private var mIdMeal: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mType = it.getString(ARG_TYPE).toString()
            mIdMeal = it.getInt(ARG_IDMEAL)
        }

        detailViewModel = ViewModelProvider(
            this,
            DetailViewModel.DetailVMFactory(mType, mIdMeal, repository)
        ).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDetailBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.mealData.observe(viewLifecycleOwner) { meal ->
            Log.d(TAG, "onViewCreated: MealData Observer, active")
            //TODO: Put anything from view

        }
    }

    companion object {
        const val ARG_TYPE = "type"
        const val ARG_IDMEAL = "idMeal"
        const val VAL_TYPE_RANDOM = "random"
        const val VAL_TYPE_MEAL = "meal"

        @JvmStatic
        fun newInstance(type: String, idMeal: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TYPE, type)
                    putInt(ARG_IDMEAL, idMeal)
                }
            }
    }
}