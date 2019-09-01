package com.winton.gank.gank.widget

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet

class AutoViewPager :ViewPager {

    var auto = true

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    override fun onPageScrolled(position: Int, offset: Float, offsetPixels: Int) {
        super.onPageScrolled(position, offset, offsetPixels)
    }


    interface AutoAdapter {
        fun needSwapPosition(): Boolean

        fun getAdapter(): PagerAdapter
    }


}