package com.winton.gank.gank.http.retrofit

import com.google.gson.Gson
import com.winton.gank.gank.http.BaseJDSubscriber
import com.winton.gank.gank.http.BaseWeixunSubscriber
import com.winton.gank.gank.http.ScHelper
import com.winton.gank.gank.http.api.JDApi
import com.winton.gank.gank.http.api.WeixunApi
import com.winton.gank.gank.http.bean.NewsContent
import com.winton.gank.gank.http.response.NewsResponse
import com.winton.gank.gank.http.response.WeixunResponse
import io.reactivex.functions.Function
import okhttp3.OkHttpClient
import org.reactivestreams.Publisher
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
    private val gson:Gson by lazy { Gson() }

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
                .map {
                    val list:ArrayList<NewsContent> = ArrayList()
                    for(data in it.data){
                        val sData = gson.fromJson<NewsContent>(data.content,NewsContent::class.java)
                        list.add(sData)
                    }
                     NewsResponse(list,it.has_more,it.has_more_to_refresh,it.tips.display_info,it.total_number,it.message)
                }
                .compose(ScHelper.compose())
                .subscribe(subscriber)
    }

}