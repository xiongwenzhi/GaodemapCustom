package com.android.orangetravel.base.utils;

/**
 * 保存常量工具类
 */
public interface ConstantUtil {

    /*APP名称*/
    String APP_NAME = "OrangeTravel";// 用于设置该应用的主文件夹; SPUtil保存的文件名;

    /*SPUtil保存用的键*/
    String WELCOME_STATE = "WELCOME_STATE";// 引导页状态
    String LOGIN_STATE = "LOGIN_STATE";// 登录状态
    String USER_TOKEN = "USER_TOKEN";// 用户token登录后
    String USER_ID = "USER_ID";// 用户ID,加密的

    /*网络请求*/
    String SUCCESS = "000";// 响应成功
    String KEY = "n'NI&u#+lFA0y@;$6Wj=5(~9";// 拼接报文的KEY
    String DATA = "data";// 报文体
    String PAGE = "page";// 当前页码
    String PAGESIZE = "pagesize";// 一页显示条数
    String LIST = "list";// 数据集


    /*刷新,加载*/
    int REFRESH = 11;
    int LOADING = 22;
    /*默认PageSize*/
    int DEFAULT_PAGESIZE = 10;

    // QQ包名
    String QQ_PACKAGE_NAME = "com.tencent.mobileqq";
    // 微信包名
    String WEIXIN_PACKGE_NAME = "com.tencent.mm";
}