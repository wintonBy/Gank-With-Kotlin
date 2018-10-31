package com.winton.gank.gank.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ItemJdGirlBinding
import com.winton.gank.gank.http.response.Comment
import com.winton.gank.gank.utils.BindingUtils

/**
 * @author: winton
 * @time: 2018/10/31 3:41 PM
 * @desc: 煎蛋图片适配器
 */
class JDGirlsAdapter:RecyclerView.Adapter<JDGirlsAdapter.ViewHolder> {

    private val mContext:Context
    private var mData:ArrayList<Comment>

    private var onItemClickListener:OnItemClick? = null

    constructor(mContext: Context) : super() {
        this.mContext = mContext
        mData = ArrayList()
    }

    fun setOnItemClickListener(listener:OnItemClick){
        onItemClickListener = listener
    }

    fun update(data:ArrayList<Comment>){
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun add(data:ArrayList<Comment>){
        var oldSize = mData.size
        mData.addAll(data)
        notifyItemRangeInserted(oldSize,data.size)
    }

    override fun onCreateViewHolder(parent:  ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemJdGirlBinding>(LayoutInflater.from(mContext), R.layout.item_jd_girl,parent,false)
        val holder = ViewHolder(binding)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick((holder.itemView.tag as Comment))
        }
        return holder
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindData(mData[position])
        viewHolder.itemView.tag = mData[position]
    }

    class ViewHolder:BaseRVHolder{
        constructor(binding: ViewDataBinding) : super(binding.root){
            this.binding = binding
        }

        fun bindData(item:Comment){
            (binding as ItemJdGirlBinding).apply {
                this.author.text = "作者：${item.comment_author}"
                this.picNum.text = "含${item.pics.size}图"
                BindingUtils.bindArticleImg(this.img,item.pics[0])
            }
        }
    }

    interface OnItemClick{
        fun onItemClick(item:Comment)
    }
}