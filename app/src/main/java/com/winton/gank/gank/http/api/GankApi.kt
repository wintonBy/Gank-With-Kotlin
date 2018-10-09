package com.winton.gank.gank.http.api

import com.winton.gank.gank.http.response.gank.TodayResponse
import io.reactivex.Flowable
import retrofit2.http.GET

/**
 * @author: winton
 * @time: 2018/10/9 下午4:06
 * @desc: gank Api
 */
interface GankApi {

    companion object {
        const val baseUrl:String ="http://gank.io/api"
    }


    @GET("/today")
    fun today():Flowable<TodayResponse>

}