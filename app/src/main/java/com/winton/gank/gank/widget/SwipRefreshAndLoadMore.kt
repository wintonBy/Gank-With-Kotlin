package com.winton.gank.gank.widget

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * @author: winton
 * @time: 2018/10/25 7:14 PM
 * @desc: 自定义的SwipRefreshLayout
 */
class SwipRefreshAndLoadMore: SwipeRefreshLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
}