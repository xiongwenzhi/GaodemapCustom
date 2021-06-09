package com.android.orangetravel.ui.mvp;

import com.android.orangetravel.api.Api;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BaseModel;
import com.android.orangetravel.base.rx.RxListObserver;
import com.android.orangetravel.base.rx.RxObserver;
import com.android.orangetravel.base.rx.RxResult;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;

import java.util.List;
import java.util.Map;

/**
 * 时间：2019/6/25 0010
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @author xiongwenzhi
 */
public class IndexModel extends BaseModel {
    /**
     * 首页
     *
     * @param observer
     */
    public void index(RxObserver<ContactBean> observer) {
        Api.getInstance()
                .mApiService
                .index()
                .compose(RxResult.<ContactBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 交通上报
     *
     * @param
     */
    public void traffic(RxObserver<ContactBean> observer, Map map) {
        Api.getInstance()
                .mApiService
                .traffic(map)
                .compose(RxResult.<ContactBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 司机日报
     *
     * @param observer
     */
    public void daily(RxObserver<ContactBean> observer, String area, String date) {
        Api.getInstance()
                .mApiService
                .daily(area, date)
                .compose(RxResult.<ContactBean>handleResultCode())
                .subscribe(observer);
    }
}