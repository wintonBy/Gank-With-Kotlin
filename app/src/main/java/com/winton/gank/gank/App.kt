package com.winton.gank.gank

import android.app.Activity
import android.app.Application
import android.os.Handler
import com.blankj.utilcode.util.Utils
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.winton.gank.gank.di.AppInjector
import com.winton.library.PriorityExecutor
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * @author: winton
 * @time: 2018/10/8 下午7:41
 * @desc: Gank App
 */
class App:Application(),HasActivityInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    companion object {
        var INSTANCE:App by Delegates.notNull()
        /*优先级线程管理器*/
        val appExcuter:PriorityExecutor by lazy {
            PriorityExecutor(2,10,10)
        }
        /*主线程*/
        val mMainThread = Thread.currentThread()
        /*主线程ID*/
        val mMainThreadId = android.os.Process.myPid()
        var mUIHandler: Handler by Delegates.notNull()

    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        mUIHandler = Handler()
        AppInjector.init(this)
        Utils.init(this)
        BigImageViewer.initialize(GlideImageLoader.with(this))

    }

    override fun activityInjector() = dispatchingAndroidInjector
}