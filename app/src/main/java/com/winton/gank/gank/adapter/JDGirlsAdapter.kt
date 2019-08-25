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
class JDGirlsAdapter constructor(private val mContext: Context): RecyclerView.Adapter<JDGirlsAdapter.ViewHolder>() {

    private val mData = ArrayList<Comment>()

    private var onItemClickListener : ((Comment) -> Unit)?  = null

    fun update(data:ArrayList<Comment>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun add(data:ArrayList<Comment>) {
        val oldSize = mData.size
        mData.addAll(data)
        notifyItemRangeInserted(oldSize,data.size)
    }

    override fun onCreateViewHolder(parent:  ViewGroup, viewType: Int): ViewHolder =
        DataBindingUtil.inflate<ItemJdGirlBinding>(LayoutInflater.from(mContext), R.layout.item_jd_girl, parent,false).run {
            return ViewHolder(this).apply {
                itemView.setOnClickListener { onItemClickListener ?. invoke((itemView.tag as Comment)) }
            }
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindData(mData[position])
        viewHolder.itemView.tag = mData[position]
    }

    class ViewHolder constructor(viewBinding : ViewDataBinding) : BaseRVHolder(viewBinding.root, viewBinding){

        fun bindData(item : Comment) = (binding as ItemJdGirlBinding).run {
                author.text = "作者：${item.comment_author}"
                picNum.text = "含${item.pics.size}图"
                BindingUtils.bindArticleImg(img, item.pics[0])
            }
    }
}