package com.cb.qiangqiang2.test.activity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseAutoLayoutActivity;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.data.api.ApiService;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.LoginModel;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

public class Activity2 extends BaseAutoLayoutActivity {
    @Inject
    ApiService apiService;

    @BindView(R.id.tv_content)
    TextView tv;

    GestureDetector gestureDetector;
    @BindView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        init();
    }

    private void init() {
        gestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(mContext, "这是双击事件", Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
//                Toast.makeText(mContext, "单击击事件 onSingleTapUp", Toast.LENGTH_SHORT).show();
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Toast.makeText(mContext, "单击击事件 onSingleTapConfirmed", Toast.LENGTH_SHORT).show();
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "单击击事件", Toast.LENGTH_SHORT).show();
            }
        });
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_2;
    }

    @OnClick({R.id.btn, R.id.btn_login, R.id.btn_collection})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
//                Intent intent = new Intent(mContext, Activity2.class);
//                startActivity(intent);
//                login();
                getTopicList();
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_collection:
//                getCollection();
                getCollection2();
                break;
        }
    }

    private void getCollection2() {
        int uid = PrefUtils.getInt(mContext, Constants.UID);
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        map.put(Constants.PAGE_SIZE, String.valueOf(1));
        map.put(Constants.TYPE, "favorite");
        HttpManager.isNeedFormatDataLogger = true;
        HttpManager.toSub(apiService.getCollectionList2(map, uid), new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                if (result != null) {
                    String gson = new Gson().toJson(result);
                    tv.setText(gson);
                    Logger.json(gson);
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(Activity2.this, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }, mContext);
    }

    private void getCollection() {
        int uid = PrefUtils.getInt(mContext, Constants.UID);
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        map.put(Constants.PAGE_SIZE, String.valueOf(1));
        map.put(Constants.TYPE, "favorite");
        HttpManager.toSubscribe(apiService.getCollectionList(map, uid), new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(Activity2.this, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void getTopicList() {
        Map<String, String> map = HttpManager.getBaseMap(mContext);

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
//                    Logger.json(gson);
                }
            }
        });
    }

    private void login() {
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        map.put("username", "testabc0000001");
        map.put("password", "123456qwe");
        Observable<LoginModel> observable = apiService.login(map);
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
                    LoginModel loginModel = (LoginModel) o;
                    PrefUtils.putString(mContext, Constants.ACCESS_TOKEN, loginModel.getToken());
                    PrefUtils.putString(mContext, Constants.ACCESS_SECRET, loginModel.getSecret());
                    PrefUtils.putInt(mContext, Constants.UID, loginModel.getUid());
                }
            }
        });
    }
}
