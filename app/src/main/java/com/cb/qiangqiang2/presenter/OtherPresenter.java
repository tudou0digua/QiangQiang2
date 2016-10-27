package com.cb.qiangqiang2.presenter;

import android.content.Context;
import android.widget.Toast;

import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.data.api.ApiService;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.BaseModel;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by cb on 2016/10/27.
 */

public class OtherPresenter {
    @Inject
    @ForActivity
    public Context mContext;

    @Inject
    public ApiService mApiService;

    @Inject
    public OtherPresenter() {

    }

    public void setCollectionStatus(String action, String idType, int id) {
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        Observable<BaseModel> observable;
        map.put(Constants.ACTION, action);
        map.put(Constants.ID_TYPE, idType);
        map.put(Constants.ID, String.valueOf(id));
        observable = mApiService.setCollectionStatus(map);
        HttpManager.isNeedFormatDataLogger = true;
        HttpManager.toSub(observable, new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                BaseModel baseModel = (BaseModel) result;
                Toast.makeText(mContext, baseModel.getHead().getErrInfo(), Toast.LENGTH_SHORT).show();
                switch (baseModel.getHead().getErrCode()) {
                    //收藏成功
                    case 2000030:

                        break;
                    //取消收藏成功
                    case 0:

                        break;
                    //已收藏，重复收藏
                    case 2000029:

                        break;
                    //未收藏，进行取消收藏
                    case 2000031:

                        break;
                    //抱歉，您指定的信息无法收藏(传的参数不对时)
                    case 2000028:

                        break;
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext);
    }

    public void setFollowStatus(int userId, String type) {
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        Observable<BaseModel> observable;
        map.put(Constants.UID, String.valueOf(userId));
        map.put(Constants.TYPE, type);
        observable = mApiService.setFollowStatus(map);
        HttpManager.isNeedFormatDataLogger = true;
        HttpManager.toSub(observable, new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                BaseModel baseModel = (BaseModel) result;
                Toast.makeText(mContext, baseModel.getHead().getErrInfo(), Toast.LENGTH_SHORT).show();
                switch (baseModel.getHead().getErrCode()) {
                    //关注成功
                    case 2000023:

                        break;
                    //取消关注成功 未关注，进行取消关注(也提示取消关注成功)
                    case 2000024:

                        break;
                    //已关注，重关注
                    case 2000022:

                        break;
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext);
    }
}