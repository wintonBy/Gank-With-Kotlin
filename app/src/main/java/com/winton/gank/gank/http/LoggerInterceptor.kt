package com.winton.gank.gank.http

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.Response
import okio.Buffer


/**
 * @author: winton
 * @time: 2018/10/10 上午10:44
 * @desc: 日志拦截器
 */
class LoggerInterceptor : Interceptor {

    private val tag:String = "OkHttp"


    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        logRequest(request)
        var response = chain.proceed(request)
        logResponse(response)
        return response
    }

    private fun logResponse(response: Response?) {
        val resTime = System.currentTimeMillis()
        Log.e(tag, "========response'log=======start")
        response?.let {
            val builder = it.newBuilder()
            val clone = builder.build()
            Log.e(tag, "url : " + clone.request().url())
            Log.e(tag, "code : " + clone.code())
            Log.e(tag, "protocol : " + clone.protocol())
            clone.message()?.let { Log.e(tag, "message : $it") }

            val headers = clone.headers()
            if (headers != null && headers.size() > 0) {
                Log.e(tag, "headers : " + headers.toString())
            }
            var body = clone.body()
            body?.let {
                it.contentType()?.let {
                    if (isText(it)) {
                        val resp = body.string()
                        Log.e(tag, "responseBody's content : $resp")
                    } else {
                        Log.e(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!")
                    }
                }
            }
        }
        Log.e(tag, "========response'log=======end " + (System.currentTimeMillis() - resTime))

    }

    private fun logRequest(request: Request?) {
        val reqTime = System.currentTimeMillis()
        Log.e(tag, "========request'log=======start")
        request?.let {
            val url = it.url().toString()
            val headers = it.headers()
            Log.e(tag, "method : " + it.method())
            Log.e(tag, "url : $url")
            if (headers != null && headers.size() > 0) {
                Log.e(tag, "headers : " + headers.toString())
            }
            val requestBody = it.body()
            requestBody?.let {
                it.contentType()?.let {
                    Log.e(tag, "requestBody's contentType : " + it.toString())
                }
            }
            Log.e(tag, "requestBody's content : " + bodyToString(it))
        }
        Log.e(tag, "========request'log=======end " + (System.currentTimeMillis() - reqTime))
    }

    private fun bodyToString(request: Request): String? {
        val copy = request.newBuilder().build()
        val buffer = Buffer()
        copy.body()?.writeTo(buffer)
        return buffer.readUtf8()

    }

    private fun isText(mediaType: MediaType): Boolean {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml"))
                return true
        }
        return false
    }
}