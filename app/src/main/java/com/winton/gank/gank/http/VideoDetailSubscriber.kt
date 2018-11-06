package com.winton.gank.gank.http

import com.blankj.utilcode.util.NetworkUtils
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.http.response.VideoDetailResponse
import io.reactivex.FlowableSubscriber
import org.reactivestreams.Subscription

/**
 * @author: winton
 * @time: 2018/11/6 2:40 PM
 * @desc: 描述
 */
abstract class VideoDetailSubscriber:FlowableSubscriber<VideoDetailResponse> {
    lateinit var mSub:Subscription

    override fun onComplete() {
    }

    override fun onSubscribe(s: Subscription) {
        mSub = s
        start()
        if(NetworkUtils.isConnected()){
            s.request(Long.MAX_VALUE)
        }else{
            onFail(ErrorCode.NETWORK_ERROR, App.INSTANCE.getString(R.string.network_error))
        }
    }

    override fun onNext(t: VideoDetailResponse) {
        when(t.retCode){
            200 ->{
                //请求成功
                onSuccess(t)
            }
            else ->{
                //请求失败，重试
                onFail(ErrorCode.DATA_ERROR,App.INSTANCE.getString(R.string.data_error))
            }
        }
    }

    override fun onError(t: Throwable?) {
        onFail(ErrorCode.SERVER_ERROR,App.INSTANCE.getString(R.string.server_error))
    }

    /**
     * 数据获取成功
     */
    abstract fun onSuccess(t:VideoDetailResponse)

    /**
     * 数据获取失败
     */
    abstract fun onFail(code:ErrorCode,errorMsg:String)

    /**
     * 请求开始
     */
    open fun start(){

    }
}