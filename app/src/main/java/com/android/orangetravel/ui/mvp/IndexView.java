package com.android.orangetravel.ui.mvp;


import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BaseView;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.SendCodeBean;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;

import java.util.List;

/**
 * 时间：2019/5/15 0010
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 *
 * @author xiongwenzhi
 */
public interface IndexView extends BaseView {

    /**
     * 获取首页-成功
     */
    void index(ContactBean bean);
}