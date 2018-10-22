package com.winton.gank.gank.utils

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * @author: winton
 * @time: 2018/10/19 上午10:42
 * @desc: ViewBinding 帮助类
 */
object BindingUtils {

    @BindingAdapter("src","placeHolder","error")
    @JvmStatic
    fun bindGift(imageView: ImageView, url:String, placeHolder:Drawable, errorId:Drawable){
        Glide.with(imageView.context)
                .applyDefaultRequestOptions(
                        RequestOptions()
                                .placeholder(placeHolder)
                                .error(errorId)
                                .circleCrop()
                                )
                .load(url)
                .into(imageView)
    }

    @BindingAdapter("src","placeHolder")
    @JvmStatic
    fun bindArticleImg(imageView: ImageView,urls:List<String>?,placeHolder: Drawable){
        urls?.let {
            if(it.isNotEmpty()){
                Glide.with(imageView.context)
                        .applyDefaultRequestOptions(
                                RequestOptions()
                                        .placeholder(placeHolder))
                        .load(urls[0])
                        .into(imageView)
            }
        }
    }
}