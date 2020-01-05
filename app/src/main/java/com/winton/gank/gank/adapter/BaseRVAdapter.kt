package com.winton.gank.gank.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


/**
 * @author: winton
 * @time: 2018/10/10 下午7:06
 * @desc: RecyclerView adapter 基类
 */

abstract class BaseRVAdapter<T,V:BaseRVHolder>: ListAdapter<T, V> {

    constructor(diffCallback: DiffUtil.ItemCallback<T>) : super(diffCallback)


}