package com.shapeide.rasadesa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.adapters.TablayoutAdapter
import com.shapeide.rasadesa.fragments.DiscoverFragment
import com.shapeide.rasadesa.utills.TablayoutController

class MainActivity : AppCompatActivity() , DiscoverFragment.CallbackListener{
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private val layoutId : Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)

        TablayoutController(this, viewPager, tabLayout).setUp()
    }

    override fun onNeedIntent(key: String, value: String, name: String) {
        Log.d(TAG, "onNeedIntent: {$key} and {$value} and {$name}")
        val intent = Intent(this, FilterActivity::class.java)
        with(intent){
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