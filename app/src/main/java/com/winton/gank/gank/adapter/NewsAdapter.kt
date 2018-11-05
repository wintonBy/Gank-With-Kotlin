package com.winton.gank.gank.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ItemNewsListVideoBinding
import com.winton.gank.gank.http.bean.NewsContent

/**
 * @author: winton
 * @time: 2018/11/2 5:44 PM
 * @desc: 新闻适配器
 */
class NewsAdapter:RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private var mData:ArrayList<NewsContent> = ArrayList()

    private var mContext: Context

    constructor(mContext: Context) : super() {
        this.mContext = mContext
    }

    fun refresh(data:ArrayList<NewsContent>){
        mData.addAll(0,data)
        notifyDataSetChanged()
    }

    fun more(data:ArrayList<NewsContent>){
        val oldSize = mData.size
        mData.addAll(data)
        notifyItemRangeInserted(oldSize,data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemNewsListVideoBinding>(LayoutInflater.from(mContext), R.layout.item_news_list_video,parent,false)
        val holder = ViewHolder(binding)
        return holder
    }

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(mData[position])
        holder.itemView.tag = mData[position]
    }

    class ViewHolder:BaseRVHolder{
        constructor(binding: ItemNewsListVideoBinding) : super(binding.root){
            this.binding = binding
        }

        fun bindData(news:NewsContent){
            (binding as ItemNewsListVideoBinding).run {
                title.text = news.title
            }
        }
    }


}