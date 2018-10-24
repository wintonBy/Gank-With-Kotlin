package com.winton.gank.gank.ui.activity

import android.os.Bundle
import com.winton.gank.gank.BuildConfig
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ActSplashBinding
import com.winton.gank.gank.ui.BaseActivity

/**
 * @author: winton
 * @time: 2018/10/24 7:49 PM
 * @desc: 描述
 */
class SplashActivity:BaseActivity<ActSplashBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.act_splash
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        binding.version.text = "v"+BuildConfig.VERSION_NAME
    }

    override fun onResume() {
        super.onResume()
        binding.root.postDelayed({
            MainActivity.start(this@SplashActivity)
            this@SplashActivity.finish()
        },2000)
    }
}