package com.winton.gank.gank.adapter.mulitype

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.BR
import com.winton.gank.gank.adapter.BaseRVHolder
import com.winton.gank.gank.databinding.ItemIndexArticleBinding
import com.winton.gank.gank.databinding.ItemIndexArticleEndBinding
import com.winton.gank.gank.databinding.ItemIndexArticleTitleBinding
import com.winton.gank.gank.databinding.ItemIndexGiftBinding
import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.http.response.gank.ResultBean
import com.winton.gank.gank.utils.BindingUtils
import java.util.*

/**
 * @author: winton
 * @time: 2018/10/17 下午6:05
 * @desc: 首页Adapter
 */

class IndexAdapter(private val mContext: Context) : RecyclerView.Adapter<IndexAdapter.ViewHolder>() {

    companion object {
        const val T_TITLE = 1
        const val T_CONTENT = 2
        const val T_END = 3
        const val T_IMAGE = 4
    }

    private var onItemClickListener : ((IndexItem) -> Unit) ? = null
    private val bindIdMap = SparseIntArray()
    private val mData = ArrayList<IndexItem>()

    fun updateData(orgData: ResultBean) {
        fixData(orgData)
        this.notifyDataSetChanged()
    }

    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)

        when (viewType) {
            T_IMAGE -> {
                val binding = DataBindingUtil.inflate<ItemIndexGiftBinding>(layoutInflater, R.layout.item_index_gift, parent, false)
                bindIdMap[T_IMAGE, BR.gankBean]
                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding)
            }
            T_TITLE -> {
                val binding = DataBindingUtil.inflate<ItemIndexArticleTitleBinding>(layoutInflater, R.layout.item_index_article_title, parent, false)
                bindIdMap[T_TITLE, BR.gankBean]
                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding)
            }
            T_END -> {
                val binding = DataBindingUtil.inflate<ItemIndexArticleEndBinding>(layoutInflater, R.layout.item_index_article_end, parent, false)
                bindIdMap[T_END, BR.gankBean]
                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemIndexArticleBinding>(layoutInflater, R.layout.item_index_article, parent, false)
                bindIdMap[T_CONTENT, BR.gankBean]
                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding)
            }
        }

    }

    fun registerOnItemClickListener(action : (IndexItem) -> Unit){
        this.onItemClickListener = action
    }

    override fun getItemCount() = mData.size

    override
    fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val variableId = bindIdMap[getItemViewType(position)]
        val item = mData[position].item
        if (item != null && variableId != 0) {
            holder.bind(variableId, item)
            holder.binding.root.tag =mData[position]
        }
    }

    override fun getItemViewType(position: Int) = mData[position].getType()

    /**
     * 数据转换
     */
    private fun fixData(orgData: ResultBean) = with(orgData) {
        mData.clear()
        gift      ?. let { addGift(it) }
        android   ?. let { addItems(it) }
        iOS       ?. let { addItems(it) }
        app       ?. let { addItems(it) }
        recommond ?. let { addItems(it) }
    }

    /**
     * 添加普通的资讯
     */
    private fun addItems(items: List<TitleBean>) {
        for (i in items.indices) {
            when (i) {
                0               -> mData.add(IndexItem(T_TITLE, items[i]))
                items.size - 1  -> mData.add(IndexItem(T_END, items[i]))
                else            -> mData.add(IndexItem(T_CONTENT, items[i]))
            }
        }
    }

    /**
     * 添加福利
     */
    private fun addGift(items: List<TitleBean>) {
        for (item in items) {
            mData.add(IndexItem(T_IMAGE, item))
        }
    }

    class ViewHolder(viewBinding: ViewDataBinding) : BaseRVHolder(viewBinding.root, viewBinding) {

        override fun bind(variableId: Int, value: Any) {
            super.bind(variableId, value)
            bindArticleImg(value as TitleBean)
        }

        private fun bindArticleImg(bean: TitleBean) {
            val images = bean.images
            var imgUrl  = ""
            images.let {
                if(it.isNotEmpty()){
                    imgUrl = it[0]
                }
            }
            when (binding) {
                is ItemIndexArticleTitleBinding -> {
                    BindingUtils.bindArticleImg((binding as ItemIndexArticleTitleBinding).ivImg, imgUrl)
                    setTitleIcon((binding as ItemIndexArticleTitleBinding).tvGroupTitle, bean.type)
                }
                is ItemIndexArticleBinding -> {
                    BindingUtils.bindArticleImg((binding as ItemIndexArticleBinding).ivImg, imgUrl)
                }
                is ItemIndexArticleEndBinding -> {
                    BindingUtils.bindArticleImg((binding as ItemIndexArticleEndBinding).ivImg, imgUrl)
                }
                is ItemIndexGiftBinding ->{
                    BindingUtils.bindGift((binding as ItemIndexGiftBinding).ivGift,bean.url)
                }
            }
        }

        /**
         * 设置分组的icon
         */
        private fun setTitleIcon(tvGroupTitles: TextView, type: String) {
            var drId = R.mipmap.ic_default_group
            when (type) {
                "Android"  -> drId = R.mipmap.ic_android
                "iOS"      -> drId = R.mipmap.ic_ios
                "App"      -> drId = R.mipmap.ic_app
            }
            val dr = App.INSTANCE.resources.getDrawable(drId)
            dr.setBounds(0, 0, dr.minimumWidth+10, dr.minimumHeight+10)
            tvGroupTitles.setCompoundDrawables(dr, null, null, null)
        }
    }
}