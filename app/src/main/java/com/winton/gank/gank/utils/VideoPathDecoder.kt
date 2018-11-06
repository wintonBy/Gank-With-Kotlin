package com.winton.gank.gank.utils

import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.blankj.utilcode.util.LogUtils
import com.winton.gank.gank.App
import com.winton.gank.gank.http.ErrorCode
import com.winton.gank.gank.http.RetrofitHolder
import com.winton.gank.gank.http.VideoDetailSubscriber
import com.winton.gank.gank.http.response.VideoDetailResponse
import com.winton.library.executor.Priority
import com.winton.library.executor.PriorityRunnable
import org.jsoup.Jsoup

/**
 * @author: winton
 * @time: 2018/11/6 2:51 PM
 * @desc: 视频播放地址解析
 */
abstract class VideoPathDecoder {

    companion object {
        const val NICK = "chaychan"
    }

    fun decodePath(url:String){
        App.appExcuter.execute(PriorityRunnable(Priority.HIGH, Runnable {
            val doc  = Jsoup.connect("http:www.baidu.com").timeout(5000).get()
            val videos = doc.getElementsByTag("video")
            if(videos.size >0){
                val playUrl = videos[0].attr("src")
                LogUtils.dTag("decode",playUrl)
                onDecodeSuccess(playUrl)
            }
        }))


    }


//    fun decodePath(url:String){
//        val webView = WebView(App.INSTANCE)
//        webView.settings.javaScriptEnabled = true
//        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
//
//        val parseFunc = ParseFunc(object :ParseListener{
//            override fun onGetParams(r: String, s: String) {
//                sendRequest(url,r,s)
//            }
//
//            override fun onGetPath(path: String) {
//                LogUtils.dTag("decoder",path)
//                onDecodeSuccess(path)
//            }
//        })
//        webView.addJavascriptInterface(parseFunc, NICK)
//        webView.loadUrl(url)
//        webView.webViewClient = object :WebViewClient(){
//            override fun onPageFinished(view: WebView, url: String) {
//                    App.mUIHandler.postDelayed({addJs(view)},5000)
//            }
//        }
//
//    }

    private fun sendRequest(url:String,r:String,s:String){

        RetrofitHolder.wInstance().getVideoDetail(url,r,s,object: VideoDetailSubscriber(){
            override fun onSuccess(t: VideoDetailResponse) {
                val videos = t.data?.video?.download
                videos?.let {
                    if(it.isNotEmpty()){
                        LogUtils.dTag("winton","video bean${it.size}")
                        onDecodeSuccess(it[it.size-1].url)
                    }
                }
            }

            override fun onFail(code: ErrorCode, errorMsg: String) {
                when(code){
                    ErrorCode.DATA_ERROR ->{
                        decodePath(url)
                    }
                    else ->{
                        onDecodeFailure(code)
                    }
                }
            }
        })
    }

    private fun addJs(webView: WebView) {
        webView.loadUrl("javascript:(function  getVideoPath(){" +
                "var videos = document.getElementsByTagName(\"video\");" +
                "var path = videos[0].src;" +
                "window.chaychan.onGetPath(path);" +
                "})()")

    }

    abstract fun onDecodeSuccess(url: String?)

    abstract fun onDecodeFailure(code:ErrorCode)

    class ParseFunc(val listener:ParseListener){

        @JavascriptInterface
        fun onReceiveParams(r:String,s:String){
            this.listener.onGetParams(r,s)
        }
        @JavascriptInterface
        fun onGetPath(path:String){
            this.listener.onGetPath(path)
        }
    }

    interface ParseListener{
        fun onGetParams(r: String, s: String)
        fun onGetPath(path: String)
    }

}