package com.winton.gank.gank.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ItemGirlsListBinding
import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.utils.BindingUtils
import com.winton.gank.gank.utils.StringUtils

/**
 * @author: winton
 * @time: 2018/10/25 7:17 PM
 * @desc: 妹子Adapter
 */
class GirlsAdapter constructor(private val mContext: Context) : RecyclerView.Adapter<GirlsAdapter.ViewHolder>() {

    private var onItemClickListener : ((TitleBean) -> Unit)? = null
    private val mData : ArrayList<TitleBean> = ArrayList()

    fun update(data:ArrayList<TitleBean>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun add(data:ArrayList<TitleBean>) {
        val oldSize = mData.size
        mData.addAll(data)
        notifyItemRangeChanged(oldSize-1,data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        DataBindingUtil.inflate<ItemGirlsListBinding>(LayoutInflater.from(mContext), R.layout.item_girls_list,parent,false).run {
            root.setOnClickListener {
                onItemClickListener ?. invoke(root.tag as TitleBean)
            }
            return ViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        val layoutParams = holder.binding.root.layoutParams
        layoutParams.height = getHeight(position)
        holder.bindData(mData[position])
        holder.binding.root.tag = mData[position]
    }

    override fun getItemCount() = mData.size

    private fun getHeight(position: Int) = (position % 3)*100 + 600

    class ViewHolder constructor(viewBinding : ItemGirlsListBinding) : BaseRVHolder(viewBinding.root, viewBinding) {

        fun bindData(titleBean: TitleBean) = (binding as ItemGirlsListBinding).run {
                time.text = "发布日期：${StringUtils.getGankReadTime(titleBean.createdAt)} \n作者：${titleBean.who}"
                BindingUtils.bindArticleImg(img, titleBean.url)
            }

    }
}