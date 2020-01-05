package com.winton.gank.gank.viewmodel

import androidx.lifecycle.MutableLiveData
import com.winton.gank.gank.http.BaseGankSubscriber
import com.winton.gank.gank.http.ErrorCode
import com.winton.gank.gank.http.RetrofitHolder
import com.winton.gank.gank.http.response.gank.CategoryResponse
import com.winton.gank.gank.repository.Resource

/**
 * @author: winton
 * @time: 2018/10/23 上午11:43
 * @desc: 描述
 */
class GankListViewModel:BaseViewModel() {

    private var listData: MutableLiveData<Resource<CategoryResponse>> = MutableLiveData()


    fun getListData():MutableLiveData<Resource<CategoryResponse>> = listData


    override fun stop() {
        super.stop()
        listRequest.mSub?.cancel()
    }


    fun loadData(category:String,pageIndex:Int){
        RetrofitHolder.gankInstance().category(category,pageIndex,listRequest)
    }

    private val listRequest = object :BaseGankSubscriber<CategoryResponse>(){
        override fun start() {
            super.start()
            listData.value = Resource.loading(null)
        }

        override fun onSuccess(t: CategoryResponse) {
            listData.value = Resource.success(t)
        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            listData.value = Resource.error(null,errorMsg)
        }
    }
}