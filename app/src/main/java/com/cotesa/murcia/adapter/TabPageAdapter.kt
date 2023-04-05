package com.cotesa.murcia.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cotesa.murcia.feature.home.fragment.MapFragment
import com.cotesa.murcia.feature.home.fragment.HomeFragment
import com.cotesa.murcia.feature.home.fragment.ListFragment

class TabPageAdapter(activity:FragmentActivity,private val tabCount: Int) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ListFragment()
            1 -> HomeFragment()
            else -> MapFragment()
    }
}

}