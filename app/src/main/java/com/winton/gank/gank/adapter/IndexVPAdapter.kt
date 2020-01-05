package com.winton.gank.gank.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author: winton
 * @time: 2018/10/22 下午8:58
 * @desc: 描述
 */
class IndexVPAdapter(fm: FragmentManager, private var fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(p0: Int) = fragments[p0]

    override fun getCount() = fragments.size
}