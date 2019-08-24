package com.winton.gank.gank.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.winton.gank.gank.http.BaseGankSubscriber
import com.winton.gank.gank.http.ErrorCode
import com.winton.gank.gank.http.RetrofitHolder
import com.winton.gank.gank.http.response.gank.TodayResponse
import com.winton.gank.gank.repository.Resource

/**
 * @author: winton
 * @time: 2018/10/22 下午8:54
 * @desc: 今日数据
 */
class TodayViewModel :BaseViewModel(){

    private val todayData : MutableLiveData<Resource<TodayResponse>> = MutableLiveData()

    fun getTodayData():MutableLiveData<Resource<TodayResponse>>{
        return todayData
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
     * 请求今日数据
     */
    private val indexRequest = object: BaseGankSubscriber<TodayResponse>(){

        override fun start() {
            super.start()
            todayData.value = Resource.loading(null)
        }

        override fun onSuccess(t: TodayResponse) {
            todayData.value = Resource.success(t)
        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            todayData.value = Resource.error(null,errorMsg)
        }
    }
}