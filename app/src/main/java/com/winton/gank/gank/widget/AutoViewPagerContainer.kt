package com.winton.gank.gank.widget

import android.content.Context
import android.os.Build
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.winton.gank.gank.R

class AutoViewPagerContainer : FrameLayout, ViewPager.OnPageChangeListener{

    private lateinit var mViewPager: AutoViewPager
    private lateinit var mLLPointContainer: LinearLayout
    private lateinit var mAdapter: PagerAdapter

    private var mPointSize = 20
    private var mPointMargin = 5

    private val pointDrawable = let {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            resources.getDrawable(R.drawable.point_selecter,null)
        } else {
           resources.getDrawable(R.drawable.point_selecter)
        }
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    private fun initView(context: Context) {
        mViewPager = AutoViewPager(context)
        this.addView(mViewPager, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        mLLPointContainer = LinearLayout(context)
        mLLPointContainer.setVerticalGravity(LinearLayout.VERTICAL)

        val layoutParam = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParam.gravity = Gravity.BOTTOM and Gravity.CENTER_HORIZONTAL
        this.addView(mLLPointContainer, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))

        mViewPager.addOnPageChangeListener(this)
    }

    fun setAdapter(adapter: PagerAdapter) {
        mAdapter = adapter
        notifyDataSet()
    }

    fun setCurrentPositon(position: Int) {

    }

    private fun notifyDataSet() {
        val count = mAdapter.count
        mLLPointContainer.removeAllViews()
        val params = LinearLayout.LayoutParams(mPointSize, mPointSize)
        params.setMargins(mPointMargin, mPointMargin, mPointMargin, mPointMargin)
        for (i in 0 .. count) {
            val view = View(context)
            view.background = pointDrawable
            mLLPointContainer.addView(view, params)
        }
        mViewPager.adapter = mAdapter
    }

    override fun onPageScrollStateChanged(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPageSelected(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}