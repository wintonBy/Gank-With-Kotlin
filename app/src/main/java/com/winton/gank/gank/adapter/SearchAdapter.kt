package com.winton.gank.gank.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.winton.gank.gank.BR
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ItemGankSearchBinding
import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.utils.StringUtils

/**
 * @author: winton
 * @time: 2018/11/13 8:19 PM
 * @desc: 搜索适配器
 */
class SearchAdapter:RecyclerView.Adapter<BaseRVHolder> {

    private var mData:ArrayList<TitleBean> = ArrayList()
    private var mContext:Context
    private var listener:SearchAdapter.OnItemClickListener? = null

    constructor(mContext: Context) : super() {
        this.mContext = mContext
    }

    /**
     * 增加数据
     */
    fun add(data:ArrayList<TitleBean>){
        val oldSize = mData.size
        mData.addAll(data)
        notifyItemRangeInserted(oldSize,data.size)
    }

    /**
     * 更新数据
     */
    fun update(data:ArrayList<TitleBean>){
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRVHolder {
        val binding = DataBindingUtil.inflate<ItemGankSearchBinding>(LayoutInflater.from(mContext), R.layout.item_gank_search,parent,false)
        val holder = BaseRVHolder(binding.root)
        holder.binding = binding
        holder.binding.root.setOnClickListener {
            listener?.clickItem((holder.itemView.tag as TitleBean))
        }
        return holder
    }

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(holder: BaseRVHolder, position: Int) {
        holder.bind(BR.gankBean,mData[position])
        holder.itemView.tag = mData[position]
        (holder.binding as ItemGankSearchBinding).time.text = StringUtils.getGankReadTime(mData[position].publishedAt)
    }

    interface OnItemClickListener{
        fun clickItem(item:TitleBean)
    }
}