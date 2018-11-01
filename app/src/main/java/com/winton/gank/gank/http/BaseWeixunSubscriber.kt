package com.winton.gank.gank.http

import com.blankj.utilcode.util.NetworkUtils
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.http.response.WeixunResponse
import io.reactivex.FlowableSubscriber
import org.reactivestreams.Subscription

/**
 * @author: winton
 * @time: 2018/11/1 3:58 PM
 * @desc: weixun 订阅者基类
 */
abstract class BaseWeixunSubscriber:FlowableSubscriber<WeixunResponse>{

    lateinit var mSub:Subscription

    override fun onComplete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSubscribe(s: Subscription) {
        mSub = s
        start()
        if(!NetworkUtils.isConnected()){
            onFail(ErrorCode.NETWORK_ERROR, App.INSTANCE.getString(R.string.network_error))
        }else{
            mSub.request(Long.MAX_VALUE)
        }
    }

    override fun onNext(t: WeixunResponse) {
        if("success"==t.message){
            onSuccess(t)
        }else{
            onFail(ErrorCode.DATA_ERROR,App.INSTANCE.getString(R.string.data_error))
        }
    }

    override fun onError(t: Throwable) {
        onFail(ErrorCode.SERVER_ERROR, App.INSTANCE.getString(R.string.server_error))
    }

    open fun start(){

    }
    abstract fun onSuccess(t: WeixunResponse)

    abstract fun onFail(code:ErrorCode,msg:String)
}