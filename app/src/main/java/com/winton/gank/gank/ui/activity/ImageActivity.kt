package com.winton.gank.gank.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.github.piasy.biv.view.BigImageView
import com.github.piasy.biv.view.GlideImageViewFactory
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ActImgBinding
import com.winton.gank.gank.ui.BaseActivity

/**
 * @author: winton
 * @time: 2018/10/19 下午8:00
 * @desc: 描述
 */
class ImageActivity:BaseActivity<ActImgBinding>() {


    companion object {
        fun start(context: Context, urls:ArrayList<String>){
            var intent = Intent(context,ImageActivity::class.java)
            intent.putExtra("url",urls)
            context.startActivity(intent)
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.act_img
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        val url = intent.getStringArrayListExtra("url")
        binding.vp.adapter = ImageAdapter(this,url)

    }

    /**
     * 适配器
     */
    class ImageAdapter:PagerAdapter{
        private var mData:ArrayList<String>
        private var mContext:Context

        constructor(context: Context,data:ArrayList<String>) : super(){
            mData = data
            mContext = context
        }

        override fun isViewFromObject(view: View, any: Any): Boolean {
            return view == any
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var imageView = BigImageView(mContext)
            val url = mData[position]
            imageView.showImage(Uri.parse(url))
            imageView.setOnClickListener {
                (mContext as ImageActivity).finish()
            }

            imageView.setInitScaleType(BigImageView.INIT_SCALE_TYPE_CENTER_CROP)
            imageView.setImageViewFactory(GlideImageViewFactory())
            container.addView(imageView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            return imageView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getCount(): Int = mData.size
    }

}