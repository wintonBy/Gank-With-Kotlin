package com.winton.gank.gank.http

import com.blankj.utilcode.util.NetworkUtils
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.http.response.JDListResponse
import io.reactivex.FlowableSubscriber
import org.reactivestreams.Subscription

/**
 * @author: winton
 * @time: 2018/10/31 11:19 AM
 * @desc: 煎蛋订阅者基类
 */
abstract class BaseJDSubscriber:FlowableSubscriber<JDListResponse> {
    lateinit var mSub:Subscription

    override fun onComplete() {

    }

    override fun onSubscribe(s: Subscription) {
        mSub = s
        onStart()
        if(!NetworkUtils.isConnected()){
            onFail(ErrorCode.NETWORK_ERROR, App.INSTANCE.getString(R.string.network_error))
        }else{
            mSub.request(Long.MAX_VALUE)
        }
    }

    override fun onNext(t: JDListResponse) {
        if("ok" == t.status){
            onSuccess(t)
        }else{
            onFail(ErrorCode.DATA_ERROR,App.INSTANCE.getString(R.string.data_error))
        }

    }
    override fun onError(t: Throwable) {
        onFail(ErrorCode.SERVER_ERROR, App.INSTANCE.getString(R.string.server_error))
    }

    abstract fun onSuccess(response: JDListResponse)

    abstract fun onFail(code:ErrorCode,msg:String)

    open fun onStart(){

    }
}