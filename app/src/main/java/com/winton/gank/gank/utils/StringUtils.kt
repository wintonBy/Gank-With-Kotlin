package com.winton.gank.gank.utils

/**
 * @author: winton
 * @time: 2018/10/26 3:50 PM
 * @desc: 字符串工具类
 */
object StringUtils {

    fun getGankReadTime(time:String):String {
        val result = time.replace("T", " ", true)
        val index = result.lastIndexOf(".");
        return result.substring(0, index);
    }

}