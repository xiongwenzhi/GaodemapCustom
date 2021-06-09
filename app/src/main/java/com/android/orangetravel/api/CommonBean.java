package com.android.orangetravel.api;


import com.android.orangetravel.base.bean.BaseBeanforCode;
import com.android.orangetravel.base.bean.ErrorMsgBean;

/**
 * 公共的接收“data”数据的Bean
 *
 * @author xiongwenzhi
 * @date 2021/3/4
 */
public class CommonBean extends BaseBeanforCode {

    /**
     * 11034
     * 修改头像
     * /data/attachment/user/180613154619_35.jpg
     */
    private String list;

    /**
     * 20031
     * 根据数量获取价格
     * 12.00
     */
    private String price;

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}