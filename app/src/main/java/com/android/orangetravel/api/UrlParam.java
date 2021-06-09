package com.android.orangetravel.api;

/**
 * Url + 参数
 *
 * @author xiongwenzhi
 */
public interface UrlParam {
    /*接口地址*/
    String HOST = "http://baidu.com/";
    /*获取验证码*/
    String verify = "verify";
    /*验证码登录*/
    String verifyLogin = "verifyLogin";
    /*账号密码登录*/
    String Login = "login";
    /*修改密码*/
    String resetPwd = "resetPwd";
    /*个人信息*/
    String info = "info";
    /*个人中心-我的工具*/
    String tools = "tools";
    /*个人中心-推荐活动*/
    String activity = "activity";
    /*联系我们*/
    String contact = "contact";
    /*司机钱包*/
    String balance = "balance";
    /*提现提示语*/
    String billTips = "bill/tips";
    /*账单列表*/
    String bill = "bill";
    /*二维码*/
    String qrCode = "qrCode";
    /*听单检测*/
    String detection = "detection";
    /*退出登录*/
    String logout = "logout";
    /*轮播消息*/
    String noticeNew = "notice/new";
    /*获取是否有新消息*/
    String noReadCount = "noReadCount";
    /*消息列表*/
    String notice = "notice";
    /*我的车辆列表*/
    String car = "car";
    /*车辆详情*/
    String detail = "car/detail";
    /*设置默认车辆*/
    String setDefault = "car/setDefault";
    /*图片上传*/
    String UploadImage = "upload/image";
    /*车辆新增编辑*/
    String carEdit = "car/edit";
    /*车辆删除*/
    String carDel = "car/del";
    /*车辆驳回原因*/
    String carReason = "car/reason";
    /* 完善车辆信息*/
    String carperfect = "car/perfect";
    /* 获取默认银行卡*/
    String bankDefalut = "bank/default";
    /* 银行卡列表*/
    String bankList = "bank";
    /* 提现记录*/
    String extract = "extract";
    /* 申请提现*/
    String extractCash = "extract/cash";
    /* 设备管理*/
    String equipment = "equipment";
    /* 设备删除*/
    String equipmentDel = "equipment/del";
    /*系统设置银行卡*/
    String bankSystom = "bank/system";
    /*银行卡添加/编辑*/
    String bankEdit = "bank/edit";
    /*我的资质*/
    String qualification = "qualification";
    /*未获得资质*/
    String notQualified = "notQualified";
    /*合规认证*/
    String compliance = "compliance";
    /*上传合规资质*/
    String complianceEdit = "compliance/edit";
    /*注销账号*/
    String accountCancel = "account/cancel";
    /*修改手机号*/
    String modifyPhone = "modifyPhone";
    /*新增、修改紧急联系人*/
    String contactEdit = "contact/edit";
    /*紧急联系人列表*/
    String contactList = "contact/list";
    /*删除联系人*/
    String contactDel = "contact/del";
    /*我的行程*/
    String orderList = "order";
    /*行程详情*/
    String orderDetails = "order/detail";
    /*流水明细*/
    String billDetails = "bill/detail";
    /*流水分析*/
    String billAnalyze = "bill/analyze";
    /*费用明细*/
    String orderAmountDetail = "order/amountDetail";
    /*更新版本*/
    String versionUpdate = "version/update";
    /*首页*/
    String index = "/";
    /*设置消息阅读状态*/
    String noticeRead = "notice/read";
    /*交通上报*/
    String traffic = "traffic";
    /*司机日报*/
    String daily = "daily";
}