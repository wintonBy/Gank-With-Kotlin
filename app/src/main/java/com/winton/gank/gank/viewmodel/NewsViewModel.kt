package com.winton.gank.gank.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.winton.gank.gank.http.response.WeixunResponse
import com.winton.gank.gank.repository.Resource

/**
 * @author: winton
 * @time: 2018/11/1 5:01 PM
 * @desc: 新闻ViewModel
 */
class NewsViewModel :BaseViewModel(){

    private val mList:MutableLiveData<Resource<WeixunResponse>> = MutableLiveData()

    fun getList(){

    }

    override fun start() {
        super.start()
    }

    override fun stop() {
        super.stop()
    }
}