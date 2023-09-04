package com.shapeide.rasadesa.ui.detail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shapeide.rasadesa.databinding.ActivityDetailBinding
import com.shapeide.rasadesa.ui.dialog.NetworkDialog
import com.shapeide.rasadesa.RasaApplication
import com.shapeide.rasadesa.utills.NetworkStateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var networkStateViewModel: NetworkStateViewModel
    private var mType : String = DetailFragment.VAL_TYPE_RANDOM
    private var mMealID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkStateViewModel = (application as RasaApplication).networkStateViewModel
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.extras.let {
            mType = it?.getString(DetailFragment.ARG_TYPE).toString()
            mMealID = it?.getInt(DetailFragment.ARG_IDMEAL)?: 0
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.collapsingToolbar.title = "Loading"
        binding.collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)
        binding.collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, DetailFragment.newInstance(mType, mMealID))
            .commit()
    }

    override fun onStart() {
        super.onStart()
        networkStateViewModel.networkStateLiveData.observe(this) { isConnected ->
            if (isConnected) NetworkDialog(context = this@DetailActivity).show()
        }
    }

    override fun onStop() {
        super.onStop()
        networkStateViewModel.networkStateLiveData.removeObservers(this)
    }
}