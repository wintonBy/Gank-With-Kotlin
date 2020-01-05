package com.winton.gank.gank.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author: winton
 * @time: 2018/10/9 下午3:38
 * @desc: Activity 基础类
 */
abstract class BaseActivity<T: ViewDataBinding>: AppCompatActivity() {

    val TAG = this.javaClass.name
    lateinit var binding:T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPreLayout(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,getLayoutId())
        initListener()
        initData(savedInstanceState)
    }



    /**
     * 设置布局
     */
    abstract fun getLayoutId():Int

    /**
     * 调用此方法可以在初始化布局之前执行
     */
    open fun initPreLayout(savedInstanceState: Bundle?){

    }

    /**
     * 初始化监听器
     */
    open fun initListener(){

    }

    /**
     * 初始化数据
     */
    open fun initData(savedInstanceState: Bundle?){

    }

}