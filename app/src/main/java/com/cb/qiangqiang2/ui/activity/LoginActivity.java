package com.cb.qiangqiang2.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseActivity;
import com.cb.qiangqiang2.data.UserManager;
import com.cb.qiangqiang2.data.model.LoginModel;
import com.cb.qiangqiang2.mvpview.LoginMvpView;
import com.cb.qiangqiang2.presenter.LoginPresenter;

import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements LoginMvpView {

    private static final String IS_NEED_GO_TO_MAIN = "isNeedGoToMain";

    @Inject
    LoginPresenter mLoginPresenter;
    @Inject
    UserManager mUserManager;

    @BindView(R.id.username)
    AutoCompleteTextView mUserNameView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.rl_loading)
    View mProgressView;
    @BindView(R.id.btn_sign_in)
    Button mEmailSignInButton;

    private boolean isNeedGoToMain = false;
    private String username;
    private String password;

    public static void startLoginActivity(Context context) {
        startLoginActivity(context, false);
    }

    public static void startLoginActivity(Context context, boolean isNeedGoToMain) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(IS_NEED_GO_TO_MAIN, isNeedGoToMain);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.slide_in_from_bottom, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void injectActivity() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void attachPresenter() {
        mLoginPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        isNeedGoToMain = getIntent().getBooleanExtra(IS_NEED_GO_TO_MAIN, false);
    }

    @OnClick({R.id.btn_sign_in, R.id.rl_loading})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                attemptLogin();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    /**
     * 登录
     */
    private void attemptLogin() {
        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        username = mUserNameView.getText().toString();
        password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUserNameView.setError(getString(R.string.username_empty));
            focusView = mUserNameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUserNameView.setError(getString(R.string.error_invalid_username));
            focusView = mUserNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mLoginPresenter.login(username, password);
        }
    }

    private boolean isUsernameValid(String username) {
        if (TextUtils.isEmpty(username)) {
            return false;
        }
        String pattern = "^.{3,15}$";
        return Pattern.matches(pattern, username);
    }

    private boolean isPasswordValid(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        //包含数字，且长度大于7
        String pattern = "^(?!\\d+$)(?!\\D+$)\\S{7,}$";
        return Pattern.matches(pattern, password);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void loginResult(LoginModel loginModel) {
        switch (loginModel.getHead().getErrCode()) {
            case "11100001":
                Toast.makeText(mContext, loginModel.getHead().getErrInfo(), Toast.LENGTH_SHORT).show();
                break;
            case "00000000":
                //保存用户信息
                mUserManager.setUserInfo(loginModel);
                //保存账号密码
                mUserManager.saveAccountInfoToLocal(username, password);
                if (isNeedGoToMain) {
                    MainActivity.startActivity(this);
                }
                finish();
                break;
            default:
                Toast.makeText(mContext, loginModel.getHead().getErrInfo(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void showLoading() {
        showProgress(true);
    }

    @Override
    public void hideLoading() {
        showProgress(false);
    }

    @Override
    public void loadError(Throwable e) {
        if (e != null)
            e.printStackTrace();
        Toast.makeText(mContext, R.string.login_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_to_bottom);
    }
}

