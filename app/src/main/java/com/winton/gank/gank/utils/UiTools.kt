package com.winton.gank.gank.utils

import android.content.Context
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.NonNull
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.winton.gank.gank.App
import com.winton.gank.gank.R

/**
 * @author: winton
 * @time: 2018/10/24 8:41 PM
 * @desc: 描述
 */
object UiTools {

    fun fitTitleBar(window:Window,color:Int){
        /*沉浸式代码*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN).and(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }

    fun initSwipRefresh(view: SwipeRefreshLayout){
        view.setColorSchemeColors(App.INSTANCE.getColor(R.color.swip_color_1),App.INSTANCE.getColor(R.color.swip_color_2),App.INSTANCE.getColor(R.color.swip_color_3))
    }

    fun pxToDp(@NonNull context: Context, px: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (px / scale + 0.5).toInt()
    }

    fun dpToPx(@NonNull context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5).toInt()
    }
}