package com.winton.gank.gank.http.api

import com.winton.gank.gank.http.response.JDListResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * @author: winton
 * @time: 2018/10/31 10:13 AM
 * @desc: 描述
 */
interface JDApi {
    companion object {
        const val baseUrl = "http://i.jandan.net/"

        const val TYPE_GIRL = "jandan.get_ooxx_comments"

    }

    @GET
    fun getDetailData(
            @Url url:String,
            @Query("oxwlxojflwblxbsapi") type:String,
                      @Query("page") page :Int):Flowable<JDListResponse>
}