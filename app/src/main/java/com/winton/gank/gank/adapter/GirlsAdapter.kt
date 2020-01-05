package com.winton.gank.gank.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ItemGirlsListBinding
import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.utils.BindingUtils
import com.winton.gank.gank.utils.UiTools

/**
 * @author: winton
 * @time: 2018/10/25 7:17 PM
 * @desc: 妹子Adapter
 */
class GirlsAdapter constructor(private val mContext: Context) : RecyclerView.Adapter<GirlsAdapter.ViewHolder>() {

    private var onItemClickListener : ((TitleBean) -> Unit)? = null
    private val mData : ArrayList<TitleBean> = ArrayList()
    private var baseHeight = UiTools.dpToPx(mContext, 150f)

    fun update(data:ArrayList<TitleBean>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(action : (TitleBean) -> Unit) {
        onItemClickListener = action
    }
    fun add(data:ArrayList<TitleBean>) {
        val oldSize = mData.size
        mData.addAll(data)
        notifyItemRangeChanged(oldSize-1,data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        DataBindingUtil.inflate<ItemGirlsListBinding>(LayoutInflater.from(mContext), R.layout.item_girls_list,parent,false).run {
            root.setOnClickListener {
                onItemClickListener?. invoke(root.tag as TitleBean)
            }
            return ViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        (holder.binding as ItemGirlsListBinding).tvTip.visibility = View.GONE
        val layoutParams = holder.binding.root.layoutParams
        if (position == 0) {
            layoutParams.height = baseHeight
            (holder.binding as ItemGirlsListBinding).tvTip.visibility = View.VISIBLE
        }
        if (position != 0) {
            layoutParams.height = (1.5 * baseHeight).toInt()
        }
        holder.bindData(mData[position])
        holder.binding.root.tag = mData[position]
    }

    override fun getItemCount() = mData.size

    class ViewHolder constructor(viewBinding : ItemGirlsListBinding) : BaseRVHolder(viewBinding.root, viewBinding) {

        fun bindData(titleBean: TitleBean) = (binding as ItemGirlsListBinding).run {
                BindingUtils.bindGirlsImage(img, titleBean.url)
            }
    }
}