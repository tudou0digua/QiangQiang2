package com.cb.qiangqiang2.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cb.qiangqiang2.R;
import com.cb.qiangqiang2.common.base.BaseFragment;

/**
 * 用户列表
 */
public class UserListFragment extends BaseFragment {
    public static final String UID = "uid";
    public static final String TYPE = "type";

    private int mUserId;
    private String mType;

    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance(int userId, String type) {

        Bundle args = new Bundle();
        args.putInt(UID, userId);
        args.putString(TYPE, type);
        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt(UID);
            mType = getArguments().getString(TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        return view;
    }

}
