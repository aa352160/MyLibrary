package com.jh352160.library.network.retrofit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jh352160.library.util.SharedPreferenceUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @FileName OkHttpUtils
 * @Description okHttp 请求服务器
 * @Author guying
 * @Date 2015-09-08 17:16
 * @Version V 1.0
 */
public class OkHttpUtils {

    private static OkHttpClient okHttpClinet;
    private static final int TIME_OUT = 30;//超时 秒作为单位 SECONDS

    private static final String TAG = "OkHttpUtils";
    private static final long SIZE_OF_CACHE = 10 * 1024;//缓存大小

    public static OkHttpClient getInstance(final Context context) {
        if (okHttpClinet == null) {
            synchronized (OkHttpUtils.class) {
                if (okHttpClinet == null) {

                    okHttpClinet = new OkHttpClient.Builder()
                            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    String token = SharedPreferenceUtil.getInstance(context).readTempData("token");

                                    Request original = chain.request();

                                    Request.Builder requestBuilder = original.newBuilder();

                                    requestBuilder.header("Accept", "application/json");

                                    if (!TextUtils.isEmpty(token)) {
                                        requestBuilder.header("token", token);
                                    }

                                    requestBuilder.method(original.method(), original.body());

                                    Request request = requestBuilder.build();

                                    long t1 = System.nanoTime();
                                    String requestLog = String.format("Sending request %s on %s%n%s",
                                            request.url(), chain.connection(), request.headers());

                                    Response response = chain.proceed(request);

                                    long t2 = System.nanoTime();
                                    String responseLog = String.format("Received response for %s in %.1fms%n%s",
                                            response.request().url(), (t2 - t1) / 1e6d, response.headers());

                                    String bodyString = response.body().string();

                                    Log.i("TAG", "request=" + requestLog + "response=" + responseLog + "body=" + bodyString);

                                    return response.newBuilder().body(ResponseBody.create(response.body().contentType(), bodyString)).build();
                                }
                            })
                            .build();

                    //设置缓存(缓存会对http get操作生效，post操作不会被缓存)
//                    try {
//                        Cache responseCache = new Cache(context.getCacheDir(), SIZE_OF_CACHE);
//                        okHttpClinet.setCache(responseCache);
//                    } catch (Exception e) {
//                        Log.d(TAG, "Unable to set http cache", e);
//                    }
                }
            }
        }
        return okHttpClinet;
    }

}
