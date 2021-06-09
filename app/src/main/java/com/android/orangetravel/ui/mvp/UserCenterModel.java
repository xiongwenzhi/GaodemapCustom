package com.android.orangetravel.ui.mvp;

import com.android.orangetravel.api.Api;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BaseModel;
import com.android.orangetravel.base.rx.RxListObserver;
import com.android.orangetravel.base.rx.RxObserver;
import com.android.orangetravel.base.rx.RxResult;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.SendCodeBean;
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
public class UserCenterModel extends BaseModel {
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
     * 登录
     *
     * @param observer
     */
    public void verifyLogin(RxObserver<LoginBean> observer, Map<String, Object> map) {
        Api.getInstance()
                .mApiService
                .verifyLogin(map)
                .compose(RxResult.<LoginBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 账号密码登录
     *
     * @param observer
     */
    public void Login(RxObserver<LoginBean> observer, Map<String, Object> map) {
        Api.getInstance()
                .mApiService
                .Login(map)
                .compose(RxResult.<LoginBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 修改密码
     *
     * @param observer
     */
    public void resetPwd(RxObserver<ErrorMsgBean> observer, Map<String, Object> map) {
        Api.getInstance()
                .mApiService
                .resetPwd(map)
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }
    /**
     * 个人信息
     *
     * @param observer
     */
    public void info(RxObserver<UserInfoBean> observer) {
        Api.getInstance()
                .mApiService
                .info()
                .compose(RxResult.<UserInfoBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 我的工具
     *
     * @param observer
     */
    public void tools(RxListObserver<List<ToolListBean>> observer) {
        Api.getInstance()
                .mApiService
                .tools()
                .compose(RxResult.<ToolListBean>handleListResult())
                .subscribe(observer);
    }

    /**
     * 推荐活动
     *
     * @param observer
     */
    public void activity(RxListObserver<List<ToolListBean>> observer) {
        Api.getInstance()
                .mApiService
                .activity()
                .compose(RxResult.<ToolListBean>handleListResult())
                .subscribe(observer);
    }

    /**
     * 轮播消息
     *
     * @param observer
     */
    public void noticeNew(RxListObserver<List<MessageNotciList>> observer) {
        Api.getInstance()
                .mApiService
                .noticeNew()
                .compose(RxResult.<MessageNotciList>handleListResult())
                .subscribe(observer);
    }

    /**
     * 获取是否有新消息
     *
     * @param observer
     */
    public void noReadCount(RxObserver<UserInfoBean> observer) {
        Api.getInstance()
                .mApiService
                .noReadCount()
                .compose(RxResult.<UserInfoBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 消息列表
     *
     * @param observer
     */
    public void notice(RxListObserver<List<MessageNotciList>> observer,String type) {
        Api.getInstance()
                .mApiService
                .notice(type)
                .compose(RxResult.<MessageNotciList>handleListResult())
                .subscribe(observer);
    }

    /**
     * 新增、修改紧急联系人
     *
     * @param
     */
    public void contactEdit(RxObserver<ErrorMsgBean> observer,Map map) {
        Api.getInstance()
                .mApiService
                .contactEdit(map)
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }
    /**
     * 删除联系人
     *
     * @param
     */
    public void contactDel(RxObserver<ErrorMsgBean> observer,Map map) {
        Api.getInstance()
                .mApiService
                .contactDel(map)
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }

    /**
     * 紧急联系人列表
     *
     * @param observer
     */
    public void contactList(RxListObserver<List<ToolListBean>> observer) {
        Api.getInstance()
                .mApiService
                .contactList()
                .compose(RxResult.<ToolListBean>handleListResult())
                .subscribe(observer);
    }

    /**
     * 设置消息阅读状态
     *
     * @param
     */
    public void noticeRead(RxObserver<ErrorMsgBean> observer,Map map) {
        Api.getInstance()
                .mApiService
                .noticeRead(map)
                .compose(RxResult.<ErrorMsgBean>handleResultCode())
                .subscribe(observer);
    }
//
//    /**
//     * 获取我的培训老师接口
//     */
//    public void getCenterTrainer(RxObserver<MineTrainerBean> observer) {
//        JsonObject object = PacketUtil.getNewRequestData(null);
//        Map<String, String> mapPart = new Gson().fromJson(object, Map.class);
//        InteGralApi.getInstance()
//                .mApiService
//                .getCenterTrainer(mapPart)
//                .compose(RxResult.<MineTrainerBean>handleResultCode())
//                .subscribe(observer);
//    }
//
//    /**
//     * 笔淘商学院接口
//     */
//    public void getCenterschool(RxObserver<BusinessSchoolBean> observer) {
//        JsonObject object = PacketUtil.getNewRequestData(null);
//        Map<String, String> mapPart = new Gson().fromJson(object, Map.class);
//        InteGralApi.getInstance()
//                .mApiService
//                .getCenterschool(mapPart)
//                .compose(RxResult.<BusinessSchoolBean>handleResultCode())
//                .subscribe(observer);
//    }


    /**
     * 我的余额
     *
     * @param observer
     */
//    public void balance(RxObserver<RrofitBalanceBean> observer) {
//        JsonObject object = PacketUtil.getNewRequestData(null);
//        Map<String, String> mapPart = new Gson().fromJson(object, Map.class);
//        InteGralApi.getInstance()
//                .mApiService
//                .balance(mapPart)
//                .compose(RxResult.<RrofitBalanceBean>handleResultCode())
//                .subscribe(observer);
//    }

    /**
     * 待入账收益
     */
//    public void balanceOrder(Map map, RxListObserver<List<BalanceOrderBean>> observer) {
//        JsonObject object = PacketUtil.getNewRequestData(map);
//        Map<String, String> mapPart = new Gson().fromJson(object, Map.class);
//        InteGralApi.getInstance()
//                .mApiService
//                .balanceOrder(mapPart)
//                .compose(RxResult.<BalanceOrderBean>handleListResult())
//                .subscribe(observer);
//    }

    /**
     * 待收益详情
     */
//    public void balanceDetail(Map map, RxObserver<TobeearnedDetailsBean> observer) {
//        JsonObject object = PacketUtil.getNewRequestData(map);
//        Map<String, String> mapPart = new Gson().fromJson(object, Map.class);
//        InteGralApi.getInstance()
//                .mApiService
//                .balanceDetail(mapPart)
//                .compose(RxResult.<TobeearnedDetailsBean>handleResultCode())
//                .subscribe(observer);
//    }

    /**
     * 收支明细
     */
//    public void payment(Map map, RxListObserver<List<PayMentListBean>> observer) {
//        JsonObject object = PacketUtil.getNewRequestData(map);
//        Map<String, String> mapPart = new Gson().fromJson(object, Map.class);
//        InteGralApi.getInstance()
//                .mApiService
//                .payment(mapPart)
//                .compose(RxResult.<PayMentListBean>handleListResult())
//                .subscribe(observer);
//    }

    /**
     * 提现帮助
     */
//    public void balanceHelp(Map map, RxListObserver<List<EarningHelpListBean>> observer) {
//        JsonObject object = PacketUtil.getNewRequestData(map);
//        Map<String, String> mapPart = new Gson().fromJson(object, Map.class);
//        InteGralApi.getInstance()
//                .mApiService
//                .balanceHelp(mapPart)
//                .compose(RxResult.<EarningHelpListBean>handleListResult())
//                .subscribe(observer);
//    }
//    /**
//     * 获取笔淘商学院标签教程数据接口
//     *
//     * @param observer
//     */
//    public void schoolNews(Map map, RxObserver<BusinessSchoolListBean> observer) {
//        JsonObject object = PacketUtil.getNewRequestData(map);
//        Map<String, String> mapPart = new Gson().fromJson(object, Map.class);
//        InteGralApi.getInstance()
//                .mApiService
//                .schoolNews(mapPart)
//                .compose(RxResult.<BusinessSchoolListBean>handleResultCode())
//                .subscribe(observer);
//    }
//
//    /**
//     * 访客导购数据接口
//     *
//     * @param observer
//     */
//    public void guideAccess(RxObserver<VisitorShopingGuideBean> observer) {
//        JsonObject object = PacketUtil.getNewRequestData(null);
//        Map<String, String> mapPart = new Gson().fromJson(object, Map.class);
//        InteGralApi.getInstance()
//                .mApiService
//                .guideAccess(mapPart)
//                .compose(RxResult.<VisitorShopingGuideBean>handleResultCode())
//                .subscribe(observer);
//    }
//
//    /**
//     * 我的收益
//     *
//     * @param observer
//     */
//    public void earning(RxObserver<MyBenefitsBean> observer) {
//        JsonObject object = PacketUtil.getNewRequestData(null);
//        Map<String, String> mapPart = new Gson().fromJson(object, Map.class);
//        InteGralApi.getInstance()
//                .mApiService
//                .earning(mapPart)
//                .compose(RxResult.<MyBenefitsBean>handleResultCode())
//                .subscribe(observer);
//    }
//
//    /**
//     * 累计收益帮助
//     *
//     * @param observer
//     */
//    public void earningHelp(RxListObserver<List<EarningHelpListBean>> observer) {
//        JsonObject object = PacketUtil.getNewRequestData(null);
//        Map<String, String> mapPart = new Gson().fromJson(object, Map.class);
//        InteGralApi.getInstance()
//                .mApiService
//                .earningHelp(mapPart)
//                .compose(RxResult.<EarningHelpListBean>handleListResult())
//                .subscribe(observer);
//    }
}