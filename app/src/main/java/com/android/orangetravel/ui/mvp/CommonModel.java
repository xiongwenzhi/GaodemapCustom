package com.android.orangetravel.ui.mvp;

import com.android.orangetravel.api.Api;
import com.android.orangetravel.api.UrlParam;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BaseModel;
import com.android.orangetravel.base.rx.RxListObserver;
import com.android.orangetravel.base.rx.RxObserver;
import com.android.orangetravel.base.rx.RxResult;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.PerfectInfoBean;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 时间：2019/6/25 0010
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @author xiongwenzhi
 */
public class CommonModel extends BaseModel {
    /**
     * 联系我们
     *
     * @param observer
     */
    public void contact(RxObserver<ContactBean> observer) {
        Api.getInstance()
                .mApiService
                .contact()
                .compose(RxResult.<ContactBean>handleResultCode())
                .subscribe(observer);
    }


    /**
     * 司机钱包
     *
     * @param observer
     */
    public void balance(RxObserver<BalanceBean> observer) {
        Api.getInstance()
                .mApiService
                .balance()
                .compose(RxResult.<BalanceBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 账单列表
     *
     * @param observer
     */
    public void bill(RxListObserver<List<BillListBean>> observer, String pm) {
        Api.getInstance()
                .mApiService
                .bill(pm)
                .compose(RxResult.<BillListBean>handleListResult())
                .subscribe(observer);
    }

    /**
     * 二维码
     *
     * @param observer
     */
    public void qrCode(RxObserver<BalanceBean> observer) {
        Api.getInstance()
                .mApiService
                .qrCode()
                .compose(RxResult.<BalanceBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 听单检测
     *
     * @param observer
     */
    public void detection(RxObserver<BalanceBean> observer, String type) {
        Api.getInstance()
                .mApiService
                .detection(type)
                .compose(RxResult.<BalanceBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 退出登录
     *
     * @param observer
     */
    public void logout(RxObserver<ErrorMsgBean> observer) {
        Api.getInstance()
                .mApiService
                .logout()
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 图片上传
     *
     * @param observer
     */
    public void UploadImage(RxObserver<ContactBean> observer, File headFile) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("file", headFile.getName(),
                RequestBody.create(MultipartBody.FORM, headFile));
        Api.getInstance()
                .mApiService
                .UploadImage(builder.build())
                .compose(RxResult.<ContactBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 设备管理
     *
     * @param observer
     */
    public void equipment(RxListObserver<List<WithDrawalHistoryBean>> observer) {
        Api.getInstance()
                .mApiService
                .equipment()
                .compose(RxResult.<WithDrawalHistoryBean>handleListResult())
                .subscribe(observer);
    }

    /**
     * 设备删除
     *
     * @param observer
     */
    public void equipmentDel(RxObserver<ErrorMsgBean> observer, Map map) {
        Api.getInstance()
                .mApiService
                .equipmentDel(map)
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 我的资质
     *
     * @param observer
     */
    public void qualification(RxListObserver<List<WithDrawalHistoryBean>> observer) {
        Api.getInstance()
                .mApiService
                .qualification()
                .compose(RxResult.<WithDrawalHistoryBean>handleListResult())
                .subscribe(observer);
    }

    /**
     * 未获得资质
     *
     * @param observer
     */
    public void notQualified(RxListObserver<List<BillListBean>> observer) {
        Api.getInstance()
                .mApiService
                .notQualified()
                .compose(RxResult.<BillListBean>handleListResult())
                .subscribe(observer);
    }


    /**
     * 合规认证
     *
     * @param observer
     */
    public void compliance(RxObserver<ComplianceBean> observer, String city) {
        Api.getInstance()
                .mApiService
                .compliance(city)
                .compose(RxResult.<ComplianceBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 上传合规资质
     *
     * @param
     */
    public void complianceEdit(RxObserver<ErrorMsgBean> observer, Map map) {
        Api.getInstance()
                .mApiService
                .complianceEdit(map)
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 注销账号
     *
     * @param
     */
    public void accountCancel(RxObserver<ErrorMsgBean> observer) {
        Api.getInstance()
                .mApiService
                .accountCancel()
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 修改手机号
     *
     * @param
     */
    public void modifyPhone(RxObserver<ErrorMsgBean> observer, Map map) {
        Api.getInstance()
                .mApiService
                .modifyPhone(map)
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 更新版本
     *
     * @param observer
     */
    public void versionUpdate(RxObserver<ContactBean> observer,String version,String equipment) {
        Api.getInstance()
                .mApiService
                .versionUpdate(version,equipment)
                .compose(RxResult.<ContactBean>handleResultCode())
                .subscribe(observer);
    }
}