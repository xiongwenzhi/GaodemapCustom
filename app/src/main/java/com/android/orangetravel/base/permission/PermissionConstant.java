package com.android.orangetravel.base.permission;

import android.Manifest;

public interface PermissionConstant {

    /*联系人*/
    String CONTACTS = Manifest.permission_group.CONTACTS;
    String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;// 写入联系人
    String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;// 获取账户
    String READ_CONTACTS = Manifest.permission.READ_CONTACTS;// 读取联系人
    /*手机*/
    String PHONE = Manifest.permission_group.PHONE;
    String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;// 看通话记录
    String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;// 读取手机状态
    String CALL_PHONE = Manifest.permission.CALL_PHONE;// 拨打电话
    String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;// WRITE CALL LOG
    String USE_SIP = Manifest.permission.USE_SIP;// 使用SIP
    String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;// 过程输出调用
    String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;// 添加语音信箱
    /*日历*/
    String CALENDAR = Manifest.permission_group.CALENDAR;
    String READ_CALENDAR = Manifest.permission.READ_CALENDAR;// 看日历
    String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;// 写的日历
    /*相机*/
    String GROUP_CAMERA = Manifest.permission_group.CAMERA;
    String CAMERA = Manifest.permission.CAMERA;// 相机
    /*传感器*/
    String SENSORS = Manifest.permission_group.SENSORS;
    String BODY_SENSORS = Manifest.permission.BODY_SENSORS;// 体传感器
    /*位置*/
    String LOCATION = Manifest.permission_group.LOCATION;
    String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;// 获得精确位置
    String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;// 访问粗定位
    /*存储*/
    String STORAGE = Manifest.permission_group.STORAGE;
    String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;// 读取外部存储器
    String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;// 写入外部存储器
    /*麦克风*/
    String MICROPHONE = Manifest.permission_group.MICROPHONE;
    String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;// 记录音频
    /*用户管理系统*/
    String SMS = Manifest.permission_group.SMS;
    String READ_SMS = Manifest.permission.READ_SMS;// 读短信
    String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;// 收到WAP推
    String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;// 接收彩信
    String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;// 收到短信
    String SEND_SMS = Manifest.permission.SEND_SMS;// 发送短信

}