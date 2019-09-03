package com.winton.gank.gank.widget

import android.content.Context
import android.os.Build
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.winton.gank.gank.R

class AutoViewPagerContainer : RelativeLayout, ViewPager.OnPageChangeListener{

    private lateinit var mViewPager: AutoViewPager
    private lateinit var mLLPointContainer: LinearLayout
    private lateinit var mAdapter: AutoViewPager.AutoAdapter

    private var mPointSize = 20
    private var mPointMargin = 5
    private var currentPosition = 1
    private var lastRealPos = 0

    private var mOnPageChangeListeners: ArrayList<ViewPager.OnPageChangeListener>? = null

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
        mViewPager.addOnPageChangeListener(this)

        mLLPointContainer = LinearLayout(context)
        mLLPointContainer.setVerticalGravity(LinearLayout.VERTICAL)
        mLLPointContainer.gravity = Gravity.CENTER

        val layoutParam = LayoutParams(LayoutParams.MATCH_PARENT, mPointSize * 2)
        layoutParam.addRule(CENTER_HORIZONTAL)
        layoutParam.addRule(ALIGN_PARENT_BOTTOM)
        this.addView(mLLPointContainer, layoutParam)

    }

    fun setAdapter(adapter: AutoViewPager.AutoAdapter) {
        mAdapter = adapter
        notifyDataSet()
        setCurrentPositon(0)
    }

    fun setCurrentPositon(position: Int) {
        if (mAdapter.needSwapPosition()) {
            mViewPager.currentItem = position + 1
        } else {
            mViewPager.currentItem = position
        }
    }

    fun addOnPageChangeListener(listener: ViewPager.OnPageChangeListener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = ArrayList()
        }
        if (mOnPageChangeListeners!!.contains(listener)) {
            return
        }
        mOnPageChangeListeners!!.add(listener)
    }

    private fun notifyDataSet() {
        val count = let { if (mAdapter.needSwapPosition()) mAdapter.getAdapter().count - 2 else mAdapter.getAdapter().count }
        mLLPointContainer.removeAllViews()
        val params = LinearLayout.LayoutParams(mPointSize, mPointSize)
        params.setMargins(mPointMargin, mPointMargin, mPointMargin, mPointMargin)
        for (i in 0 until count) {
            val view = ImageView(context)
            view.setImageDrawable(pointDrawable)
            mLLPointContainer.addView(view, params)
        }
        mViewPager.adapter = mAdapter.getAdapter()
    }

    override fun onPageScrollStateChanged(state: Int) {
        var needNotify = true
        if (mAdapter.needSwapPosition()) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (currentPosition == 0) {
                    mViewPager.setCurrentItem(mAdapter.getAdapter().count - 2, false)
                    // avoid notify twice
                    needNotify = false
                }

                if (currentPosition == mAdapter.getAdapter().count - 1) {
                    mViewPager.setCurrentItem(1, false)
                    // avoid notify twice
                    needNotify = false
                }
            }
        }
        if (needNotify) {
            mOnPageChangeListeners?.run {
                for (listener in this) {
                    listener.onPageScrollStateChanged(state)
                }
            }
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        mOnPageChangeListeners?.run {
            for (listener in this) {
                listener.onPageScrolled(realPos(position), positionOffset, positionOffsetPixels)
            }
        }
    }

    override fun onPageSelected(position: Int) {
        currentPosition = position
        mOnPageChangeListeners?.run {
            for (listener in this) {
                listener.onPageSelected(realPos(position))
            }
        }
        mLLPointContainer.getChildAt(lastRealPos).isActivated = false
        mLLPointContainer.getChildAt(realPos(position)).isActivated = true
        lastRealPos = realPos(position)
    }

    private fun realPos(pos: Int): Int {
        if (! mAdapter.needSwapPosition()) return pos
        if (pos == 0) return mAdapter.getAdapter().count - 2 - 1
        if (pos == mAdapter.getAdapter().count - 1) return 0
        return pos - 1
    }


}