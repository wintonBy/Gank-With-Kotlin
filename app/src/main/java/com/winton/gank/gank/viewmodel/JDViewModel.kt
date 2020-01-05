package com.winton.gank.gank.viewmodel

import androidx.lifecycle.MutableLiveData
import com.winton.gank.gank.http.BaseJDSubscriber
import com.winton.gank.gank.http.ErrorCode
import com.winton.gank.gank.http.RetrofitHolder
import com.winton.gank.gank.http.response.JDListResponse
import com.winton.gank.gank.repository.Resource

/**
 * @author: winton
 * @time: 2018/10/31 2:16 PM
 * @desc: 煎蛋的ViewModel
 */
class JDViewModel:BaseViewModel() {

    private var data:MutableLiveData<Resource<JDListResponse>> = MutableLiveData()

    fun getData(): MutableLiveData<Resource<JDListResponse>> = data

    override fun start() {
        super.start()
    }

    override fun stop() {
        super.stop()
        listRequest.mSub.cancel()
    }

    fun loadData(type:String,pageIndex:Int){
        RetrofitHolder.jdInstance().getDetail(type,pageIndex,listRequest)
    }

    private val listRequest = object :BaseJDSubscriber(){

        override fun onStart() {
            data.value = Resource.loading(null)
        }

        override fun onSuccess(response: JDListResponse) {
            data.value = Resource.success(response)
        }

        override fun onFail(code: ErrorCode, msg: String) {
            data.value = Resource.error(null,msg)
        }
    }
}