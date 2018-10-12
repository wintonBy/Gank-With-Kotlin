package com.winton.gank.gank.di

import android.app.Application
import com.winton.gank.gank.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * @author: winton
 * @time: 2018/10/10 下午7:42
 * @desc: 描述
 */

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class
        ]
)
interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application:Application):Builder

        fun build():AppComponent
    }

    fun inject(app: App)
}