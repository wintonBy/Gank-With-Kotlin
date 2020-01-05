package com.winton.gank.gank.utils.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.winton.gank.gank.http.bean.PersonCenterBean

/**
 * @author: winton
 * @time: 2018/10/29 4:21 PM
 * @desc:
 */
class PersonCenterBeanDiff : DiffUtil.ItemCallback<PersonCenterBean>(){

    override fun areItemsTheSame(oldItem: PersonCenterBean, newItem: PersonCenterBean): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: PersonCenterBean, newItem: PersonCenterBean): Boolean {
        return oldItem.title == newItem.title
    }
}