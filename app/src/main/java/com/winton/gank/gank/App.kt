package com.winton.gank.gank

import android.app.Activity
import android.app.Application
import com.winton.gank.gank.di.AppInjector
import com.winton.library.PriorityExecutor
import dagger.android.AndroidInjector
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
        /**
         * 线程管理器
         */
        val appExcuter:PriorityExecutor by lazy {
            PriorityExecutor(2,10,10)
        }

    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}