package com.winton.gank.gank.adapter.mulitype

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.winton.gank.gank.App
import com.winton.gank.gank.BR
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.BaseRVHolder
import com.winton.gank.gank.adapter.HeadImageAdapter
import com.winton.gank.gank.databinding.ItemGankArticleBinding
import com.winton.gank.gank.databinding.ItemImageHeaderGiftBinding
import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.utils.BindingUtils
import com.winton.gank.gank.utils.StringUtils
import kotlin.random.Random

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

    private val mDrawables = Array<Drawable>(2){
        mContext.resources.getDrawable(R.mipmap.icon_man)
        mContext.resources.getDrawable(R.mipmap.icon_women)
    }

    private val userHeaders = HashMap<String, Int>()

    fun updateData(items: ArrayList<IndexItem>) {
        mData.clear()
        mData.addAll(items)
        this.notifyDataSetChanged()
    }

    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)

        when (viewType) {
            T_IMAGE -> {
                val binding = DataBindingUtil.inflate<ItemImageHeaderGiftBinding>(layoutInflater, R.layout.item_image_header_gift, parent, false)
                binding.root.setOnClickListener {
//                    onItemClickListener?.invoke(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding, mDrawables, userHeaders)
            }
            T_TITLE -> {
                val binding = DataBindingUtil.inflate<ItemGankArticleBinding>(layoutInflater, R.layout.item_gank_article, parent, false)
                bindIdMap.put(T_TITLE, BR.gankBean)
                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding, mDrawables, userHeaders)
            }
            T_END -> {
                val binding = DataBindingUtil.inflate<ItemGankArticleBinding>(layoutInflater, R.layout.item_gank_article, parent, false)
                bindIdMap.put(T_END, BR.gankBean)
                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding, mDrawables, userHeaders)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemGankArticleBinding>(layoutInflater, R.layout.item_gank_article, parent, false)
                bindIdMap.put(T_CONTENT, BR.gankBean)
                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(binding.root.tag as IndexItem)
                }
                return ViewHolder(binding, mDrawables, userHeaders)
            }
        }

    }

    fun registerOnItemClickListener(action : (IndexItem) -> Unit){
        this.onItemClickListener = action
    }

    override fun getItemCount() = mData.size

    override
    fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mData[position].run {
            if (this.getType() == T_IMAGE) {
                holder.bindIndexHeaderGift(this)
            } else {
                holder.bindArticle(this.item!!, holder.binding as ItemGankArticleBinding)
                holder.binding.root.tag = this
            }
        }
    }

    override fun getItemViewType(position: Int) = mData[position].getType()


    class ViewHolder(viewBinding: ViewDataBinding,
                     private val mDrawables:Array<Drawable>,
                     private val userHeaders:HashMap<String, Int>) : BaseRVHolder(viewBinding.root, viewBinding) {

        override fun bind(variableId: Int, value: Any) {
            super.bind(variableId, value)
        }

        fun bindArticle(bean: TitleBean, viewBinding: ItemGankArticleBinding) {
            with(viewBinding) {
                bindPublisher(bean, ivHeader, tvPublisher)
                tvViewNum.text = bean.views.toString()
                tvSource.text = bean.source
                tvType.text = bean.type
                tvPublishTime.text = "发布于：${StringUtils.getGankReadTime(bean.publishedAt)}"
                bindArticleImg(bean, viewBinding.flImagesContent)
                //need last set, avoid
                tvContent.text = bean.desc
            }
        }

        private fun bindArticleImg(bean: TitleBean, container: FrameLayout) {
            container.visibility = View.VISIBLE
            // images may null
            bean.images?.let {
                val imagesNum = it.size
                if (imagesNum <= 0) {
                    container.visibility = View.GONE
                } else {
                    BindingUtils.bindCircleImage(container.getChildAt(0) as ImageView, it[0])
                }

            } ?: kotlin.run { container.visibility = View.GONE }
        }

        private fun bindPublisher(bean: TitleBean, head: ImageView, name: TextView) {
            name.text = bean.who
            var headId = userHeaders[bean.who]
            if (headId == null) {
                headId = Random.nextInt(2)
                userHeaders[bean.who] = headId
            }
            Log.d("id", "${bean.who}:$headId@$head")
            head.setImageDrawable(mDrawables[headId])
        }

        fun bindIndexHeaderGift(item : IndexItem) {
            (binding as ItemImageHeaderGiftBinding).avpContainer.setAdapter(HeadImageAdapter(binding.root.context , item.head!!))
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