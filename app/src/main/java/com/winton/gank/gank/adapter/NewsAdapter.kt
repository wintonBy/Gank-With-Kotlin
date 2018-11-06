package com.winton.gank.gank.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ItemNewsListVideoBinding
import com.winton.gank.gank.http.ErrorCode
import com.winton.gank.gank.http.bean.NewsContent
import com.winton.gank.gank.utils.BindingUtils
import com.winton.gank.gank.utils.VideoPathDecoder

/**
 * @author: winton
 * @time: 2018/11/2 5:44 PM
 * @desc: 新闻适配器
 */
class NewsAdapter:RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    companion object {
        const val TAG = "NewsAdapter"
    }

    private var mData:ArrayList<NewsContent> = ArrayList()

    private var mContext: Context

    private lateinit var smallVideoHelper: GSYVideoHelper

    private lateinit var gsySmallVideoHelperBuilder: GSYVideoHelper.GSYVideoHelperBuilder


    constructor(mContext: Context) : super() {
        this.mContext = mContext
    }

    /**
     * 添加下拉刷新的数据
     */
    fun refresh(data:ArrayList<NewsContent>){
        mData.addAll(0,data)
        notifyDataSetChanged()
    }

    /**
     * 添加上拉加载更多的数据
     */
    fun more(data:ArrayList<NewsContent>){
        val oldSize = mData.size
        mData.addAll(data)
        notifyItemRangeInserted(oldSize,data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemNewsListVideoBinding>(LayoutInflater.from(mContext), R.layout.item_news_list_video,parent,false)
        val holder = ViewHolder(this,binding)
        holder.setVideoPlayerHelper(smallVideoHelper!!,gsySmallVideoHelperBuilder!!)
        return holder
    }

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(mData[position],position)
        holder.itemView.tag = mData[position]
    }

    class ViewHolder:BaseRVHolder{
        private lateinit var smallVideoHelper: GSYVideoHelper

        private lateinit var gsySmallVideoHelperBuilder: GSYVideoHelper.GSYVideoHelperBuilder

        private var imageView:ImageView

        private var adapter:NewsAdapter

        constructor(adapter: NewsAdapter,binding: ItemNewsListVideoBinding) : super(binding.root){
            this.binding = binding
            imageView = ImageView(binding.root.context)
            this.adapter = adapter
        }

        fun bindData(news:NewsContent,position: Int){
            (binding as ItemNewsListVideoBinding).run {
                BindingUtils.bindArticleImg(imageView,news.video_detail_info.detail_video_large_image.url)
                smallVideoHelper.addVideoPlayer(position,imageView,TAG,itemContainer,playBtn)

                playBtn.setOnClickListener {
                    adapter.notifyDataSetChanged()
                    smallVideoHelper.setPlayPositionAndTag(position, TAG)
                    gsySmallVideoHelperBuilder.videoTitle = news.title
                    val decoder = object :VideoPathDecoder(){
                        override fun onDecodeSuccess(url: String?) {
                            App.mUIHandler.post {
                                url?.let {
                                    LogUtils.dTag("winton","playurl:$it")
                                    gsySmallVideoHelperBuilder.url = it
                                    smallVideoHelper.startPlay()
                                }
                            }
                        }

                        override fun onDecodeFailure(code: ErrorCode) {
                            ToastUtils.showShort("解析数据失败")
                        }
                    }
                    decoder.decodePath(news.url)
                }
            }


        }
        fun setVideoPlayerHelper(videoHelper:GSYVideoHelper,videoHelperBuilder:GSYVideoHelper.GSYVideoHelperBuilder){
            smallVideoHelper = videoHelper
            gsySmallVideoHelperBuilder = videoHelperBuilder
        }
    }

    fun setVideoPlayerHelper(videoHelper:GSYVideoHelper,videoHelperBuilder:GSYVideoHelper.GSYVideoHelperBuilder){
        smallVideoHelper = videoHelper
        gsySmallVideoHelperBuilder = videoHelperBuilder
    }


}