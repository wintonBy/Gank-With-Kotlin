package com.winton.gank.gank.viewmodel

import androidx.lifecycle.MutableLiveData
import com.winton.gank.gank.http.BaseGankSubscriber
import com.winton.gank.gank.http.ErrorCode
import com.winton.gank.gank.http.RetrofitHolder
import com.winton.gank.gank.http.response.gank.TodayResponse
import com.winton.gank.gank.repository.Resource

/**
 * @author: winton
 * @time: 2018/10/17 下午3:30
 * @desc: 首页ViewModel
 */
class IndexViewModel :BaseViewModel(){

    private var indexData: MutableLiveData<Resource<TodayResponse>> = MutableLiveData()

    fun getIndexData():MutableLiveData<Resource<TodayResponse>>{
        return indexData
    }

    override fun start() {
        super.start()
        loadToday()
    }

    override fun stop() {
        super.stop()
        indexRequest.mSub?.cancel()
    }

    private fun loadToday(){
        RetrofitHolder.gankInstance().today(indexRequest)
    }

    /**
     * 首页的请求
     */
    private val indexRequest = object:BaseGankSubscriber<TodayResponse>(){

        override fun start() {
            super.start()
            indexData.value = Resource.loading(null)
        }

        override fun onSuccess(t: TodayResponse) {
            indexData.value = Resource.success(t)
        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            indexData.value = Resource.error(null,errorMsg)
        }
    }


}