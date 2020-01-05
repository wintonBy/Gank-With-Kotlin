package com.winton.gank.gank.adapter.mulitype

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.BaseRVHolder
import com.winton.gank.gank.databinding.ItemGankArticleBinding
import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.utils.BindingUtils
import com.winton.gank.gank.utils.StringUtils
import com.winton.gank.gank.utils.UiTools
import kotlin.random.Random

/**
 * @author: winton
 * @time: 2018/10/23 下午1:28
 * @desc: Gank 分类列表
 */
class GankListAdapter(private var mContext: Context) : RecyclerView.Adapter<GankListAdapter.ViewHolder>() {

    private var mData : ArrayList<TitleBean> = ArrayList()
    private var onItemClickListener : ((TitleBean) -> Unit)? = null
    private val mDrawables = arrayOf( R.mipmap.icon_man, R.mipmap.icon_women).toIntArray()
    private val userHeaders = HashMap<String, Int>()

    fun setItemClickListener(action :(TitleBean) -> Unit) {
        onItemClickListener = action
    }

    fun update(data : ArrayList<TitleBean>){
        this.mData.clear()
        this.mData.addAll(data)
        notifyDataSetChanged()
    }

    fun add(data : ArrayList<TitleBean>){
        val oldSize = mData.size
        this.mData.addAll(data)
        notifyItemRangeChanged(oldSize-1,data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = DataBindingUtil.inflate<ItemGankArticleBinding>(layoutInflater, R.layout.item_gank_article,parent,false)
        binding.root.setOnClickListener {
            onItemClickListener?.invoke(binding.root.tag as TitleBean)
        }
        return ViewHolder(binding, mDrawables, userHeaders)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position, mData[position])
        holder.binding.root.tag =mData[position]
    }

    class ViewHolder constructor(viewBinding : ViewDataBinding,
                                 private val mDrawables:IntArray,
                                 private val userHeaders:HashMap<String, Int>) : BaseRVHolder(viewBinding.root, viewBinding) {

        fun bindData(position: Int, item: TitleBean){
            with(binding as ItemGankArticleBinding) {
                bindPublisher(item, ivHeader, tvPublisher)
                tvViewNum.text = item.views.toString()
                tvSource.text = item.source
                tvType.visibility = View.GONE
                tvPublishTime.text = "发布于：${StringUtils.getGankReadTime(item.publishedAt)}"
                bindArticleImg(item, flImagesContent)
                //need last set, avoid
                tvContent.typeface = App.typeTTF
                tvContent.text = item.desc
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
                    val imageView = container.getChildAt(0) as ImageView
                    val currentViewCount = container.childCount
                    if (currentViewCount < imagesNum) {
                        val padding = UiTools.dpToPx(container.context, 2f)
                        for (i in 1 .. imagesNum - currentViewCount) {
                            val params = FrameLayout.LayoutParams(imageView.layoutParams)
                            params.leftMargin = (currentViewCount - 1 + i) * 40
                            params.gravity = Gravity.CENTER_VERTICAL
                            val view = ImageView(container.context)
                            view.setBackgroundResource(R.drawable.article_image_bg)
                            view.setPadding(padding, padding, padding, padding)
                            container.addView(view, params)
                        }
                    } else if (currentViewCount > imagesNum) {
                        val count = currentViewCount - imagesNum
                        container.removeViews(imagesNum, count)
                    }

                    for (i in it.indices) {
                        BindingUtils.bindCircleImage(container.getChildAt(i) as ImageView, it[i])
                    }
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
            head.setImageResource(mDrawables[headId])
        }

    }
}