package com.winton.gank.gank.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ItemMeCenterBinding
import com.winton.gank.gank.http.bean.PersonCenterBean
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author: winton
 * @time: 2018/10/29 3:41 PM
 * @desc: 个人中心RecyclerView adapter
 */
class MeAdapter constructor(private val mContext: Context) : RecyclerView.Adapter<MeAdapter.ViewHolder>() {

    private var onItemClickListener : ((PersonCenterBean) -> Unit)? = null
    private var onItemLongClickListener : ((RecyclerView.ViewHolder) -> Boolean)?= null

    private var mData:ArrayList<PersonCenterBean> = ArrayList()

    fun update(data:ArrayList<PersonCenterBean>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun getData():ArrayList<PersonCenterBean> = mData

    override fun getItemCount() = mData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        DataBindingUtil.inflate<ItemMeCenterBinding>(LayoutInflater.from(mContext), R.layout.item_me_center,parent,false).run {
            root.setOnClickListener { onItemClickListener ?. invoke((root.tag as PersonCenterBean)) }
            return ViewHolder(this).apply {
                root.setOnLongClickListener { onItemLongClickListener ?. invoke(this) ?: false }
            }
        }
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        holder.bindData(mData[position])
        holder.binding.root.tag = mData[position]
    }

    class ViewHolder constructor(viewBinding : ViewDataBinding): BaseRVHolder(viewBinding.root, viewBinding), ItemTouchListener {

        fun bindData(item:PersonCenterBean){
            (binding as ItemMeCenterBinding).let {
                it.tvTitle.text = item.title
                if(item.iconId != -1){
                    it.ivIcon.setImageResource(item.iconId)
                }
            }
        }

        override fun onItemSelect() {
            (itemView as CardView).apply {
                this.setCardBackgroundColor(App.INSTANCE.getColor(R.color.divider_line))

            }
        }

        override fun onItemClear() = (itemView as CardView).run {
            setCardBackgroundColor(Color.TRANSPARENT)
        }

    }
}


interface ItemTouchListener{
    fun onItemSelect()

    fun onItemClear()
}

/**
 * 拖拽回调
 */
class ItemTouchHelperCallBack constructor(private val mAdapter: MeAdapter) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder) =
       makeMovementFlags(ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP,0)

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        val oldPosition = viewHolder.adapterPosition
        val newPosition = target.adapterPosition
        Collections.swap(mAdapter.getData(),oldPosition,newPosition)
        mAdapter.notifyItemMoved(oldPosition,newPosition)
        return true
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
                if(viewHolder is ItemTouchListener){
                    viewHolder.onItemSelect()
                }
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if(viewHolder is ItemTouchListener){
            viewHolder.onItemClear()
        }
    }
}