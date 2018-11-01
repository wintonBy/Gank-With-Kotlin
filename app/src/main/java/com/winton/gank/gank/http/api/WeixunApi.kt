package com.winton.gank.gank.http.api

import com.winton.gank.gank.http.response.WeixunResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author: winton
 * @time: 2018/11/1 2:01 PM
 * @desc: weixun API
 */
interface WeixunApi {

    companion object {
        const val baseUrl = "http://is.snssdk.com/"
        const val GET_ARTICLE_LIST = "api/news/feed/v62/?refer=1&count=20&loc_mode=4&device_id=34960436458&iid=13136511752"
    }

    @GET(GET_ARTICLE_LIST)
    fun getNewsList(@Query("category")category:String,
                    @Query("min_behot_time")lastTime:Long,
                    @Query("last_refresh_sub_entrance_interval")currentTime:Long):Flowable<WeixunResponse>

}