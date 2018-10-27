package com.winton.gank.gank.utils.diffutils

import android.support.v7.util.DiffUtil
import com.winton.gank.gank.http.bean.TitleBean

/**
 * @author: winton
 * @time: 2018/10/25 7:46 PM
 * @desc: 描述
 */
class GankGirlsDiff:DiffUtil.ItemCallback<TitleBean>() {


    override fun areContentsTheSame(oldItem: TitleBean, newItem: TitleBean): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areItemsTheSame(oldItem: TitleBean, newItem: TitleBean): Boolean {
        return oldItem.url == newItem.url
    }

}