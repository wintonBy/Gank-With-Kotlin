package com.winton.gank.gank.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.winton.gank.gank.ui.BaseFragment

/**
 * @author: winton
 * @time: 2018/10/22 下午8:58
 * @desc: 描述
 */
class IndexVPAdapter(fm: FragmentManager?, private var fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(p0: Int) = fragments[p0]

    override fun getCount() = fragments.size
}