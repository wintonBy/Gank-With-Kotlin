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
import com.winton.gank.gank.widget.AutoViewPager
import com.winton.gank.gank.widget.AutoViewPagerContainer

class HeadImageAdapter constructor(val context: Context, private val urls: ArrayList<TitleBean>): PagerAdapter(), AutoViewPager.AutoAdapter {


    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding  = DataBindingUtil.inflate<ItemVpImageGiftBinding>(LayoutInflater.from(context),
                R.layout.item_vp_image_gift,
                container,
                false)
        var fakePosition = position
        if (needSwapPosition()) {
            if (position == 0) {
                fakePosition = urls.size - 1
            } else if (position == count - 1) {
                fakePosition = 0
            } else {
                fakePosition = position - 1
            }

        }

        BindingUtils.bindArticleImg(binding.ivImg, urls[fakePosition].url)
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount() : Int {
        if (urls.size <= 1) return urls.size
        //if has more than one item, it can loop
        return urls.size + 2
    }

    override fun needSwapPosition() = urls.size > 1

    override fun getAdapter() = this
}