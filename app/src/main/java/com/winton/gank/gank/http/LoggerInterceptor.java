package com.winton.gank.gank.http;

import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * @author: winton
 * @time: 2018/6/12 16:22
 * @package: com.af.smartfactorypad.network
 * @project: SmartFactoryPad
 * @mail:
 * @describe: 日志拦截器
 */
public class LoggerInterceptor implements Interceptor{
    public static final String TAG = "OkHttp";
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag, boolean showResponse)
    {
        if (TextUtils.isEmpty(tag))
        {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public LoggerInterceptor(String tag)
    {
        this(tag, true);
    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response)
    {
        try
        {
            //===>response log
            long resTime = System.currentTimeMillis();
            Log.e(tag, "========response'log=======start");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            Log.e(tag, "url : " + clone.request().url());
            Log.e(tag, "code : " + clone.code());
            Log.e(tag, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message()))
                Log.e(tag, "message : " + clone.message());

            Headers headers = clone.headers();
            if (headers != null && headers.size() > 0) {
                Log.e(tag, "headers : " + headers.toString());
            }

            if (showResponse)
            {
                ResponseBody body = clone.body();
                if (body != null)
                {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null)
                    {
                        if (isText(mediaType))
                        {
                            String resp = body.string();
                            Log.e(tag, "responseBody's content : " + resp);

                            body = ResponseBody.create(mediaType, resp);
                            Log.e(tag, "========response'log=======end " + (System.currentTimeMillis()-resTime));
                            return response.newBuilder().body(body).build();
                        } else
                        {
                            Log.e(tag, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }


        } catch (Exception e)
        {
            LogUtils.e(e);
        }


        return response;
    }

    private void logForRequest(Request request)
    {
        try
        {
            String url = request.url().toString();
            Headers headers = request.headers();

            long reqTime = System.currentTimeMillis();
            Log.e(tag, "========request'log=======start");
            Log.e(tag, "method : " + request.method());
            Log.e(tag, "url : " + url);
            if (headers != null && headers.size() > 0)
            {
                Log.e(tag, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null)
            {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null)
                {
                    Log.e(tag, "requestBody's contentType : " + mediaType.toString());
                    Log.e(tag, "requestBody's content : " + bodyToString(request));
                }
            }
            Log.e(tag, "========request'log=======end " + (System.currentTimeMillis()-reqTime));
        } catch (Exception e)
        {
        }
    }

    private boolean isText(MediaType mediaType)
    {
        if (mediaType.type() != null && mediaType.type().equals("text"))
        {
            return true;
        }
        if (mediaType.subtype() != null)
        {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request)
    {
        try
        {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e)
        {
            return "something error when show requestBody.";
        }
    }
}
