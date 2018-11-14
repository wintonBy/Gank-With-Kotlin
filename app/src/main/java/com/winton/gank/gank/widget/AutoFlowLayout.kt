package com.winton.gank.gank.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



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

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if(mIsGridMode){
            gridMeasure(widthMeasureSpec,heightMeasureSpec)
        }else{
            flowMeasure(widthMeasureSpec,heightMeasureSpec)
        }
    }

    private fun flowMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

    }

    private fun gridMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 获得它的父容器为它设置的测量模式和大小
        val sizeWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val sizeHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        val modeWidth = View.MeasureSpec.getMode(widthMeasureSpec)
        val modeHeight = View.MeasureSpec.getMode(heightMeasureSpec)
        
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