package com.android.orangetravel.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.orangetravel.R;
import com.android.orangetravel.base.adapter.rvadapter.CommonAdapter;
import com.android.orangetravel.base.adapter.rvadapter.base.ViewHolder;
import com.android.orangetravel.base.adapter.rvadapter.utils.ListItemDecoration;
import com.android.orangetravel.base.base.BaseActivity;
import com.android.orangetravel.base.bean.ErrorMsgBean;
import com.android.orangetravel.base.glide.GlideUtil;
import com.android.orangetravel.base.mvp.BasePresenter;
import com.android.orangetravel.base.widgets.CircleImageView;
import com.android.orangetravel.base.widgets.YangTitleBar;
import com.android.orangetravel.bean.BalanceBean;
import com.android.orangetravel.bean.BillListBean;
import com.android.orangetravel.bean.ComplianceBean;
import com.android.orangetravel.bean.ContactBean;
import com.android.orangetravel.bean.UserInfoBean;
import com.android.orangetravel.bean.WithDrawalHistoryBean;
import com.android.orangetravel.ui.mvp.CommonPresenter;
import com.android.orangetravel.ui.mvp.CommonView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;

/**
 * @author Mr Xiong
 * @date 2020/12/24
 * 我的二维码
 */

public class MyQrCodeActivity extends BaseActivity<CommonPresenter> implements CommonView {
    @BindView(R.id.id_title_bar)
    YangTitleBar id_title_bar;
    /*二维码*/
    @BindView(R.id.qrcode)
    ImageView qrCode;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.head_icon)
    CircleImageView head_icon;
    private UserInfoBean userInfoBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_qrcode;
    }

    @Override
    public CommonPresenter initPresenter() {
        return new CommonPresenter(this);
    }

    @Override
    public void initView() {
        setTitleBar("我的二维码");
        setmYangStatusBar(ContextCompat.getColor(MyQrCodeActivity.this, R.color.title_bar_black));
        id_title_bar.setBackgroundColor(ContextCompat.getColor(MyQrCodeActivity.this, R.color.title_bar_black));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("userBean")) {
                userInfoBean = (UserInfoBean) bundle.getSerializable("userBean");
                userName.setText(userInfoBean.getName());
                GlideUtil.loadImg(mContext, userInfoBean.getAvatar(), head_icon);
            }
        }
    }


    @Override
    public void requestData() {
        getPresenter().qrCode();
    }

    @Override
    public void contact(ContactBean bean) {

    }

    @Override
    public void balance(BalanceBean bean) {
        if (bean.getQrCode() == null) {
            return;
        }
        String code;
        code = bean.getQrCode();
        if (bean.getQrCode().contains("data:image/png;base64,")) {
            code = bean.getQrCode().replace("data:image/png;base64,", "");
        }
        try {
            byte[] decodedString = Base64.decode(code.trim(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
                    0, decodedString.length);
            qrCode.setImageBitmap(decodedByte);
        } catch (Exception e) {

        }
    }

    @Override
    public void loginOut(ErrorMsgBean bean) {

    }

    @Override
    public void billList(List<BillListBean> bean) {

    }

    @Override
    public void DiveList(List<WithDrawalHistoryBean> bean) {

    }

    @Override
    public void Compliance(ComplianceBean bean) {

    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
