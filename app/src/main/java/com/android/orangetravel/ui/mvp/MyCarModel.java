package com.android.orangetravel.ui.mvp;

import com.android.orangetravel.api.Api;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BaseModel;
import com.android.orangetravel.base.rx.RxListObserver;
import com.android.orangetravel.base.rx.RxObserver;
import com.android.orangetravel.base.rx.RxResult;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.CarDetailsBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.MyCarListBean;
import com.android.orangetravel.bean.PerfectInfoBean;
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
public class MyCarModel extends BaseModel {

    /**
     * 我的车辆列表
     *
     * @param observer
     */
    public void car(RxListObserver<List<MyCarListBean>> observer) {
        Api.getInstance()
                .mApiService
                .car()
                .compose(RxResult.<MyCarListBean>handleListResult())
                .subscribe(observer);
    }

    /**
     * 车辆详情
     *
     * @param observer
     */
    public void carDetail(RxObserver<CarDetailsBean> observer, String id) {
        Api.getInstance()
                .mApiService
                .carDetail(id)
                .compose(RxResult.<CarDetailsBean>handleResultCode())
                .subscribe(observer);
    }
    /**
     * 设置默认车辆
     *
     * @param observer
     */
    public void setDefault(RxObserver<ErrorMsgBean> observer, Map map) {
        Api.getInstance()
                .mApiService
                .setDefault(map)
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 车辆新增编辑
     *
     * @param observer
     */
    public void carEdit(RxObserver<ErrorMsgBean> observer, Map map) {
        Api.getInstance()
                .mApiService
                .carEdit(map)
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }
    /**
     * 车辆删除
     *
     * @param observer
     */
    public void carDel(RxObserver<ErrorMsgBean> observer, Map map) {
        Api.getInstance()
                .mApiService
                .carDel(map)
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 车辆驳回原因
     *
     * @param observer
     */
    public void carReason(RxObserver<PerfectInfoBean> observer, String map) {
        Api.getInstance()
                .mApiService
                .carReason(map)
                .compose(RxResult.<PerfectInfoBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 完善车辆信息
     *
     * @param observer
     */
    public void carperfect(RxObserver<ErrorMsgBean> observer, Map map) {
        Api.getInstance()
                .mApiService
                .carperfect(map)
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }


}