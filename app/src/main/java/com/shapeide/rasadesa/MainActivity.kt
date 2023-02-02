package com.shapeide.rasadesa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.fragments.DiscoverFragment
import com.shapeide.rasadesa.utills.Resource
import com.shapeide.rasadesa.viewmodel.MainActivityVM
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), DiscoverFragment.CallbackListener {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private val layoutId: Int = R.layout.activity_main
    private val mMainActivityVM: MainActivityVM by viewModels { MainActivityVM.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)

        // TablayoutController(this, viewPager, tabLayout).setUp()

        /**
         * Test for network 2
         * based on https://medium.com/dsc-sastra-deemed-to-be-university/retrofit-with-viewmodel-in-kotlin-part-2-15f395e32424
         */

        lifecycleScope.launch {
            mMainActivityVM.getRandomMeal()
            mMainActivityVM.randomMeal.observe(this@MainActivity) { response ->
                when (response) {
                    is Resource.Success -> {
                        Log.d(TAG, "onCreate: ${response.data.toString()}")
                    }
                    is Resource.Error -> {
                        Log.e(TAG, "onCreate: ${response.message}")
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "onCreate: Just Still Loading")
                    }
                }
            }
        }

        /**
         * Network 2 has chosen
         */

    }

    override fun onNeedIntent(key: String, value: String, name: String) {
        Log.d(TAG, "onNeedIntent: {$key} and {$value} and {$name}")
        val intent = Intent(this, FilterActivity::class.java)
        with(intent) {
            putExtra("key", key)
            putExtra("value", value)
            putExtra("name", name)
        }
        startActivity(intent)
    }

    override fun onDetailMeal(idMeal: String) {
        Log.d(TAG, "onDetailMeal: $idMeal")
    }
}