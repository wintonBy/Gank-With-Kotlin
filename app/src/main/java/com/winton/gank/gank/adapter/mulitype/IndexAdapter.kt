package com.winton.gank.gank.adapter.mulitype

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.winton.gank.gank.BR
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.BaseRVHolder
import com.winton.gank.gank.databinding.ItemIndexArticleBinding
import com.winton.gank.gank.databinding.ItemIndexArticleEndBinding
import com.winton.gank.gank.databinding.ItemIndexArticleTitleBinding
import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.http.response.gank.ResultBean

/**
 * @author: winton
 * @time: 2018/10/17 下午6:05
 * @desc: 首页Adapter
 */

class IndexAdapter:RecyclerView.Adapter<IndexAdapter.ViewHolder>{

    companion object {
        const val T_TITLE = 1
        const val T_CONTENT = 2
        const val T_END = 3
    }


    private  var mContext:Context

    private val bindIdMap:HashMap<Int,Int> = HashMap()

    private var mData:ArrayList<IndexItem> = ArrayList()

    constructor(mContext: Context) : super() {
        this.mContext = mContext
    }

    fun updateData(orgData: ResultBean){
        fixData(orgData)
        this.notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        when(viewType){
            T_TITLE ->{
                val binding = DataBindingUtil.inflate<ItemIndexArticleTitleBinding>(layoutInflater, R.layout.item_index_article_title,parent,false)
                bindIdMap.put(T_TITLE,BR.gankBean)
                return ViewHolder(binding)
            }
            T_END ->{
                val binding = DataBindingUtil.inflate<ItemIndexArticleEndBinding>(layoutInflater,R.layout.item_index_article_end,parent,false)
                bindIdMap.put(T_END,BR.gankBean)
                return ViewHolder(binding)
            }
            else ->{
                val binding = DataBindingUtil.inflate<ItemIndexArticleBinding>(layoutInflater,R.layout.item_index_article,parent,false)
                bindIdMap.put(T_CONTENT,BR.gankBean)
                return ViewHolder(binding)
            }
        }

    }

    override fun getItemCount(): Int {
        return mData.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var variableId = bindIdMap[getItemViewType(position)]
        var item = mData[position].item
        if(item != null && variableId != null){
            holder.bind(variableId,item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mData[position].getType()
    }

    /**
     * 数据转换
     */
    private fun fixData(orgData: ResultBean){
        mData.clear()
        orgData.android?.let {
            addItems(it)
        }
        orgData.iOS?.let {
            addItems(it)
        }
        orgData.app?.let {
            addItems(it)
        }
        orgData.recommond?.let {
            addItems(it)
        }

    }

    private fun addItems(items:List<TitleBean>){
        for(i in items.indices){
            when(i){
                0 ->{
                    mData.add(IndexItem(T_TITLE,items[i]))
                }
                items.size -1 ->{
                    mData.add(IndexItem(T_END,items[i]))
                }
                else ->{
                    mData.add(IndexItem(T_CONTENT,items[i]))
                }
            }
        }
    }

    class ViewHolder:BaseRVHolder{
        constructor(binding: ViewDataBinding) : super(binding.root){
            this.binding = binding
        }

        override fun bind(variableId: Int, value: Any) {
            super.bind(variableId, value)
        }

    }
}