package com.winton.gank.gank.http

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.winton.gank.gank.App
import com.winton.gank.gank.constant.DeveloperConfig
import com.winton.gank.gank.http.retrofit.GankRetrofitClient
import com.winton.gank.gank.http.retrofit.JDRetrofitClient
import com.winton.gank.gank.http.retrofit.WeixunRetrofitClient
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * @author: winton
 * @time: 2018/10/9 下午4:08
 * @desc: Retrofit 客户端
 */
object RetrofitHolder {
    /**
     * 默认超时时间
     */
    private const val DEFAULT_TIMEOUT = 15L

    private val mOkHttpClient:OkHttpClient

    private var gankInstance:GankRetrofitClient? = null
    private var jdInstance:JDRetrofitClient? = null
    private var wInstance:WeixunRetrofitClient? = null


    init {
        val cookieJar = PersistentCookieJar(SetCookieCache(),SharedPrefsCookiePersistor(App.INSTANCE))
        var okHttpBuilder = OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .cookieJar(cookieJar)
        if(DeveloperConfig.isDebug){
            okHttpBuilder.addInterceptor(LoggerInterceptor("Okhttp"))
        }

        mOkHttpClient = okHttpBuilder.build()

    }

    /**
     * 获取Gank server
     */
    fun gankInstance():GankRetrofitClient{
        if(gankInstance == null){
            gankInstance = GankRetrofitClient(mOkHttpClient)
        }
        return gankInstance!!
    }

    /**
     * 获取煎蛋 Server
     */
    fun jdInstance():JDRetrofitClient{
        if(jdInstance == null){
            jdInstance = JDRetrofitClient(mOkHttpClient)
        }
        return jdInstance!!
    }

    /**
     * 获取weixun 客户端
     */
    fun wInstance():WeixunRetrofitClient{
        if(wInstance == null){
            wInstance = WeixunRetrofitClient(mOkHttpClient)
        }
        return wInstance!!;
    }
}