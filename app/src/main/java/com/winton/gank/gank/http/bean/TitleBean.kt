package com.winton.gank.gank.http.bean

/**
 * @author: winton
 * @time: 2018/10/9 下午5:12
 * @desc: Title bean
 */

class TitleBean(
    val _id: String,
    val createdAt: String,
    val desc: String,
    val images: List<String>?,
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
) {
    var views = 0
}