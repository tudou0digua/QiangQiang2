package com.cb.qiangqiang2.presenter;

import com.cb.qiangqiang2.common.base.BasePresenter;
import com.cb.qiangqiang2.common.constant.Constants;
import com.cb.qiangqiang2.common.util.PrefUtils;
import com.cb.qiangqiang2.data.api.HttpManager;
import com.cb.qiangqiang2.data.model.BoardBean;
import com.cb.qiangqiang2.data.model.BoardModel;
import com.cb.qiangqiang2.mvpview.BoardMvpView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by cb on 2016/10/20.
 */

public class BoardPresenter extends BasePresenter<BoardMvpView> {

    @Inject
    public BoardPresenter() {
    }

    public void loadBoardData(){
        Map<String, String> map = HttpManager.getBaseMap(mContext);
        HttpManager.toSub(mApiService.getBoardList(map), new HttpManager.OnResponse() {
            @Override
            public void onSuccess(Object result) {
                if (getMvpView() == null) return;
                if (result != null) {
                    List<BoardBean> lists = new ArrayList<>();
                    try {
                        BoardModel boardModel = (BoardModel) result;
                        for (int i = 0; i < boardModel.getList().size(); i++) {
                            BoardModel.ListBean listBean = boardModel.getList().get(i);
                            int categoryId = listBean.getBoard_category_id();
                            String categoryName = listBean.getBoard_category_name();
                            List<BoardModel.ListBean.BoardListBean> boardListBeen = listBean.getBoard_list();
                            if (boardListBeen == null) continue;
                            for (int j = 0; j < boardListBeen.size(); j++) {
                                BoardModel.ListBean.BoardListBean item = boardListBeen.get(j);
                                BoardBean bean = new BoardBean();
                                bean.setCategoryId(categoryId);
                                bean.setCategory(categoryName);
                                bean.setId(item.getBoard_id());
                                bean.setName(item.getBoard_name());
                                bean.setDescription(item.getDescription());
                                bean.setFavNum(item.getFavNum());
                                bean.setImage(item.getBoard_img());
                                bean.setLastPostsDate(item.getLast_posts_date());
                                bean.setPostsTotalNum(item.getPosts_total_num());
                                bean.setTodayPostsNum(item.getTd_posts_num());
                                bean.setTopicTotalNum(item.getTopic_total_num());
                                lists.add(bean);
                            }
                        }
                        Gson gson = new Gson();
                        String data = gson.toJson(lists);
                        PrefUtils.putString(mContext, Constants.BOARD_LIST, data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    getMvpView().loadBoardData(lists);
                }
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().loadError(e);
            }
        }, mContext);
    }
}
