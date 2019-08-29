package com.winton.gank.gank.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ItemVpImageGiftBinding
import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.utils.BindingUtils

class HeadImageAdapter constructor(val context:Context, private val urls : ArrayList<TitleBean>) : PagerAdapter() {


    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding  = DataBindingUtil.inflate<ItemVpImageGiftBinding>(LayoutInflater.from(context),
                R.layout.item_vp_image_gift,
                container,
                false)
        BindingUtils.bindArticleImg(binding.ivImg, urls[position].url)
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount() = urls.size
}