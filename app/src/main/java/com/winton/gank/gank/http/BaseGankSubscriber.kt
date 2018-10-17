package com.winton.gank.gank.http

import android.support.v4.content.ContextCompat
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.http.response.BaseGankResponse
import io.reactivex.FlowableSubscriber
import org.reactivestreams.Subscription

/**
 * @author: winton
 * @time: 2018/10/9 下午8:53
 * @desc: 描述
 */
abstract class BaseGankSubscriber<T:BaseGankResponse>:FlowableSubscriber<T> {

    lateinit var mSub:Subscription

    override fun onComplete() {
    }

    override fun onSubscribe(s: Subscription) {
        mSub = s
        start()
        if(!NetworkUtils.isAvailableByPing()){
            onFail(ErrorCode.NETWORK_ERROR, App.INSTANCE.getString(R.string.network_error))
        }else{
            mSub.request(Long.MAX_VALUE)
        }
    }

    override fun onNext(t: T) {
        if(t.error){
            LogUtils.e(t)
            onFail(ErrorCode.DATA_ERROR,App.INSTANCE.getString(R.string.request_error))
        }else{
            onSuccess(t)
        }
    }

    override fun onError(t: Throwable?) {
        onFail(ErrorCode.SERVER_ERROR,App.INSTANCE.getString(R.string.server_error))
        t?.printStackTrace()
        LogUtils.e(t)
    }

    /**
     * 数据获取成功
     */
    abstract fun onSuccess(t:T)

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