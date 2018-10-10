package com.winton.gank.gank.ui

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author: winton
 * @time: 2018/10/9 下午3:40
 * @desc: Fragment 基类
 */
abstract class BaseFragment<T:ViewDataBinding>:Fragment() {

    lateinit var binding:T
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        initListener()
        initData()
        return binding.root
    }

    open fun initData() {
    }

    open fun initListener() {

    }


    /**
     * 获取布局的Id
     */
    abstract fun getLayoutId():Int

}