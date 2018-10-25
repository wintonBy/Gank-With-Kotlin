package com.winton.gank.gank.adapter.mulitype

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.BaseRVHolder
import com.winton.gank.gank.databinding.ItemGankListManyImageBinding
import com.winton.gank.gank.databinding.ItemGankListNoImageBinding
import com.winton.gank.gank.databinding.ItemGankListOneImageBinding
import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.utils.BindingUtils

/**
 * @author: winton
 * @time: 2018/10/23 下午1:28
 * @desc: 描述
 */
class GankListAdapter:RecyclerView.Adapter<GankListAdapter.ViewHolder> {

    private var mContext:Context
    private var mData:ArrayList<IndexItem>
    private var onItemClickListener:OnItemClick? = null

    companion object {
        const val T_NO_IMG = 1
        const val T_ONE_IMG = 2
        const val T_MANY_IMG = 3
    }

    constructor(mContext: Context) : super() {
        this.mContext = mContext
        mData = ArrayList()
    }

    fun update(data:ArrayList<TitleBean>){
        this.mData.clear()
        this.mData.addAll(fixData(data))
        notifyDataSetChanged()
    }

    fun add(data: ArrayList<TitleBean>){
        var oldSize = mData.size
        this.mData.addAll(fixData(data))
        notifyItemRangeChanged(oldSize-1,data.size)
    }

    fun setItemClickListener(listener:OnItemClick){
        onItemClickListener = listener
    }

    /**
     * 数据转换
     */
    private fun fixData(mData: ArrayList<TitleBean>): ArrayList<IndexItem> {
        val result = ArrayList<IndexItem>()
        for(bean in mData){
            if(bean.images == null || bean.images.isEmpty() ){
                result.add(IndexItem(T_NO_IMG,bean))
            }else if( bean.images.size == 1){
                result.add(IndexItem(T_ONE_IMG,bean))
            }else{
                result.add(IndexItem(T_MANY_IMG,bean))
            }
        }
        return result
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        when(viewType){
            T_ONE_IMG ->{
                val binding = DataBindingUtil.inflate<ItemGankListOneImageBinding>(layoutInflater, R.layout.item_gank_list_one_image,parent,false)
                binding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding)
            }
            T_MANY_IMG ->{
                val binding = DataBindingUtil.inflate<ItemGankListManyImageBinding>(layoutInflater, R.layout.item_gank_list_many_image,parent,false)
                binding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemGankListNoImageBinding>(layoutInflater, R.layout.item_gank_list_no_image,parent,false)
                binding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding)
            }
        }

    }

    override fun getItemCount(): Int = mData.size

    override fun getItemViewType(position: Int): Int  = mData[position].getType()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position,mData[position].item!!)
        holder.binding.root.tag =mData[position]
    }

    class ViewHolder:BaseRVHolder{
        constructor(binding:ViewDataBinding) : super(binding.root){
            this.binding = binding
        }

        fun bindData(position:Int,item:TitleBean){
            when(binding){
                is ItemGankListNoImageBinding ->{
                    (binding as ItemGankListNoImageBinding).let {
                        it.title.text = item.desc
                    }
                }
                is ItemGankListOneImageBinding ->{
                    (binding as ItemGankListOneImageBinding).let {
                        it.tvTitle.text = item.desc
                        BindingUtils.bindArticleImg(it.ivImg,item.images[0])
                    }

                }
                is ItemGankListManyImageBinding ->{
                    (binding as ItemGankListManyImageBinding).let {
                        it.title.text = item.desc
                        BindingUtils.bindArticleImg(it.img1,item.images[0])
                        BindingUtils.bindArticleImg(it.img2,item.images[1])
                        if(item.images.size >= 3){
                            it.img3.visibility = View.VISIBLE
                            BindingUtils.bindArticleImg(it.img3,item.images[2])
                        }else{
                            it.img3.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    interface OnItemClick{
        fun onItemClick(item:IndexItem)
    }
}