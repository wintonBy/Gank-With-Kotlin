package com.winton.gank.gank.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.winton.gank.gank.http.BaseWeixunSubscriber
import com.winton.gank.gank.http.ErrorCode
import com.winton.gank.gank.http.RetrofitHolder
import com.winton.gank.gank.http.response.WeixunResponse
import com.winton.gank.gank.repository.Resource

/**
 * @author: winton
 * @time: 2018/11/1 5:01 PM
 * @desc: 新闻ViewModel
 */
class NewsViewModel :BaseViewModel(){

    private var lastTime = 0L
    private val mList:MutableLiveData<Resource<WeixunResponse>> = MutableLiveData()

    fun getList() = mList


    fun loadData(){
        RetrofitHolder.wInstance().getNewsList("video",lastTime,listRequest)
    }

    private val listRequest = object :BaseWeixunSubscriber(){
        override fun start() {
            mList.value = Resource.loading(null)
        }

        override fun onSuccess(t: WeixunResponse) {
            mList.value = Resource.success(t)
        }

        override fun onFail(code: ErrorCode, msg: String) {
            mList.value = Resource.error(null,msg)
        }
    }

    override fun start() {
        super.start()
    }

    override fun stop() {
        super.stop()
        listRequest.mSub.cancel()
    }
}