package com.winton.gank.gank.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.winton.gank.gank.R

/**
 * @author: winton
 * @time: 2018/11/8 4:59 PM
 * @desc: loadingView1
 */
class LoadingView1:View{

    private var color = Color.BLUE

    private var radius = 40F

    private var duration = 500L

    private var cx = 0F
    private var cy = 0F
    private var animSizeValue = radius

    /**
     * 画笔1
     */
    private val mPaint1:Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = this@LoadingView1.color
            alpha = 128
            style = Paint.Style.FILL
        }
    }
    /**
     * 画笔2
     */
    private val mPaint2:Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = this@LoadingView1.color
            alpha = 128
            style = Paint.Style.FILL
        }
    }
    private val sizeAnim:ValueAnimator by lazy {
        ValueAnimator.ofFloat(0F,radius).apply {
            duration = this@LoadingView1.duration
            repeatCount = -1
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener {
               animSizeValue =  (it.animatedValue as Float)
                postInvalidate()
            }
        }
    }


    constructor(context: Context?) : super(context){
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        val attrsArray = context?.obtainStyledAttributes(attrs,R.styleable.LoadingView1)
        attrsArray?.let {
            color = it.getColor(R.styleable.LoadingView1_color,color)
            duration = (it.getInteger(R.styleable.LoadingView1_duration,duration.toInt())).toLong()
            radius = it.getDimension(R.styleable.LoadingView1_radius,radius)
            it.recycle()
        }
        initView()
    }

    /**
     * 初始化工作
     */
    private fun initView() {
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            drawCircle(cx,cy,radius-animSizeValue,mPaint1)
            drawCircle(cx,cy,animSizeValue,mPaint2)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        cx = (measuredWidth /2).toFloat()
        cy = (measuredWidth /2).toFloat()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if(!sizeAnim.isRunning){
            sizeAnim.start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if(sizeAnim.isRunning){
            sizeAnim.cancel()
        }
    }

}