package com.jh352160.library.network.retrofit;

/**
 * ProgressCancelListener
 */

public interface SubscriberOnSuccessListener<T> {
    void onSuccess(T t);

    void onError(int code, String msg);
}
