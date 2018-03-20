package com.cb.qiangqiang2.mvpview;

import com.cb.qiangqiang2.common.base.BaseMVPView;
import com.cb.qiangqiang2.data.model.BoardBean;

import java.util.List;

/**
 * Created by cb on 2016/10/20.
 */

public interface BoardMvpView extends BaseMVPView {
    void loadBoardData(List<BoardBean> list);
}
