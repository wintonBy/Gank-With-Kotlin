package com.winton.gank.gank.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil

/**
 * @author: winton
 * @time: 2018/10/10 下午7:06
 * @desc: RecyclerView adapter 基类
 */

open abstract class BaseRVAdapter<T,V:BaseRVHolder>:ListAdapter<T,V>{

    constructor(diffCallback: DiffUtil.ItemCallback<T>) : super(diffCallback)


}