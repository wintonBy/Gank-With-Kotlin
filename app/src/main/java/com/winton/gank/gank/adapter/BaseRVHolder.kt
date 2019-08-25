package com.winton.gank.gank.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author: winton
 * @time: 2018/10/17 下午6:00
 * @desc: Recycle ViewHolder
 */
open class BaseRVHolder(itemView: View, var binding:ViewDataBinding) : RecyclerView.ViewHolder(itemView) {

    open fun bind(variableId:Int,value:Any) {
        binding.setVariable(variableId,value)
        binding.executePendingBindings()
    }

}