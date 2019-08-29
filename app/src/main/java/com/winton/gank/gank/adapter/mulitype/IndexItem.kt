package com.winton.gank.gank.adapter.mulitype

import com.winton.gank.gank.http.bean.TitleBean

/**
 * @author: winton
 * @time: 2018/10/18 上午10:52
 * @desc: 描述
 */
class IndexItem :MutilItemType {

    private val itemType:Int

    var item:TitleBean? = null

    var head:ArrayList<TitleBean>? = null

    constructor(itemType: Int) {
        this.itemType = itemType
    }

    constructor(itemType: Int,item: TitleBean) {
        this.item = item
        this.itemType = itemType
    }

    constructor(itemType: Int, head: ArrayList<TitleBean>) {
        this.itemType = itemType
        this.head = head
    }

    override fun getType() = itemType
}