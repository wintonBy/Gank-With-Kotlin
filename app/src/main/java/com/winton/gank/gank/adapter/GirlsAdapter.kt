package com.winton.gank.gank.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ItemGirlsListBinding
import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.utils.BindingUtils
import com.winton.gank.gank.utils.StringUtils
import java.util.*

/**
 * @author: winton
 * @time: 2018/10/25 7:17 PM
 * @desc: 妹子Adapter
 */
class GirlsAdapter:BaseRVAdapter<TitleBean,GirlsAdapter.ViewHolder> {

    private var mContext: Context
    private var onItemClickListener:OnItemClick? = null


    constructor(diffCallback: DiffUtil.ItemCallback<TitleBean>, mContext: Context) : super(diffCallback) {
        this.mContext = mContext
    }

    fun setOnItemClickListener(listener:OnItemClick){
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GirlsAdapter.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemGirlsListBinding>(LayoutInflater.from(mContext), R.layout.item_girls_list,parent,false)
        binding.root.setOnClickListener {
            onItemClickListener?.let {
                it.onItemClick((binding.root.tag as TitleBean))
            }
        }
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: GirlsAdapter.ViewHolder, position: Int) {
        var layoutParams = holder.binding.root.layoutParams
        layoutParams.height = getHeight(position)
        holder.bindData(getItem(position))
        holder.binding.root.tag = getItem(position)
    }

    private fun getHeight(position: Int): Int {
        return Random().nextInt(3)*100 + 600
    }

    class ViewHolder:BaseRVHolder {

        constructor(binding: ItemGirlsListBinding) : super(binding.root){
            this.binding =binding
        }
        fun bindData(titleBean: TitleBean){
            (binding as ItemGirlsListBinding).run {
                this.time.text = "发布日期："+StringUtils.getGankReadTime(titleBean.createdAt)+"\n作者："+titleBean.who
                BindingUtils.bindArticleImg(this.img,titleBean.url)
            }
        }
    }
    interface OnItemClick{
        fun onItemClick(item: TitleBean)
    }


}