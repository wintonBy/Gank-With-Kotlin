package com.winton.gank.gank.http.response.gank

import com.google.gson.annotations.SerializedName
import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.http.response.BaseGankResponse

/**
 * @author: winton
 * @time: 2018/10/9 下午4:58
 * @desc: Today 接口返回响应
 */
class TodayResponse:BaseGankResponse (){


    var results:ResultBean? = null

    var category:List<String>? = null



}

class ResultBean{
    @SerializedName("Android")
    var android:List<TitleBean>? = null

    @SerializedName("App")
    var app:List<TitleBean>? = null

    @SerializedName("iOS")
    var iOS:List<TitleBean>? = null

    @SerializedName("休息视频")
    var video:List<TitleBean>? = null

    @SerializedName("福利")
    var gift:List<TitleBean>? = null

    @SerializedName("瞎推荐")
    var recommond:List<TitleBean>? = null

}