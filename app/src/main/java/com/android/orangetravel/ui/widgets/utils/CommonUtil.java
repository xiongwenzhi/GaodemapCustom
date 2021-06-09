package com.android.orangetravel.ui.widgets.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;


import com.amap.api.services.core.PoiItem;
import com.android.orangetravel.base.utils.DensityUtil;
import com.android.orangetravel.base.utils.SPUtil;
import com.android.orangetravel.base.utils.StringUtil;
import com.android.orangetravel.base.utils.log.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 公共方法
 *
 * @author yangfei
 * @date 2018/6/23
 */
public final class CommonUtil {

    private CommonUtil() {
    }

    /**
     * 拼接商品分类:笔>毛笔>狼毫笔头
     */
    public static String spliceGoodsClassify(String arg1, String arg2, String arg3) {
        return arg1 +
                ">" +
                arg2 +
                ">" +
                arg3;
        /*return new StringBuilder()
                .append(arg1)
                .append(">")
                .append(arg2)
                .append(">")
                .append(arg3)
                .toString();*/
    }

    /**
     * HashSet<String> -> 16,17,18
     */
    public static String idSetToIdsStr(HashSet<String> idSet) {
        StringBuilder builder = new StringBuilder();
        for (String next : idSet) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(next);
        }
        return builder.toString();
    }

    /**
     * 规格
     */
    public static String getSkuName(String skuName) {
        if (StringUtil.isEmpty(skuName)) {
            return "..";
        }
        if (skuName.length() > 8) {
            skuName = skuName.substring(0, 8) + "..";
        }
        return skuName;
    }

    /**
     * 价格加单位，设置单位字体大小
     */
    public static SpannableStringBuilder priceUnitSetSize(String price, int size) {
        if (StringUtil.isEmpty(price)) {
            price = "0.00";
        }
        price = StringUtil.getDoublePrecision(price, "0.00");

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("¥").append(price);

        /*ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        builder.setSpan(colorSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);*/

        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(DensityUtil.sp2px(size));
        builder.setSpan(sizeSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        return builder;
    }

    /**
     * "字符串" -> "¥字符串"
     */
    public static SpannableStringBuilder addRMB(String str, int size) {
        if (StringUtil.isEmpty(str)) {
            str = "0.00";
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("¥").append(str);

        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(DensityUtil.sp2px(size));
        builder.setSpan(sizeSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        return builder;
    }


    /**
     * 获取搜索历史记录
     *
     * @param context
     * @param FileName
     * @param key
     * @return
     */
    public static List<PoiItem> getValue(Context context, String FileName, String key) {
        String value = SPUtil.readString(context, FileName, key);
        List<PoiItem> searchHistory = new ArrayList<PoiItem>();
        if (value == null) {
            return new ArrayList<>();
        }
        LogUtil.e(value + "取值");
        try {
            JSONArray jsonArray = new JSONArray(value);
            List<PoiItem> list = new Gson().fromJson(value, new TypeToken<List<PoiItem>>() {
            }.getType());
            searchHistory.clear();
            for (int i = 0; i < list.size(); i++) {
                searchHistory.add(list.get(i));
            }
            LogUtil.e(searchHistory.toString() + "取值");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return searchHistory;
    }

    /**
     * @param context
     * @param value   要保存的值
     * @param key     要保存的key
     */
    public static void SetSearchHistory(String FILENAME, Context context, PoiItem value, String key) {
        boolean issame = false;
        List<PoiItem> stringList = new ArrayList<PoiItem>();
        stringList.clear();
        stringList = getValue(context, FILENAME, key);
        LogUtil.e(stringList.toString() + "得到之前的");
        for (int i = 0; i < stringList.size(); i++) {
            if (stringList.get(i).getLatLonPoint().getLatitude() ==
                    value.getLatLonPoint().getLatitude() &&
                    stringList.get(i).getLatLonPoint().getLongitude() ==
                            value.getLatLonPoint().getLongitude()) {
                issame = true;
                break;
            }
        }
        if (!issame) {
            stringList.add(0, value);
        }
        LogUtil.e(issame + "");
        Type type = new TypeToken<List<PoiItem>>() {
        }.getType();
        String jsonListTest = new Gson().toJson(stringList, type);
        SPUtil.write(context, FILENAME, key, jsonListTest.toString());
    }

    /**
     * 获取view在全局中的X轴坐标点
     *
     * @param view
     * @return
     */
    public static float getContentX(View view) {
        int x = 0;
        Activity context = (Activity) view.getContext();
        View content = ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
        x += view.getX();
        ViewGroup parent = (ViewGroup) view.getParent();

        while (parent != content) {
            x += parent.getX();
            parent = (ViewGroup) parent.getParent();
        }
        return x;
    }

    /**
     * 获取view在全局中的Y轴坐标点
     *
     * @param view
     * @return
     */
    public static float getContentY(View view) {
        int y = 0;
        Activity context = (Activity) view.getContext();
        View content = ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
        y += view.getY();
        ViewGroup parent = (ViewGroup) view.getParent();

        while (parent != content) {
            y += parent.getY();
            parent = (ViewGroup) parent.getParent();
        }
        return y;
    }


    /**
     * 设置view在全局中的X轴坐标点
     *
     * @param view
     * @param x
     */
    public static void setContentX(View view, float x) {
        Activity context = (Activity) view.getContext();
        View content = ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
        ViewGroup parent = (ViewGroup) view.getParent();
        while (parent != content) {
            x -= parent.getX();
            parent = (ViewGroup) parent.getParent();
        }
        view.setX(x);
    }

    /**
     * 设置view在全局中的Y轴坐标点
     *
     * @param view
     * @param y
     */
    public static void setContentY(View view, float y) {
        Activity context = (Activity) view.getContext();
        View content = ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
        ViewGroup parent = (ViewGroup) view.getParent();
        while (parent != content) {
            y -= parent.getY();
            parent = (ViewGroup) parent.getParent();
        }
        view.setY(y);
    }

    /**
     * 通过图片url生成Bitmap对象
     *
     * @param urlpath
     * @return Bitmap
     * 根据图片url获取图片对象
     */
    public static Bitmap getBitMBitmap(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static ScaleAnimation startAnim(View view) {
        ScaleAnimation animation_suofang = new ScaleAnimation(0.9f, 1.0f,
                0.9f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_suofang.setDuration(300);                        //执行时间
        animation_suofang.setRepeatCount(-1);                    //重复执行动画
        animation_suofang.setRepeatMode(Animation.REVERSE);    //重复 缩小和放大效果
        view.startAnimation(animation_suofang);
        return  animation_suofang;
    }
}