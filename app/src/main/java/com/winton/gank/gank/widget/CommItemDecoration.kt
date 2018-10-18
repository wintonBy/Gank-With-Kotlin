package com.winton.gank.gank.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import android.support.annotation.ColorInt
import android.support.v7.widget.LinearLayoutManager

/**
 * @author: winton
 * @time: 2018/10/18 下午6:15
 * @desc: 描述
 */
class CommItemDecoration private constructor(context: Context, private val mOrientation: Int, @ColorInt color: Int, space: Int) : RecyclerView.ItemDecoration() {

    private var mSpace = 1     //间隔
    private val mRect = Rect(0, 0, 0, 0)
    private val mPaint = Paint()

    init {
        if (space > 0) {
            mSpace = space
        }
        mPaint.setColor(color)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                    .layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mSpace
            mRect.set(left, top, right, bottom)
            c.drawRect(mRect, mPaint)
        }
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                    .layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + mSpace
            mRect.set(left, top, right, bottom)
            c.drawRect(mRect, mPaint)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mSpace)
        } else {
            outRect.set(0, 0, mSpace, 0)
        }
    }

    companion object {

        private val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
        private val VERTICAL_LIST = LinearLayoutManager.VERTICAL

        fun createVertical(context: Context, @ColorInt color: Int, height: Int): CommItemDecoration {
            return CommItemDecoration(context, VERTICAL_LIST, color, height)
        }

        fun createHorizontal(context: Context, @ColorInt color: Int, width: Int): CommItemDecoration {
            return CommItemDecoration(context, HORIZONTAL_LIST, color, width)
        }
    }
}