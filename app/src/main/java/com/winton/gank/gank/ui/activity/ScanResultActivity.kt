package com.winton.gank.gank.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.githang.statusbar.StatusBarCompat
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ActScanResultBinding
import com.winton.gank.gank.ui.BaseActivity

/**
 * @author: winton
 * @time: 2018/11/20 8:29 PM
 * @desc: 扫描结果
 */
class ScanResultActivity:BaseActivity<ActScanResultBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.act_scan_result
    }

    companion object {
        fun start(context: Context,result:String){
            var intent = Intent(context,ScanResultActivity::class.java)
            intent.putExtra("data",result)
            context.startActivity(intent)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        var result = intent.getStringExtra("data")
        binding.tvTitle.text = "扫码结果"
        binding.tvResult.text = result
    }

    override fun initListener() {
        super.initListener()
        binding.ivBack.setOnClickListener {
            this.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        StatusBarCompat.setStatusBarColor(this, Color.WHITE,true)
    }

}