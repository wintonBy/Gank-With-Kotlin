package com.winton.gank.gank.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.paddingTop
import android.R.attr.paddingBottom
import android.R.attr.maxHeight
import android.R.attr.paddingRight
import android.R.attr.paddingLeft
import android.R.attr.maxWidth
import android.text.method.TextKeyListener.clear







/**
 * @author: winton
 * @time: 2018/11/13 7:43 PM
 * @desc: 流式布局
 */
class AutoFlowLayout<T>:ViewGroup {

    /**
     * 存储所有的view
     */
    private val mAllViews:ArrayList<ArrayList<View>> = ArrayList()
    /**
     * 记录每一行的宽度
     */
    private val mWidthList:ArrayList<Int> = ArrayList()
    /**
     * 记录设置单行显示的标志
     */
    private var mIsSingleLine: Boolean = false
    /**
     * 记录每一行的最大高度
     */
    private var mLineHeight = ArrayList<Int>()
    /**
     * 记录设置最大行数量
     */
    private var mMaxLineNumbers: Int = 0
    /**
     * 记录当前行数
     */
    private var mCount: Int = 0

    /**
     * 是否还有数据没显示
     */
    private var mHasMoreData: Boolean = false
    /**
     * 子View的点击事件
     */
    private var mOnItemClickListener: OnItemClickListener? = null
    /**
     * 当前view的索引
     */
    private var mCurrentItemIndex = -1
    /**
     * 多选标志，默认支持单选
     */
    private var mIsMultiChecked: Boolean = false
    /**
     * 记录选中的View
     */
    private var mSelectedView: View? = null
    /**
     * 记录选中的View
     */
    private var mCheckedViews = ArrayList()
    /**
     * 记录展示的数量
     */
    private var mDisplayNumbers: Int = 0
    /**
     * 数据适配器
     */
    private var mAdapter: FlowAdapter<T>? = null
    /**
     * 水平方向View之间的间距
     */
    private var mHorizontalSpace: Float = 0.toFloat()
    /**
     * 竖直方向View之间的间距
     */
    private var mVerticalSpace: Float = 0.toFloat()
    /**
     * 列数
     */
    private var mColumnNumbers: Int = 0
    /**
     * 行数
     */
    private var mRowNumbers: Int = 0
    /**
     * 是否设置了网格布局
     */
    private var mIsGridMode: Boolean = false
    /**
     * 是否设置了分割线
     */
    private var mIsCutLine: Boolean = false
    /**
     * 记录分割线的宽度
     */
    private var mCutLineWidth: Float = 0F
    /**
     * 记录分割线的长度
     */
    private var mCutLineColor: Int = 0
    /**
     * 是否每行居中处理
     */
    private var mIsCenter: Boolean = false

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if(mIsGridMode){
            gridLayout(changed,l,t,r,b)
        }else{
            flowLayout(changed,l,t,r,b)
        }
    }

    private fun flowLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    private fun gridLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mCheckedViews.clear()
        mCurrentItemIndex = -1
        val sizeWidth = width
        val sizeHeight = height
        //子View的平均宽高 默认所有View宽高一致
        val tempChild = getChildAt(0)
        val lp = tempChild
                .layoutParams as ViewGroup.MarginLayoutParams
        val childAvWidth = ((sizeWidth - paddingLeft - paddingRight - mHorizontalSpace * (mColumnNumbers - 1)) / mColumnNumbers).toInt() - lp.leftMargin - lp.rightMargin
        val childAvHeight = ((sizeHeight - paddingTop - paddingBottom - mVerticalSpace * (mRowNumbers - 1)) / mRowNumbers).toInt() - lp.topMargin - lp.bottomMargin
        for (i in 0 until mRowNumbers) {
            for (j in 0 until mColumnNumbers) {
                val child = getChildAt(i * mColumnNumbers + j)
                if (child != null) {
                    mCurrentItemIndex++
                    if (child.visibility != View.GONE) {
                        setChildClickOperation(child, -1)
                        val childLeft = (paddingLeft + j * (childAvWidth + mHorizontalSpace)).toInt() + j * (lp.leftMargin + lp.rightMargin) + lp.leftMargin
                        val childTop = (paddingTop + i * (childAvHeight + mVerticalSpace)).toInt() + i * (lp.topMargin + lp.bottomMargin) + lp.topMargin
                        child.layout(childLeft, childTop, childLeft + childAvWidth, childAvHeight + childTop)
                    }
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if(mIsGridMode){
            gridMeasure(widthMeasureSpec,heightMeasureSpec)
        }else{
            flowMeasure(widthMeasureSpec,heightMeasureSpec)
        }
    }

    private fun flowMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mCount = 0
        // 获得它的父容器为它设置的测量模式和大小
        val sizeWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val sizeHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        val modeWidth = View.MeasureSpec.getMode(widthMeasureSpec)
        val modeHeight = View.MeasureSpec.getMode(heightMeasureSpec)
        // 如果是warp_content情况下，记录宽和高
        var width = 0
        var height = paddingTop + paddingBottom
        /**
         * 记录每一行的宽度，width不断取最大宽度
         */
        var lineWidth = paddingLeft + paddingRight
        /**
         * 每一行的高度，累加至height
         */
        var lineHeight = 0

        val cCount = childCount

        // 遍历每个子元素
        for (i in 0 until cCount) {
            val child = getChildAt(i)
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            // 得到child的lp
            val lp = child.layoutParams as ViewGroup.MarginLayoutParams
            // 当前子空间实际占据的宽度
            val childWidth = (child.measuredWidth + lp.leftMargin + lp.rightMargin)
            // 当前子空间实际占据的高度
            val childHeight = (child.measuredHeight + lp.topMargin + lp.bottomMargin)
            /**
             * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
             */
            if (lineWidth + childWidth > sizeWidth) {
                width = Math.max(lineWidth, childWidth)// 取最大的
                lineWidth = childWidth // 重新开启新行，开始记录
                // 叠加当前高度，
                height += lineHeight
                // 开启记录下一行的高度
                lineHeight = childHeight
                mCount++
                if (mCount >= mMaxLineNumbers) {
                    setHasMoreData(i + 1, cCount)
                    break
                }
                if (mIsSingleLine) {
                    setHasMoreData(i + 1, cCount)
                    break
                }
            } else
            // 否则累加值lineWidth,lineHeight取最大高度
            {
                lineWidth += childWidth
                lineHeight = Math.max(lineHeight, childHeight)
            }
            // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
            if (i == cCount - 1) {
                width = Math.max(width, lineWidth)
                height += lineHeight
            }
        }
        setMeasuredDimension(if (modeWidth == View.MeasureSpec.EXACTLY) sizeWidth else width,
                if (modeHeight == View.MeasureSpec.EXACTLY) sizeHeight else height)
    }

    /**
     * 判断是否还有跟多View未展示
     * @param i 当前展示的View
     * @param count 总共需要展示的View
     */
    private fun setHasMoreData(i: Int, count: Int) {
        if (i < count) {
            mHasMoreData = true
        }
    }

    private fun gridMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 获得它的父容器为它设置的测量模式和大小
        val sizeWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val sizeHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        val modeWidth = View.MeasureSpec.getMode(widthMeasureSpec)
        val modeHeight = View.MeasureSpec.getMode(heightMeasureSpec)

        //获取viewgroup的padding
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom
        //最终的宽高值
        val heightResult: Int
        val widthResult: Int
        //未设置行数 推测行数
        if (mRowNumbers == 0) {
            mRowNumbers = if (childCount % mColumnNumbers == 0)
                childCount / mColumnNumbers
            else
                childCount / mColumnNumbers + 1
        }
        var maxChildHeight = 0
        var maxWidth = 0
        var maxHeight = 0
        var maxLineWidth = 0

        //统计最大高度/最大宽度
        for (i in 0 until mRowNumbers) {
            for (j in 0 until mColumnNumbers) {
            val child = getChildAt(i * mColumnNumbers + j)
            child?.let {
                if (it.visibility != GONE) {
                    measureChild(it,widthMeasureSpec,heightMeasureSpec)
                    // 得到child的lp
                    val lp = (it.layoutParams as MarginLayoutParams)
                    maxLineWidth +=it.measuredWidth+lp.leftMargin+lp.rightMargin
                    maxChildHeight = Math.max(maxChildHeight, it.measuredHeight+lp.topMargin+lp.bottomMargin)
                }
                }

            }
            maxWidth = Math.max(maxLineWidth,maxWidth)
            maxLineWidth = 0
            maxHeight += maxChildHeight
            maxChildHeight = 0
        }
        val tempWidth = (maxWidth + mHorizontalSpace * (mColumnNumbers - 1) + paddingLeft + paddingRight).toInt()
        val tempHeight = (maxHeight + mVerticalSpace * (mRowNumbers - 1) + paddingBottom + paddingTop).toInt()
        widthResult = if(tempWidth > sizeWidth) sizeWidth else tempWidth

        heightResult = if(tempHeight >sizeHeight) sizeHeight else tempHeight

        setMeasuredDimension(if (modeWidth == View.MeasureSpec.EXACTLY) sizeWidth else widthResult,
                if (modeHeight == View.MeasureSpec.EXACTLY) sizeHeight else heightResult)

    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

}

/**
 * 流式布局适配器
 */
abstract class FlowAdapter<T>{

    private var data:ArrayList<T>

    constructor(data: ArrayList<T>) {
        this.data = data
    }

    fun getItem(position:Int):T{
        return data[position]
    }

    fun getCount() = data.size

    abstract fun getView(): View

}