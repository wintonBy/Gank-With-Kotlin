package com.winton.gank.gank.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ActWebBinding
import com.winton.gank.gank.ui.BaseActivity

/**
 * @author: winton
 * @time: 2018/10/19 下午5:02
 * @desc: 用于加载Web的Activity
 */
class WebActivity:BaseActivity<ActWebBinding>() {

    private lateinit var url: String

    private val webview:WebView by lazy {
        WebView(App.INSTANCE)
    }

    companion object {
        fun start(context:Context,url:String){
            var intent = Intent(context,WebActivity::class.java)
            intent.putExtra("url",url)
            context.startActivity(intent)
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.act_web
    }

    override fun initListener() {
        binding.ivBack.setOnClickListener {
            if(webview.canGoBack()){
                webview.goBack()
            }else{
                this@WebActivity.finish()
            }
        }
    }

    override fun onBackPressed() {
        if(webview.canGoBack()){
            webview.goBack()
        }else{
            super.onBackPressed()
        }

    }

    override fun initData(savedInstanceState: Bundle?) {
        url = intent.getStringExtra("url")
        initWebView()
        webview.loadUrl(url)
    }

    /**
     * 初始化WebView
     */
    private fun initWebView() {
        var param = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        binding.fl.addView(webview,param)
        val setting = webview.settings

        setting.javaScriptEnabled = false
        //设置自适应屏幕
        setting.useWideViewPort = true
        setting.loadWithOverviewMode = true
        //支持缩放
        setting.setSupportZoom(true)
        setting.builtInZoomControls = true
        setting.displayZoomControls = false

        setting.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        //允许访问文件
        setting.allowFileAccess = true
        //自动加载图片
        setting.loadsImagesAutomatically = true
        //默认编码格式
        setting.defaultTextEncodingName = "utf-8"

        webview.webViewClient = object :WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
            }
        }

        webview.webChromeClient = object :WebChromeClient(){
            override fun onReceivedTitle(view: WebView?, title: String?) {
                title?.let {
                    binding.tvTitle.text = it
                }
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {

                if(newProgress == 100){
                    binding.progress.visibility = View.GONE
                }
                binding.progress.text = "正在加载$newProgress%"
            }

        }
    }


    override fun onDestroy() {
        webview.loadDataWithBaseURL(null,"","text/html","utf-8",null)
        webview.clearHistory()
        binding.fl.removeView(webview)
        webview.destroy()
        super.onDestroy()
    }


}