package com.android.orangetravel.ui.mvp;

import com.android.orangetravel.api.Api;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BaseModel;
import com.android.orangetravel.base.rx.RxListObserver;
import com.android.orangetravel.base.rx.RxObserver;
import com.android.orangetravel.base.rx.RxResult;
import com.android.orangetravel.bean.BankDefault;
import com.android.orangetravel.bean.BankListDefault;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;

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
public class BillModel extends BaseModel {

    /**
     * 提现提示语
     *
     * @param observer
     */
    public void billTips(RxObserver<ContactBean> observer) {
        Api.getInstance()
                .mApiService
                .billTips()
                .compose(RxResult.<ContactBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 获取默认银行卡
     *
     * @param observer
     */
    public void bankDefalut(RxObserver<BankDefault> observer) {
        Api.getInstance()
                .mApiService
                .bankDefalut()
                .compose(RxResult.<BankDefault>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 银行卡列表
     *
     * @param observer
     */
    public void bankList(RxListObserver<List<BankListDefault>> observer) {
        Api.getInstance()
                .mApiService
                .bankList()
                .compose(RxResult.<BankListDefault>handleListResult())
                .subscribe(observer);
    }

    /**
     * 提现记录
     *
     * @param observer
     */
    public void extract(RxListObserver<List<WithDrawalHistoryBean>> observer) {
        Api.getInstance()
                .mApiService
                .extract()
                .compose(RxResult.<WithDrawalHistoryBean>handleListResult())
                .subscribe(observer);
    }

    /**
     * 申请提现
     *
     * @param observer
     */
    public void extractCash(RxObserver<ErrorMsgBean> observer, Map map) {
        Api.getInstance()
                .mApiService
                .extractCash(map)
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 系统设置银行卡
     *
     * @param observer
     */
    public void bankSystom(RxListObserver<List<WithDrawalHistoryBean>> observer) {
        Api.getInstance()
                .mApiService
                .bankSystom()
                .compose(RxResult.<WithDrawalHistoryBean>handleListResult())
                .subscribe(observer);
    }

    /**
     * 发送验证码
     *
     * @param observer
     */
    public void verify(RxObserver<Object> observer, Map<String, Object> map) {
        Api.getInstance()
                .mApiService
                .verify(map)
                .compose(RxResult.<Object>handleResult())
                .subscribe(observer);
    }


    /**
     * 银行卡添加/编辑
     *
     * @param
     */
    public void bankEdit(RxObserver<ErrorMsgBean> observer, Map map) {
        Api.getInstance()
                .mApiService
                .bankEdit(map)
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }
}