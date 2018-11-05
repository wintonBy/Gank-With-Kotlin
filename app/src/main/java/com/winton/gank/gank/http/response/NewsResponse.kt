package com.winton.gank.gank.http.response

import com.winton.gank.gank.http.bean.NewsContent

/**
 * @author: winton
 * @time: 2018/11/5 11:08 AM
 * @desc: 页面需要的NewsResponse
 */
data class NewsResponse(
    val data:ArrayList<NewsContent>,
    val hasMore: Boolean,
    val hasMoreToRefresh: Boolean,
    val displayInfo: String,
    val totalNumber: Int,
    val message:String

)