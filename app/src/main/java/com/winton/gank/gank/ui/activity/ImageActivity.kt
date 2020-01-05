package com.winton.gank.gank.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
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
        fun start(context: Context, urls:ArrayList<String>)
                = context.startActivity(Intent(context,ImageActivity::class.java).apply { putExtra("url", urls) })
    }

    override fun getLayoutId() = R.layout.act_img

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        val url = intent.getStringArrayListExtra("url")
        binding.vp.adapter = ImageAdapter(this,url)
    }

    /**
     * 适配器
     */
    class ImageAdapter constructor(private val mContext:Context, private val mData:ArrayList<String>): PagerAdapter() {

        override fun isViewFromObject(view: View, any: Any) = view == any
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any)
                = container.removeView(`object` as View)
        override fun getCount(): Int = mData.size

        override fun instantiateItem(container: ViewGroup, position: Int) = BigImageView(mContext).apply {
            showImage(Uri.parse(mData[position]))
            setOnClickListener { (mContext as ImageActivity).finish() }
            setInitScaleType(BigImageView.INIT_SCALE_TYPE_CENTER_CROP)
            setImageViewFactory(GlideImageViewFactory())
            container.addView(this, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

}