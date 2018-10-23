package com.winton.gank.gank.http.response.gank

import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.http.response.BaseGankResponse

/**
 * @author: winton
 * @time: 2018/10/22 下午8:30
 * @desc: 分类
 */
class CategoryResponse:BaseGankResponse() {

    var results:ArrayList<TitleBean>? = null
}