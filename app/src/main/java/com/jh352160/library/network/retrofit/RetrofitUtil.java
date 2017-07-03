package com.jh352160.library.network.retrofit;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RetrofitUtil
 */

public class RetrofitUtil {

    private static Retrofit singleton;
    private static Retrofit chatSingleton;

    public static Retrofit createApi(Context context) {
        if (singleton == null) {
            synchronized (RetrofitUtil.class) {
                if (singleton == null) {
                    singleton = new Retrofit.Builder()
                            .client(OkHttpUtils.getInstance(context.getApplicationContext()))
                            .baseUrl("")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return singleton;
    }

    public static Retrofit createPushApi(Context context) {
        if (chatSingleton == null) {
            synchronized (RetrofitUtil.class) {
                if (chatSingleton == null) {
                    chatSingleton = new Retrofit.Builder()
                            .client(OkHttpUtils.getInstance(context.getApplicationContext()))
                            .baseUrl("")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return chatSingleton;
    }

    public static <T> T create(Context context, Class<T> service) {
        singleton = createApi(context);
        final Object serviceImpl = singleton.create(service);
        return (T) java.lang.reflect.Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Observable observable = (Observable) method.invoke(serviceImpl, args);
                return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        });
    }

    public static <T> T createPush(Context context, Class<T> service) {
        chatSingleton = createPushApi(context);
        final Object serviceImpl = chatSingleton.create(service);
        return (T) java.lang.reflect.Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Observable observable = (Observable) method.invoke(serviceImpl, args);
                return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        });
    }
}
