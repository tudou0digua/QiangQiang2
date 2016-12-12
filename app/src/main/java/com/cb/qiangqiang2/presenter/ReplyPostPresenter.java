package com.cb.qiangqiang2.presenter;

import com.cb.qiangqiang2.common.base.BasePresenter;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.ReplyPostBody;
import com.cb.qiangqiang2.data.model.ReplyPostModel;
import com.cb.qiangqiang2.mvpview.ReplyPostMvpView;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by cb on 2016/12/12.
 */

public class ReplyPostPresenter extends BasePresenter<ReplyPostMvpView> {

    @Inject
    public ReplyPostPresenter() {
    }

    public void replyPost(String content, int fid, int tid) {
        if (getMvpView() != null) getMvpView().showLoading();
        //组装回复json
        ReplyPostBody replyPostBody = new ReplyPostBody();
        ReplyPostBody.BodyBean bodyBean = new ReplyPostBody.BodyBean();
        ReplyPostBody.BodyBean.JsonBean jsonBean = new ReplyPostBody.BodyBean.JsonBean();
        jsonBean.setFid(fid);
        jsonBean.setTid(tid);
        List<ReplyPostBody.BodyBean.JsonBean.ContentBean> list = new ArrayList<>();
        ReplyPostBody.BodyBean.JsonBean.ContentBean contentBean = new ReplyPostBody.BodyBean.JsonBean.ContentBean();
        contentBean.setType(0);
        contentBean.setInfor(content);
        list.add(contentBean);
        String contentStr = new Gson().toJson(list);
        Logger.d(contentStr);
        jsonBean.setContent(contentStr);
        bodyBean.setJson(jsonBean);
        replyPostBody.setBody(bodyBean);

        String json = new Gson().toJson(replyPostBody);
        Logger.json(json);
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        map.put(Constants.ACT, "reply");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), json);
        HttpManager.isNeedFormatDataLogger = true;
        HttpManager.toSub(mApiService.replyPost(map, requestBody), new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                if (getMvpView() == null) return;
                getMvpView().hideLoading();
                if (result != null) {
                    getMvpView().replyResult((ReplyPostModel) result);
                } else {
                    getMvpView().loadError(null);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (getMvpView() == null) return;
                getMvpView().hideLoading();
                getMvpView().loadError(e);
            }
        }, mContext);

    }

}
