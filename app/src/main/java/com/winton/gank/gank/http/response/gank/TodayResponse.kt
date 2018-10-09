package com.winton.gank.gank.http.response.gank

import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.http.response.BaseGankResponse

/**
 * @author: winton
 * @time: 2018/10/9 下午4:58
 * @desc: Today 接口返回响应
 */
class TodayResponse:BaseGankResponse (){


    var results:Map<String,List<TitleBean>>? = null

    var category:List<String>? = null

}