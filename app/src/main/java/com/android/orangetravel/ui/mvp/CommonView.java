package com.android.orangetravel.ui.mvp;


import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.mvp.BaseView;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
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
public interface CommonView extends BaseView {

    /**
     * 联系我们-成功
     */
    void contact(ContactBean bean);
    /**
     * 联系我们-成功
     */
    void balance(BalanceBean bean);

    /**
     * 退出登录-成功
     */
    void loginOut(ErrorMsgBean bean);
    /**
     * 账单列表-成功
     */
    void billList(List<BillListBean> bean);
    /**
     * 账单列表-成功
     */
    void DiveList(List<WithDrawalHistoryBean> bean);


    /**
     * 退出登录-成功
     */
    void Compliance(ComplianceBean bean);
}