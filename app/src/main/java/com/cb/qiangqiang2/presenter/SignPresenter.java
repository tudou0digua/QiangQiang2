package com.cb.qiangqiang2.presenter;

import android.text.TextUtils;

import com.cb.qiangqiang2.common.base.BasePresenter;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.ToastUtil;
import com.cb.qiangqiang2.data.UserManager;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.event.SignResultEvent;
import com.cb.qiangqiang2.mvpview.CheckInMvpView;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

/**
 * 签到
 * Created by cb on 2016/10/24.
 */

public class SignPresenter extends BasePresenter<CheckInMvpView> {

    @Inject
    UserManager userManager;

    @Inject
    public SignPresenter() {

    }

    /**
     * 加载签到页面，获取其中formhash值
     */
    public void sign() {
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        HttpManager.toSub(mApiService.getCheckInPage(map), new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                try {
                    String jsonString = ((okhttp3.ResponseBody)((retrofit2.Response) result).body()).string();
                    if (jsonString != null && jsonString.contains("先登录")) {
                        EventBus.getDefault().post(new SignResultEvent(SignResultEvent.TYPE_UN_LOGIN));
                        return;
                    }
                    String formHash = null;
                    String[] strs = jsonString.split("formhash=");
                    if (strs.length > 2){
                        String str1 = strs[1];
                        formHash = str1.substring(0, str1.indexOf("&"));
                        formHash = formHash.split("\"")[0];
                    }
                    Logger.e(formHash);
                    if (!TextUtils.isEmpty(formHash)){
                        postCheckIn(formHash);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new SignResultEvent(SignResultEvent.TYPE_SIGN_FAIL));
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                EventBus.getDefault().post(new SignResultEvent(SignResultEvent.TYPE_SIGN_FAIL));
            }
        }, mContext);
    }

    /**
     * 签到
     * @param formHash
     */
    public void postCheckIn(String formHash) {
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        map.put(Constants.FORMHASH, formHash);
        map.put(Constants.QDXQ, "kx");
        map.put(Constants.QD_MODE, "2");
        map.put(Constants.TODAY_SAY, "");
        map.put(Constants.FAST_REPLY, "0");
        HttpManager.toSub(mApiService.checkIn(map), new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                try {
                    String jsonString = ((okhttp3.ResponseBody)((retrofit2.Response) result).body()).string();
                    Logger.d(jsonString);
                    if (jsonString.contains("签到成功")) {
//                        Intent intent = new Intent(mContext, WebViewActivity.class);
//                        intent.putExtra(WebViewActivity.TITLE,"签到");
//                        intent.putExtra(WebViewActivity.URL,"http://www.qiangqiang5.com/plugin.php?id=dsu_paulsign:sign");
//                        mContext.startActivity(intent);
                        userManager.saveLastSignSuccessTime();
                        ToastUtil.show(mContext, "签到成功！");
                        EventBus.getDefault().post(new SignResultEvent(SignResultEvent.TYPE_SIGN_SUCESS));
                    } else if (jsonString.contains("已经签到")) {
                        ToastUtil.show(mContext, "今天已经签到！");
                        EventBus.getDefault().post(new SignResultEvent(SignResultEvent.TYPE_ALREADY_SIGN));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new SignResultEvent(SignResultEvent.TYPE_SIGN_FAIL));
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                EventBus.getDefault().post(new SignResultEvent(SignResultEvent.TYPE_SIGN_FAIL));
            }
        }, mContext);
    }

}
