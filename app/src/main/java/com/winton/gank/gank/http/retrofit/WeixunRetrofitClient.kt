package com.winton.gank.gank.http.retrofit

import com.winton.gank.gank.http.BaseJDSubscriber
import com.winton.gank.gank.http.BaseWeixunSubscriber
import com.winton.gank.gank.http.ScHelper
import com.winton.gank.gank.http.api.JDApi
import com.winton.gank.gank.http.api.WeixunApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: winton
 * @time: 2018/11/1 2:47 PM
 * @desc: weixun RetrofitClient
 */
class WeixunRetrofitClient(private val okHttpClient: OkHttpClient) {
    private  var server: WeixunApi

    init {
        val client = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(WeixunApi.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        server = client.create(WeixunApi::class.java)
    }

    /*********************接口方法***********************/
    /**
     * 获取新闻列表
     */
    fun getNewsList(category:String,lastTime:Long,subscriber: BaseWeixunSubscriber){
        server.getNewsList(category,lastTime,System.currentTimeMillis()/1000)
                .compose(ScHelper.compose())
                .subscribe(subscriber)
    }

}