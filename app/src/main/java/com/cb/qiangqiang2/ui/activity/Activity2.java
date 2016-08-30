package com.cb.qiangqiang2.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseAutoLayoutActivity;
import com.cb.qiangqiang2.data.api.ApiService;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.LoginResult;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

public class Activity2 extends BaseAutoLayoutActivity {
    @BindView(R.id.btn)
    Button button;

    @BindView(R.id.tv)
    TextView tv;

    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_2;
    }

    @OnClick({R.id.btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
//                Intent intent = new Intent(mContext, Activity2.class);
//                startActivity(intent);
//                login();
                getTopicList();
                break;
        }
    }

    private void getTopicList() {
        Map<String, String> map = HttpManager.getBaseMap();
        map.put("", "");
        map.put("", "");
        map.put("", "");
        map.put("", "");
        map.put("", "");
        map.put("", "");
        map.put("", "");

        HttpManager.toSubscribe(apiService.getTopicList(map), new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(Activity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Object o) {
                if (o != null) {
                    String gson = new Gson().toJson(o);
                    tv.setText(gson);
                    Logger.json(gson);
                }
            }
        });
    }

    private void login() {
        Map<String, String> map = new HashMap<>();
        map.put("username", "testabc0000001");
        map.put("password", "123456qwe");
//        map.put("", "");
        Observable<LoginResult> observable = apiService.login(map);
        HttpManager.toSubscribe(observable, new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(Activity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Object o) {
                if (o != null) {
                    String gson = new Gson().toJson(o);
                    tv.setText(gson);
                    Logger.json(gson);
                }
            }
        });
    }
}
