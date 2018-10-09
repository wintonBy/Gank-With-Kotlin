package com.winton.gank.gank

import android.app.Application
import kotlin.properties.Delegates

/**
 * @author: winton
 * @time: 2018/10/8 下午7:41
 * @desc: Gank App
 */
class App:Application() {

    companion object {
        var INSTANCE:App by Delegates.notNull<App>()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}