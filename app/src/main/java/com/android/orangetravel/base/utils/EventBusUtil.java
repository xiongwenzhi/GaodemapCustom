package com.android.orangetravel.base.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus工具类
 *
 * @author yangfei
 * @date 2018/6/12
 */
public final class EventBusUtil {

    private EventBusUtil() {
    }

    /**
     * 发送事件
     *
     * @param event 事件
     */
    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }

}