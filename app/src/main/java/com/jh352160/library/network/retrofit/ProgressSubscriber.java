package com.jh352160.library.network.retrofit;

import android.content.Context;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;


/**
 * ProgressSubscriber
 */

public class ProgressSubscriber<T> extends Subscriber<ResCode> implements ProgressCancelListener {

    private SubscriberOnSuccessListener<T> mListener;
    private Context mContext;
    private ProgressDialogHandler mHandler;

    public ProgressSubscriber(SubscriberOnSuccessListener<T> listener, Context context) {
        this.mListener = listener;
        this.mContext = context;
        mHandler = new ProgressDialogHandler(context, this, true);
    }

    public ProgressSubscriber(SubscriberOnSuccessListener<T> listener, Context context, boolean isProgress) {
        this.mListener = listener;
        this.mContext = context;
        if (isProgress) {
            mHandler = new ProgressDialogHandler(context, this, true);
        }
    }

    private void showProgressDialog() {
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mHandler = null;
        }
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {

        } else if (e instanceof ConnectException) {
            Toast.makeText(mContext,"网络中断，请检查您的网络状态",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext,"网络中断，请检查您的网络状态",Toast.LENGTH_LONG).show();
        }
        dismissProgressDialog();
        if (mListener != null) {
            mListener.onError(-1, String.valueOf(e.getMessage()));
        }
    }

    @Override
    public void onNext(ResCode t) {
        if (mListener != null) {
            if (t.getCode() == 200) {
                mListener.onSuccess((T) t.getData());
            } else {
                Toast.makeText(mContext,t.getMessage(),Toast.LENGTH_LONG).show();
                mListener.onError(t.getCode(), t.getMessage());
            }
        }
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
