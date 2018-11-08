package com.winton.gank.gank.http.bean

import android.content.Intent

/**
 * @author: winton
 * @time: 2018/10/29 3:42 PM
 * @desc: 个人中心Bean
 */
data class PersonCenterBean(
        val title:String
        ):Comparable<PersonCenterBean>{
    var iconId:Int = -1
    var type:BeanType = BeanType.URL
    var url:String = ""
    var intent: Intent? = null
    var order = -1

    override fun compareTo(other: PersonCenterBean): Int {
        return this.order - other.order
    }
}

enum class BeanType{
    URL,
    ACTIVITY
}




