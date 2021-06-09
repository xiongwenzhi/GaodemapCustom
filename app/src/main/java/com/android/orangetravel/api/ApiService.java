package com.android.orangetravel.api;

import com.android.orangetravel.base.bean.BaseBean;
import com.android.orangetravel.base.bean.BaseBeanforCode;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.bean.ListBaseBean;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BankDefault;
import com.android.orangetravel.bean.BankListDefault;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.CarDetailsBean;
import com.android.orangetravel.bean.ChargeDetailsBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.FlowAnalyBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.MyCarListBean;
import com.android.orangetravel.bean.OrderListBean;
import com.android.orangetravel.bean.PerfectInfoBean;
import com.android.orangetravel.bean.SendCodeBean;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 请求接口管理
 *
 * @author yangfei
 */
public interface ApiService {

//    /**
//     * 注册
//     */
//    @FormUrlEncoded
//    @POST(UrlParam.verifyLogin)
//    Observable<BaseBean<RegisterSucBean>> register(
//            @Field(UrlParam.REQUEST_DATA) String requestData
//    );

    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST(UrlParam.verify)
    Observable<BaseBean<Object>> verify(@FieldMap Map<String, Object> post);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST(UrlParam.verifyLogin)
    Observable<BaseBeanforCode<LoginBean>> verifyLogin(
            @FieldMap Map<String, Object> post
    );

    /**
     * 账号密码登录
     */
    @FormUrlEncoded
    @POST(UrlParam.Login)
    Observable<BaseBeanforCode<LoginBean>> Login(
            @FieldMap Map<String, Object> post
    );

    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST(UrlParam.resetPwd)
    Observable<BaseBeanforCode<ErrorMsgBean>> resetPwd(
            @FieldMap Map<String, Object> post
    );

    /**
     * 个人信息
     */
    @GET(UrlParam.info)
    Observable<BaseBeanforCode<UserInfoBean>> info();

    /**
     * 我的工具
     */
    @GET(UrlParam.tools)
    Observable<ListBaseBean<ToolListBean>> tools();

    /**
     * 推荐活动
     */
    @GET(UrlParam.activity)
    Observable<ListBaseBean<ToolListBean>> activity();

    /**
     * 联系我们
     */
    @GET(UrlParam.contact)
    Observable<BaseBeanforCode<ContactBean>> contact();

    /**
     * 司机钱包
     */
    @GET(UrlParam.balance)
    Observable<BaseBeanforCode<BalanceBean>> balance();

    /**
     * 账单列表
     */
    @GET(UrlParam.bill)
    Observable<ListBaseBean<BillListBean>> bill(@Query("pm") String pm);

    /**
     * 二维码
     */
    @GET(UrlParam.qrCode)
    Observable<BaseBeanforCode<BalanceBean>> qrCode();

    /**
     * 听单检测
     */
    @GET(UrlParam.detection)
    Observable<BaseBeanforCode<BalanceBean>> detection(@Query("type") String type);

    /**
     * 退出登录
     */
    @POST(UrlParam.logout)
    Observable<BaseBeanforCode<ErrorMsgBean>> logout();

    /**
     * 轮播消息
     */
    @GET(UrlParam.noticeNew)
    Observable<ListBaseBean<MessageNotciList>> noticeNew();

    /**
     * 获取是否有新消息
     */
    @GET(UrlParam.noReadCount)
    Observable<BaseBeanforCode<UserInfoBean>> noReadCount();

    /**
     * 消息列表
     */
    @GET(UrlParam.notice)
    Observable<ListBaseBean<MessageNotciList>> notice(@Query("type") String type);

    /**
     * 我的车辆列表
     */
    @GET(UrlParam.car)
    Observable<ListBaseBean<MyCarListBean>> car();

    /**
     * 车辆详情
     */
    @GET(UrlParam.detail)
    Observable<BaseBeanforCode<CarDetailsBean>> carDetail(@Query("id") String id);

    /**
     * 设置默认车辆
     */
    @FormUrlEncoded
    @POST(UrlParam.setDefault)
    Observable<BaseBeanforCode<ErrorMsgBean>> setDefault(
            @FieldMap Map<String, Object> post
    );

    /**
     * 图片上传
     */
    @POST(UrlParam.UploadImage)
    Observable<BaseBeanforCode<ContactBean>> UploadImage(
            @Body MultipartBody body
    );

    /**
     * 车辆新增编辑
     */
    @FormUrlEncoded
    @POST(UrlParam.carEdit)
    Observable<BaseBeanforCode<ErrorMsgBean>> carEdit(
            @FieldMap Map<String, Object> post
    );

    /**
     * 车辆删除
     */
    @FormUrlEncoded
    @POST(UrlParam.carDel)
    Observable<BaseBeanforCode<ErrorMsgBean>> carDel(
            @FieldMap Map<String, Object> post
    );


    /**
     * 车辆驳回原因
     */
    @GET(UrlParam.carReason)
    Observable<BaseBeanforCode<PerfectInfoBean>> carReason(@Query("id") String id);

    /**
     * 完善车辆信息
     */
    @FormUrlEncoded
    @POST(UrlParam.carperfect)
    Observable<BaseBeanforCode<ErrorMsgBean>> carperfect(
            @FieldMap Map<String, Object> post
    );

    /**
     * 提现提示语
     */
    @GET(UrlParam.billTips)
    Observable<BaseBeanforCode<ContactBean>> billTips();


