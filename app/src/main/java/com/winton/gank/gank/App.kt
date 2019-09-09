package com.winton.gank.gank

import android.app.Activity
import android.app.Application
import android.graphics.Typeface
import android.os.Handler
import android.support.multidex.MultiDex
import android.support.v4.graphics.TypefaceCompatUtil
import com.blankj.utilcode.util.Utils
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.winton.gank.gank.di.AppInjector
import com.winton.library.PriorityExecutor
import com.winton.librarystatue.StatueView
import com.winton.librarystatue.StatusViewFactory
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

        var typeTTF: Typeface by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        INSTANCE = this
        mUIHandler = Handler()

        typeTTF = Typeface.createFromAsset(assets, "fonts/custom.ttf")

        AppInjector.init(this)
        Utils.init(this)
        BigImageViewer.initialize(GlideImageLoader.with(this))
        StatusViewFactory.appConfig().loadingView(R.layout.layout_page_loading)

    }

    override fun activityInjector() = dispatchingAndroidInjector
}