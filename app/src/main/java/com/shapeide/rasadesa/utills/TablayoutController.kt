package com.shapeide.rasadesa.utills

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.adapters.TablayoutAdapter

class TablayoutController(
    val fragment: FragmentActivity,
    private val viewPager: ViewPager2,
    private val tabLayout: TabLayout
) {
    fun setUp() {
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TablayoutAdapter(fragment)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val icons = listOf(
                R.drawable.ic_outline_home,
                R.drawable.ic_outline_compass,
                R.drawable.ic_outline_favorite
            )
            val teks = listOf("Home", "Discover", "Favourites")
            tab.setIcon(icons[position])
            tab.setText(teks[position])
        }.attach()

        //TODO: Add some transition or page transformer
    }
}