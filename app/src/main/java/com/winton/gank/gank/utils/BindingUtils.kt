package com.winton.gank.gank.utils

import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.winton.gank.gank.R
import com.winton.gank.gank.utils.glide.GlideApp

/**
 * @author: winton
 * @time: 2018/10/19 上午10:42
 * @desc: ViewBinding 帮助类
 */
object BindingUtils {

    fun bindCircleImage(imageView: ImageView, url:String){
        GlideApp.with(imageView.context)
                .load(url)
                .circleCrop()
                .transition(withCrossFade())
                .into(imageView)
    }

    fun bindGirlsImage(imageView: ImageView, url: String) {
        GlideApp.with(imageView.context)
                .load(url)
                .transition(withCrossFade())
                .into(imageView)
    }

    fun bindArticleImg(imageView: ImageView,url:String){
            GlideApp.with(imageView.context)
                    .load(url)
                    .error(R.mipmap.default_img)
                    .transition(withCrossFade())
                    .into(imageView)
    }
}