package com.cb.qiangqiang2.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.common.util.AppUtils;
import com.cb.qiangqiang2.data.model.ImageParamBean;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

public class BigImageActivity extends BaseActivity {
    public static final String IMAGE_URL = "image_url";
    public static final String IMAGE_PARAM_BEAN = "image_param_bean";
    private static final int ANIMATOR_DURATION = 500;

    @BindView(R.id.image_view)
    PhotoView imageView;
    @BindView(R.id.tv_bg)
    TextView tvBg;

    private String imageUrl;
    private ImageParamBean imageParamBean;
    private AnimatorSet enterAnimatorSet;
    private AnimatorSet exitAnimatorSet;

    public static void startBigImageActivity(String imageUrl, Activity context, ImageView view) {
        Intent intent = new Intent(context, BigImageActivity.class);
        intent.putExtra(IMAGE_URL, imageUrl);

        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        Logger.e("rect.left : " + rect.left);
        Logger.e("rect.top : " + rect.top);
        Logger.e("view.getLeft() : " + view.getLeft());
        Logger.e("view.getTop() : " + view.getTop());

        ImageParamBean imageParamBean = new ImageParamBean();
        imageParamBean.setViewTop(rect.top);
        imageParamBean.setViewLeft(rect.left);
        imageParamBean.setViewWidth(view.getWidth());
        imageParamBean.setViewHeight(view.getWidth());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        imageParamBean.setImageWidth(bitmap.getWidth());
        imageParamBean.setImageHeight(bitmap.getHeight());
        bitmap = null;

        intent.putExtra(IMAGE_PARAM_BEAN, imageParamBean);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.big_image_in_out, 0);

//        ActivityOptionsCompat compat;
//        compat =
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
        imageParamBean = getIntent().getParcelableExtra(IMAGE_PARAM_BEAN);
    }

    @Override
    protected void initView() {
//        if (imageParamBean != null) {
//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
//            int screenWidth = AppUtils.getWidth(BigImageActivity.this);
//            layoutParams.width = screenWidth;
//            layoutParams.height = (int) (screenWidth * (imageParamBean.getImageHeight() * 1.0f / imageParamBean.getImageWidth()));
//        }

//        imageView.enable();

        if (imageUrl != null) {
            //TODO 加载Gif有时候不显示
            Glide.with(mContext)
                    .load(imageUrl)
                    .into(imageView);
        }
        enterAnimator();
    }

    private void enterAnimator() {
        if (imageParamBean != null) {
            final int screenWidth = AppUtils.getWidth(BigImageActivity.this);
            int screenHeight = AppUtils.getHeight(BigImageActivity.this);
            int originWidth = imageParamBean.getViewWidth();
            int originHeight = imageParamBean.getViewHeight();
            int deltaY = imageParamBean.getViewTop() - (screenHeight - originHeight) / 2;
            float startScale = originWidth * 1.0f / screenWidth;
            enterAnimatorSet = new AnimatorSet();
            ObjectAnimator moveY = ObjectAnimator.ofFloat(imageView, "translationY", deltaY, 0);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", startScale, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", startScale, 1f);
            enterAnimatorSet.setDuration(500);
            enterAnimatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
            enterAnimatorSet.play(scaleX).with(moveY).with(scaleY);
            enterAnimatorSet.start();

        }
        ObjectAnimator bgAlpha = ObjectAnimator.ofFloat(tvBg, "alpha", 0f, 1f);
        bgAlpha.setInterpolator(new AccelerateDecelerateInterpolator());
        bgAlpha.setDuration(ANIMATOR_DURATION);
        bgAlpha.start();
    }

    @OnClick({R.id.image_view, R.id.tv_bg})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.image_view:
                finishActivity();
                break;
            case R.id.tv_bg:
                finishActivity();
                break;
        }
    }

    private void finishActivity() {
        exitAnimator();
//        ActivityCompat.finishAfterTransition(BigImageActivity.this);
    }

    @Override
    public void onBackPressed() {
        exitAnimator();
    }

    private void exitAnimator() {
        if (imageParamBean != null && !enterAnimatorSet.isRunning()) {
            int screenWidth = AppUtils.getWidth(BigImageActivity.this);
            int screenHeight = AppUtils.getHeight(BigImageActivity.this);
            int originWidth = imageParamBean.getViewWidth();
            int originHeight = imageParamBean.getViewHeight();
            int deltaY = imageParamBean.getViewTop() - (screenHeight - originHeight) / 2;
            // UserInfoActivity没有延伸到状态栏时，重新回到前台，会有个状态栏向下出现的过程
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//                deltaY -= AppUtils.getStatusBarHeight(mContext);
            }
            float startScale = originWidth * 1.0f / screenWidth;
            enterAnimatorSet = new AnimatorSet();
            ObjectAnimator moveY = ObjectAnimator.ofFloat(imageView, "translationY", 0, deltaY);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, startScale);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, startScale);
            enterAnimatorSet.setDuration(500);
            enterAnimatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
            enterAnimatorSet.play(scaleX).with(moveY).with(scaleY);
            enterAnimatorSet.start();

            ObjectAnimator bgAlpha = ObjectAnimator.ofFloat(tvBg, "alpha", 1.0f, 0f);
            bgAlpha.setInterpolator(new AccelerateDecelerateInterpolator());
            bgAlpha.setDuration(ANIMATOR_DURATION);
            bgAlpha.start();
        }
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, ANIMATOR_DURATION - 1);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.big_image_in_out);
    }
}
