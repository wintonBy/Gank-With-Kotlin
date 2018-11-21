package com.winton.gank.gank.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.winton.gank.gank.http.BaseWeixunSubscriber
import com.winton.gank.gank.http.ErrorCode
import com.winton.gank.gank.http.RetrofitHolder
import com.winton.gank.gank.http.bean.NewsContent
import com.winton.gank.gank.http.response.NewsResponse
import com.winton.gank.gank.http.response.WeixunResponse
import com.winton.gank.gank.repository.Resource

/**
 * @author: winton
 * @time: 2018/11/1 5:01 PM
 * @desc: 新闻ViewModel
 */
class NewsViewModel :BaseViewModel(){

    private var lastTime = 0L

    private var loadMoreTime = 0L
    private var firstLoadTime = 0L
    private val mList:MutableLiveData<Resource<WeixunResponse>> = MutableLiveData()
    private val mVideoList:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private val gson:Gson by lazy { Gson() }
    fun getList() = mVideoList

    /**
     * 下拉刷新
     */
    fun loadRefreshData(){
        val currentTime = System.currentTimeMillis()/1000
        lastTime = SPUtils.getInstance().getLong("video",0)
        if(lastTime == 0L ){
            firstLoadTime = currentTime
        }
        RetrofitHolder.wInstance().getNewsList("video",lastTime,currentTime,listRequest)
        lastTime = currentTime
        SPUtils.getInstance().put("video",lastTime)
    }



    /**
     * 上拉加载更多
     */
    fun loadMoreData(){
        if(loadMoreTime == 0L){
            loadMoreTime = firstLoadTime
        }
        RetrofitHolder.wInstance().getNewsList("video",loadMoreTime - 60*10,loadMoreTime,listRequest)
    }

    private val listRequest = object :BaseWeixunSubscriber(){
        override fun start() {
            mList.value = Resource.loading(null)
        }

        override fun onSuccess(t: NewsResponse) {
            mVideoList.value = Resource.success(t)
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