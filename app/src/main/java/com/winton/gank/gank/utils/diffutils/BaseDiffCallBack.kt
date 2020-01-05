package com.winton.gank.gank.utils.diffutils

import androidx.recyclerview.widget.DiffUtil

/**
 * @author: winton
 * @time: 2018/8/3 16:17
 * @describe: DiffUtils 异常
 */
abstract class BaseDiffCallBack<T>(var oldList:List<T>, var newList:List<T>): DiffUtil.Callback() {



    abstract override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean

    open override fun getOldListSize(): Int {
        return oldList.size
    }

    open override fun getNewListSize(): Int {
        return  newList.size
    }

    abstract override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
}