    /**
     * 获取默认银行卡
     */
    @GET(UrlParam.bankDefalut)
    Observable<BaseBeanforCode<BankDefault>> bankDefalut();

    /**
     * 银行卡列表
     */
    @GET(UrlParam.bankList)
    Observable<ListBaseBean<BankListDefault>> bankList();

    /**
     * 提现记录
     */
    @GET(UrlParam.extract)
    Observable<ListBaseBean<WithDrawalHistoryBean>> extract();

    /**
     * 申请提现
     */
    @FormUrlEncoded
    @POST(UrlParam.extractCash)
    Observable<BaseBeanforCode<ErrorMsgBean>> extractCash(
            @FieldMap Map<String, Object> post
    );

    /**
     * 设备管理
     */
    @GET(UrlParam.equipment)
    Observable<ListBaseBean<WithDrawalHistoryBean>> equipment();

    /**
     * 设备删除
     */
    @FormUrlEncoded
    @POST(UrlParam.equipmentDel)
    Observable<BaseBeanforCode<ErrorMsgBean>> equipmentDel(
            @FieldMap Map<String, Object> post
    );

    /**
     * 系统设置银行卡
     */
    @GET(UrlParam.bankSystom)
    Observable<ListBaseBean<WithDrawalHistoryBean>> bankSystom();

    /**
     * 银行卡添加/编辑
     */
    @FormUrlEncoded
    @POST(UrlParam.bankEdit)
    Observable<BaseBeanforCode<ErrorMsgBean>> bankEdit(
            @FieldMap Map<String, Object> post
    );

    /**
     * 我的资质
     */
    @GET(UrlParam.qualification)
    Observable<ListBaseBean<WithDrawalHistoryBean>> qualification();

    /**
     * 未获得资质
     */
    @GET(UrlParam.notQualified)
    Observable<ListBaseBean<BillListBean>> notQualified();

    /**
     * 合规认证
     */
    @GET(UrlParam.compliance)
    Observable<BaseBeanforCode<ComplianceBean>> compliance(@Query("city") String city);

    /**
     * 上传合规资质
     */
    @FormUrlEncoded
    @POST(UrlParam.complianceEdit)
    Observable<BaseBeanforCode<ErrorMsgBean>> complianceEdit(
            @FieldMap Map<String, Object> post
    );

    /**
     * 注销账号
     */
    @POST(UrlParam.accountCancel)
    Observable<BaseBeanforCode<ErrorMsgBean>> accountCancel(
    );

    /**
     * 修改手机号
     */
    @FormUrlEncoded
    @POST(UrlParam.modifyPhone)
    Observable<BaseBeanforCode<ErrorMsgBean>> modifyPhone(
            @FieldMap Map<String, Object> post
    );

    /**
     * 新增、修改紧急联系人
     */
    @FormUrlEncoded
    @POST(UrlParam.contactEdit)
    Observable<BaseBeanforCode<ErrorMsgBean>> contactEdit(
            @FieldMap Map<String, Object> post
    );

    /**
     * 删除联系人
     */
    @FormUrlEncoded
    @POST(UrlParam.contactDel)
    Observable<BaseBeanforCode<ErrorMsgBean>> contactDel(
            @FieldMap Map<String, Object> post
    );

    /**
     * 紧急联系人列表
     */
    @GET(UrlParam.contactList)
    Observable<ListBaseBean<ToolListBean>> contactList();

    /**
     * 我的行程
     */
    @GET(UrlParam.orderList)
    Observable<ListBaseBean<OrderListBean>> orderList(@Query("trip") String id);

    /**
     * 行程详情
     */
    @GET(UrlParam.orderDetails)
    Observable<BaseBeanforCode<OrderListBean.DataBean>> orderDetails(@Query("id") String id);

    /**
     * 流水明细
     */
    @GET(UrlParam.billDetails)
    Observable<ListBaseBean<OrderListBean>> billDetails();

    /**
     * 流水分析
     */
    @GET(UrlParam.billAnalyze)
    Observable<BaseBeanforCode<FlowAnalyBean>> billAnalyze();

    /**
     * 费用明细
     */
    @GET(UrlParam.orderAmountDetail)
    Observable<BaseBeanforCode<ChargeDetailsBean>> orderAmountDetail(@Query("id") String id);


    /**
     * 更新版本
     */
    @GET(UrlParam.versionUpdate)
    Observable<BaseBeanforCode<ContactBean>> versionUpdate(@Query("version") String version, @Query("equipment") String equipment);


    /**
     * 首页
     */
    @GET(UrlParam.index)
    Observable<BaseBeanforCode<ContactBean>> index();

    /**
     * 设置消息阅读状态
     */
    @FormUrlEncoded
    @POST(UrlParam.noticeRead)
    Observable<BaseBeanforCode<ErrorMsgBean>> noticeRead(
            @FieldMap Map<String, Object> post
    );

    /**
     * 交通上报
     */
    @FormUrlEncoded
    @POST(UrlParam.traffic)
    Observable<BaseBeanforCode<ContactBean>> traffic(
            @FieldMap Map<String, Object> post
    );

    /**
     * 司机日报
     */
    @GET(UrlParam.daily)
    Observable<BaseBeanforCode<ContactBean>> daily(@Query("area") String area, @Query("date") String date);
}