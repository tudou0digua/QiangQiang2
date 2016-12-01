package com.cb.qiangqiang2.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

public class BigImageActivity extends BaseActivity {
    public static final String IMAGE_URL = "image_url";

    @BindView(R.id.photo_view)
    ImageView photoView;

    private String imageUrl;

    public static void startBigImageActivity(String imageUrl, Activity context, View view) {
        Intent intent = new Intent(context, BigImageActivity.class);
        intent.putExtra(IMAGE_URL, imageUrl);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.scale_in, 0);
//        ActivityOptionsCompat compat =
//                ActivityOptionsCompat.makeCustomAnimation(context, R.anim.scale_in, 0);

//        ActivityOptionsCompat compat =
//                ActivityOptionsCompat.makeScaleUpAnimation(view, view.getTop(), view.getLeft(), view.getWidth(), view.getHeight());
//        ActivityOptionsCompat.makeSceneTransitionAnimation(context,
//                        view, context.getString(R.string.big_image_transition));
//        ActivityCompat.startActivity(context, intent, compat.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_big_image;
    }

    @Override
    protected void injectActivity() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        imageUrl = getIntent().getStringExtra(IMAGE_URL);
//        imageUrl = imageUrl.replace("middle", "large");
        Logger.e(imageUrl);
    }

    @Override
    protected void initView() {
        if (imageUrl != null) {
            Glide.with(mContext)
                    .load(imageUrl)
                    .into(photoView);
        }
    }

    @OnClick({R.id.photo_view})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.photo_view:
                finishActivity();
                break;
        }
    }

    private void finishActivity() {
        finish();
//        ActivityCompat.finishAfterTransition(BigImageActivity.this);
    }
}
