package com.cb.qiangqiang2.presenter;

import android.content.Context;

import com.cb.qiangqiang2.common.base.BasePresenter;
import com.cb.qiangqiang2.common.dagger.qualifier.ForActivity;
import com.cb.qiangqiang2.data.api.ApiService;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.BoardModel;
import com.cb.qiangqiang2.mvpview.BoardMvpView;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by cb on 2016/10/20.
 */

public class BoardPresenter extends BasePresenter<BoardMvpView> {

    @Inject
    ApiService apiService;

    @Inject
    @ForActivity
    Context context;

    @Inject
    public BoardPresenter() {
    }

    public void loadBoardData(){
        Map<String, String> map = HttpManager.getBaseMap(context);
        HttpManager.toSub(apiService.getBoardList(map), new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                if (getMvpView() == null) return;
                if (result != null) {
                    getMvpView().loadBoardData((BoardModel) result);
                }
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().loadError(e);
            }
        }, context);
    }
}
