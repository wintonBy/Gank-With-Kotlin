package com.winton.gank.gank.widget

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.winton.gank.gank.App

class EasyTFTextView: AppCompatTextView {

    companion object {
        var defaultTypeFace:Typeface = App.typeTTF
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        typeface = defaultTypeFace
    }
}