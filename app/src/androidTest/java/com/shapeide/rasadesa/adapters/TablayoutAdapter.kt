package com.shapeide.rasadesa.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shapeide.rasadesa.ui.old.discover.DiscoverFragment
import com.shapeide.rasadesa.ui.old.favorite.FavoriteFragment
import com.shapeide.rasadesa.ui.old.home.HomeFragment

internal class TablayoutAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int= 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> {
                com.shapeide.rasadesa.ui.old.home.HomeFragment()
            }
            1-> {
                com.shapeide.rasadesa.ui.old.discover.DiscoverFragment()
            }
            2-> {
                com.shapeide.rasadesa.ui.old.favorite.FavoriteFragment()
            }
            else -> createFragment(0)
        }
    }
}