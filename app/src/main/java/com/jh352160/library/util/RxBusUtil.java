package com.jh352160.library.util;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by jh352160 on 2017/7/3.
 */

public class RxBusUtil {
    private static final String TAG = RxBusUtil.class.getSimpleName();
    private static RxBusUtil instance;
    public static boolean DEBUG = false;

    public static synchronized RxBusUtil get() {
        if (null == instance) {
            instance = new RxBusUtil();
        }
        return instance;
    }

    private RxBusUtil() {
    }

    private ConcurrentHashMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<>();

    /**
     * code example:
     *
     * addOb = RxBus.get().register(Consts.LOGIN_SUCCESS, Object.class);
     * addOb.observeOn(AndroidSchedulers.mainThread())
     *      .subscribe(new Action1<Object>() {
     *          @Override
     *          public void call(Object object) {
     *              getIsReadMessage();
     *          }
     *      });
     */
    public <T> Observable<T> register(Object tag, Class<T> clazz) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (null == subjectList) {
            subjectList = new ArrayList<>();
            subjectMapper.put(tag, subjectList);
        }

        Subject<T, T> subject;
        subjectList.add(subject = PublishSubject.create());
        if (DEBUG) Log.d(TAG, "[register]subjectMapper: " + subjectMapper);
        return subject;
    }

    public void unregister(Object tag, Observable observable) {
        List<Subject> subjects = subjectMapper.get(tag);
        if (!isEmpty(subjects)) {
            subjects.remove(observable);

            if (isEmpty(subjects)) {
                subjectMapper.remove(tag);
            }
        }

        if (DEBUG) Log.d(TAG, "[unregister]subjectMapper: " + subjectMapper);
    }

    /**
     * code example:
     *
     * RxBus.get().post(Consts.LOGIN_SUCCESS, "");
     */
    public void post(Object content) {
        post(content.getClass().getName(), content);
    }

    @SuppressWarnings("unchecked")
    public void post(Object tag, Object content) {
        List<Subject> subjectList = subjectMapper.get(tag);

        if (!isEmpty(subjectList)) {
            for (Subject subject : subjectList) {
                subject.onNext(content);
            }
        }
        if (DEBUG) Log.d(TAG, "[send]subjectMapper: " + subjectMapper);
    }

    private boolean isEmpty(List<Subject> subjectList) {
        return !(subjectList != null && subjectList.size() > 0);
    }
}
