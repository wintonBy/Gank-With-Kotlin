package com.winton.gank.gank.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.support.v7.util.DiffUtil
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
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
class MeAdapter:RecyclerView.Adapter<MeAdapter.ViewHolder> {

    private var mContext:Context
    private var onItemClickListener:OnItemClick? = null
    private var onItemLongClickListener:OnItemLongClick?= null

    private var mData:ArrayList<PersonCenterBean> = ArrayList()

    constructor(mContext: Context) : super() {
        this.mContext = mContext
    }

    fun update(data:ArrayList<PersonCenterBean>){
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun getData():ArrayList<PersonCenterBean>{
        return mData
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setOnItemClickListener(listener:OnItemClick){
        onItemClickListener = listener
    }
    fun setOnItemLongClickListener(listener:OnItemLongClick){
        onItemLongClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeAdapter.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemMeCenterBinding>(LayoutInflater.from(mContext), R.layout.item_me_center,parent,false)
        binding.root.setOnClickListener {
            onItemClickListener?.onItemClick((binding.root.tag as PersonCenterBean))
        }
        val holder = ViewHolder(binding)
        binding.root.setOnLongClickListener(object :View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                var result = false
                onItemLongClickListener?.let {
                    result = it.onItemLongClick(holder)
                }
                return result
            }
        })
        return holder
    }

    override fun onBindViewHolder(holder: MeAdapter.ViewHolder, position: Int) {
        holder.bindData(mData[position])
        holder.binding.root.tag = mData[position]
    }

    class ViewHolder:BaseRVHolder,ItemTouchListener{
        constructor(binding: ViewDataBinding) : super(binding.root){
            this.binding = binding
        }

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

        override fun onItemClear() {
            (itemView as CardView).apply {
                this.setCardBackgroundColor(Color.TRANSPARENT)

            }
        }
    }

    /**
     * 单击Item
     */
    interface OnItemClick{
        fun onItemClick(item: PersonCenterBean)
    }

    /**
     * 长按Item
     */
    interface OnItemLongClick{
        fun onItemLongClick(viewHolder: RecyclerView.ViewHolder):Boolean
    }
}


interface ItemTouchListener{
    fun onItemSelect()

    fun onItemClear()
}

/**
 * 拖拽回调
 */
class ItemTouchHelperCallBack:ItemTouchHelper.Callback{

    private var mAdapter:MeAdapter

    constructor(mAdapter: MeAdapter) : super() {
        this.mAdapter = mAdapter
    }


    override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder): Int {

        return makeMovementFlags(ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP,0)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        var oldPosition = viewHolder.adapterPosition
        var newPosition = target.adapterPosition
        Collections.swap(mAdapter.getData(),oldPosition,newPosition)
        mAdapter.notifyItemMoved(oldPosition,newPosition)
        return true
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {

    }

    override fun isLongPressDragEnabled(): Boolean {
        return super.isLongPressDragEnabled()
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