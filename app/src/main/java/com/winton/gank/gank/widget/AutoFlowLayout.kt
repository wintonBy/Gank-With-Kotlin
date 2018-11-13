package com.winton.gank.gank.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * @author: winton
 * @time: 2018/11/13 7:43 PM
 * @desc: 流式布局
 */
class AutoFlowLayout:ViewGroup {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

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





}