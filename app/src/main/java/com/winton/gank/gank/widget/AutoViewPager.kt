package com.winton.gank.gank.widget

import android.content.Context
import android.os.Handler
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.widget.Scroller

class AutoViewPager :ViewPager {

    var auto = true
        set(value) {
            if (field != value) {
                field = value
                if (field) {
                    mUiHandler.sendEmptyMessageDelayed(MSG_AUTO, autoTime)
                } else {
                    mUiHandler.removeMessages(MSG_AUTO)
                }
            }
        }

    companion object {
        private const val MSG_AUTO: Int = 0x1000
    }
    private var autoTime = 5000L // 2s
    private val mFixScroller:FixScroller = FixScroller(context)
        get() = field

    private val mUiHandler = Handler {
        if (it.what == MSG_AUTO) {
            next()
            true
        }
        false
    }

    init {
        val clazz = Class.forName("android.support.v4.view.ViewPager")
        val field = clazz.getDeclaredField("mScroller")
        field.isAccessible = true
        mFixScroller.mDuration = 1200
        field.set(this, mFixScroller)
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        mUiHandler.removeMessages(MSG_AUTO)
        if (getVisibility() == View.VISIBLE) {
            if (auto && ! mUiHandler.hasMessages(MSG_AUTO)) {
                mUiHandler.sendEmptyMessageDelayed(MSG_AUTO, autoTime)
            }
        } else {
            mUiHandler.removeMessages(MSG_AUTO)
        }
    }

    interface AutoAdapter {
        fun needSwapPosition(): Boolean

        fun getAdapter(): PagerAdapter
    }

    private fun next() {
        val count = adapter?.count ?: 0
        if (count == 0) {
            return
        }
        setCurrentItem(currentItem + 1 % count, true)
        mUiHandler.sendEmptyMessageDelayed(MSG_AUTO, autoTime)
    }

    private class FixScroller
        constructor(context: Context): Scroller(context) {

        var mDuration: Int = 600

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
            super.startScroll(startX, startY, dx, dy, mDuration)
        }

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, mDuration)
        }
    }

}