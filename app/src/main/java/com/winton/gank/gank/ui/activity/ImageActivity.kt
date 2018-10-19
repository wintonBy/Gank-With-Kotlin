package com.winton.gank.gank.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
        fun start(context: Context, url:String){
            var intent = Intent(context,ImageActivity::class.java)
            intent.putExtra("url",url)
            context.startActivity(intent)
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.act_img
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        val url = intent.getStringExtra("url")
        binding.bigImage.showImage(Uri.parse(url))

    }

    override fun initListener() {
        super.initListener()
        binding.bigImage.setOnClickListener {
            finish()
        }
    }

}