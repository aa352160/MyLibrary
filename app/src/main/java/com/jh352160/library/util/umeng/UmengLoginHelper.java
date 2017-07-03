package com.jh352160.library.util.umeng;

/**
 * Created by pan jh on 2017/6/19.
 * 使用该工具类需要在登录界面中完成onActivityResult方法，具体请参考友盟文档:
 * http://dev.umeng.com/social/android/login-page#1
 */

public class UmengLoginHelper {
//    private Activity mContext;
//    private ProgressDialogHandler mHandler;
//    private UmengLoginListener listener;
//
//    public UmengLoginHelper(Activity context,UmengLoginListener listener){
//        this.mContext=context;
//        this.listener=listener;
//        mHandler = new ProgressDialogHandler(context, null, false);
//    }
//
//    public void qqLogin(){
//        UMShareAPI.get(mContext).getPlatformInfo(mContext, SHARE_MEDIA.QQ, getTencentUMAuthListener(mContext,listener));
//    }
//
//    public void wechatLogin(){
//        UMShareAPI.get(mContext).getPlatformInfo(mContext, SHARE_MEDIA.WEIXIN, getTencentUMAuthListener(mContext,listener));
//    }
//
//    private UMAuthListener getTencentUMAuthListener(final Activity context, final UmengLoginListener listener){
//        return new UMAuthListener() {
//            @Override
//            public void onStart(SHARE_MEDIA share_media) {
//                showProgressDialog();
//            }
//
//            @Override
//            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//                dismissProgressDialog();
//                Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();
//                if (listener!=null){
//                    TencentLoginRequest request=new TencentLoginRequest();
//                    request.setOpenid(map.get("uid"));
//                    request.setAccessToken(map.get("accessToken"));
//                    listener.onLoginSuccess(request);
//                }
//            }
//
//            @Override
//            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//                dismissProgressDialog();
//                Toast.makeText(context, "授权错误", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancel(SHARE_MEDIA share_media, int i) {
//                dismissProgressDialog();
//                Toast.makeText(context, "授权取消", Toast.LENGTH_SHORT).show();
//            }
//        };
//    }
//
//    private void showProgressDialog() {
//        if (mHandler != null) {
//            mHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
//        }
//    }
//
//    private void dismissProgressDialog() {
//        if (mHandler != null) {
//            mHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
//            mHandler = null;
//        }
//    }
//
//    public interface UmengLoginListener{
//        void onLoginSuccess(TencentLoginRequest request);
//    }
}
