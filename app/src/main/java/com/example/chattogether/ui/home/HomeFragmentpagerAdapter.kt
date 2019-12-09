package com.example.chattogether.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.chattogether.ui.home.contact.ContactFragment

class HomeFragmentpagerAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> MessageFragment()
            1-> ContactFragment()
            2-> SettingsFragment()
            else ->MessageFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}