package com.android.orangetravel.ui.mvp;


import com.android.orangetravel.base.mvp.BaseView;
import com.android.orangetravel.bean.BankDefault;
import com.android.orangetravel.bean.BankListDefault;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.LoginBean;
import com.android.orangetravel.bean.MessageNotciList;
import com.android.orangetravel.bean.SendCodeBean;
import com.android.orangetravel.bean.ToolListBean;
import com.android.orangetravel.bean.UserInfoBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;

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
public interface BillView extends BaseView {

    /**
     * 联系我们-成功
     */
    void contact(ContactBean bean);
    /**
     * 默认银行卡-成功
     */
    void bankDefalut(BankDefault bean);
    /**
     * 默认银行卡-成功
     */
    void bankList(List<BankListDefault> bean);
    /**
     * 默认银行卡-成功
     */
    void WithdrawHistoryList(List<WithDrawalHistoryBean> bean);
    /**
     * 获取验证码-成功
     */
    void verify(String bean);
}