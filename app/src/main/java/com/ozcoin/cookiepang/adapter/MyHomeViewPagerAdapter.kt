package com.ozcoin.cookiepang.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ozcoin.cookiepang.ui.myhome.CollectedCookieFragment
import com.ozcoin.cookiepang.ui.myhome.CreatedCookieFragment
import com.ozcoin.cookiepang.ui.myhome.QuestionsFragment

class MyHomeViewPagerAdapter(f: Fragment) : FragmentStateAdapter(f) {

    private val collectedCookieFragment = CollectedCookieFragment()
    private val createdCookieFragment = CreatedCookieFragment()
    private val questionFragment = QuestionsFragment()

    private val pageList = listOf(collectedCookieFragment, createdCookieFragment, questionFragment)

    override fun getItemCount(): Int = pageList.size

    override fun createFragment(position: Int): Fragment {
        return pageList[position]
    }
}
