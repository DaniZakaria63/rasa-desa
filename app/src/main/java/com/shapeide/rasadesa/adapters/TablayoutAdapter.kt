package com.shapeide.rasadesa.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shapeide.rasadesa.ui.discover.DiscoverFragment
import com.shapeide.rasadesa.ui.favorite.FavoriteFragment
import com.shapeide.rasadesa.ui.home.HomeFragment

internal class TablayoutAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int= 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> { HomeFragment() }
            1-> { DiscoverFragment() }
            2-> { FavoriteFragment() }
            else -> createFragment(0)
        }
    }
}