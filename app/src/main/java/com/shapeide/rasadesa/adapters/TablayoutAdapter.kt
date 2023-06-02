package com.shapeide.rasadesa.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shapeide.rasadesa.ui.fragments.DiscoverFragment
import com.shapeide.rasadesa.ui.fragments.FavouriteFragment
import com.shapeide.rasadesa.ui.fragments.HomeFragment

internal class TablayoutAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int= 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> { HomeFragment() }
            1-> { DiscoverFragment() }
            2-> {
                FavouriteFragment() }
            else -> createFragment(0)
        }
    }
}