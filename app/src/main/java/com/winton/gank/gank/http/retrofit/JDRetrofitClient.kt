package com.winton.gank.gank.http.retrofit

import com.winton.gank.gank.http.BaseJDSubscriber
import com.winton.gank.gank.http.ScHelper
import com.winton.gank.gank.http.api.JDApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: winton
 * @time: 2018/10/31 11:03 AM
 * @desc: 煎蛋RetrofitClient
 */
class JDRetrofitClient(private val okHttpClient: OkHttpClient) {

    private  var server:JDApi

    init {
        val client = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(JDApi.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        server = client.create(JDApi::class.java)
    }

    /*********************接口方法***********************/
    /**
     * 获取煎蛋数据
     */
    fun getDetail(type:String,page:Int,subscriber:BaseJDSubscriber ){
        server.getDetailData(JDApi.baseUrl,type,page)
                .compose(ScHelper.compose())
                .subscribe(subscriber)
    }

}