package com.jh352160.library.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class TimeUtil {

    public static final String TAG = "TimeUtil";
    /**
     * 倒计时时间
     */
    public static final int CUTDOWN_TIME = 60;
    /**
     * 倒计时线程
     */
    private Thread timeThread;
    /**
     * 判断是否倒计时
     */
    private boolean flag;
    /**
     * 倒计时长:秒
     */
    private int setTime;
    /**
     * 回调
     */
    private Handler handler;

    /**
     * 设置回调
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * 设置倒计时时间
     */
    public void setTime(int time) {
        this.setTime = time;
    }

    /**
     * 开启倒计时
     */
    public void start() {
        Log.i(TAG, "thread_start");
        if (timeThread == null) {
            if (setTime == 0) {
                setTime = CUTDOWN_TIME;
            }
            timeThread = new Thread(timeRunnable, "thread-1");
            flag = true;
            timeThread.start();
        }
    }

    /**
     * 关闭倒计时
     */
    public void stop() {
        Log.i(TAG, "thread_stop");
        if (timeThread != null) {
            flag = false;
            timeThread = null;
        }
    }

    /**
     * 计算倒计时
     */
    private Runnable timeRunnable = new Runnable() {

        @Override
        public void run() {
            while (flag) {

                setTime--;

                sendMesage(handler, CUTDOWN_TIME, setTime);

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (setTime == 0) {
                    stop();
                }
            }
        }
    };

    /**
     * 传递消息.
     */
    public void sendMesage(Handler handler, int position, int arg1) {
        Message msg = handler.obtainMessage();
        msg.what = position;
        msg.arg1 = arg1;
        msg.sendToTarget();
    }

    /**
     * 判断是否开启倒计时
     */
    public boolean isFlag() {
        return flag;
    }

}
