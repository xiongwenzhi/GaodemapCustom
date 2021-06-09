package com.android.orangetravel.base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.android.orangetravel.base.base.BaseApp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 *
 * @author yangfei
 */
public final class StringUtil {

    private StringUtil() {
        // 这个类不能实例化
    }

    /**
     * 判断字符串不为空
     */
    public static boolean isNotEmpty(String str) {
        return null != str && !"null".equals(str.toLowerCase()) && str.length() != 0;
    }

    /**
     * 判断字符串为空
     */
    public static boolean isEmpty(String str) {
        return null == str || "null".equals(str.toLowerCase()) || str.length() == 0 || "".equals(str);
    }

    /**
     * 判断List不为空
     */
    public static boolean isListNotEmpty(List<?> mList) {
        return null != mList && mList.size() > 0;
    }

    /**
     * 判断List为空
     */
    public static boolean isListEmpty(List<?> mList) {
        return null == mList || mList.size() == 0;
    }

    /**
     * 判断JSON数组不为空
     */
    public static boolean isJSONArrayNotEmpty(JSONArray array) {
        return null != array && array.length() > 0;
    }

    /**
     * 判断JSON数组为空
     */
    public static boolean isJSONArrayEmpty(JSONArray array) {
        return null == array || array.length() == 0;
    }

    /**
     * 判断JSON对象不为空
     */
    public static boolean isJSONObjectNotEmpty(JSONObject object) {
        return null != object && object.length() > 0;
    }

    /**
     * 判断JSON对象为空
     */
    public static boolean isJSONObjectEmpty(JSONObject object) {
        return null == object || object.length() == 0;
    }

//    /**
//     * 身份证正则判断
//     */
//    public static boolean isIdCard(String idCode) {
//        if (isEmpty(idCode))
//            return false;
//        Pattern p = Pattern.compile("^([1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3})|([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[X,x]))$");
//        Matcher matcher = p.matcher(idCode);
//        return matcher.find();
//    }

    /**
     * 判断不是正确的身份证
     */
    public static boolean isNotIdCard(String idCard) {
        if (isEmpty(idCard))
            return true;
        return !Pattern.compile("^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$").matcher(idCard).find();
    }

//    /**
//     * 手机号正则判断
//     */
//    public static boolean isMobileNO(String mobile) {
//        if (isEmpty(mobile))
//            return false;
//        // Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//        Pattern p = Pattern.compile("(\\+\\d+)?1[3458]\\d{9}$");
//        Matcher m = p.matcher(mobile);
//        return m.matches();
//    }

//    /**
//     * 邮箱号正则判断
//     */
//    public static boolean isEmail(String email) {
//        if (isEmpty(email))
//            return false;
//        Pattern p = Pattern.compile("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$");
//        Matcher m = p.matcher(email);
//        return m.matches();
//    }

    /**
     * HTML转义字符 --> 字符串
     */
    public static String htmlEscapeCharsToString(String source) {
        return isEmpty(source) ? source : source.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&")
                .replaceAll("&quot;", "\"")
                .replaceAll("&copy;", "©")
                .replaceAll("&yen;", "¥")
                .replaceAll("&divide;", "÷")
                .replaceAll("&times;", "×")
                .replaceAll("&reg;", "®")
                .replaceAll("&sect;", "§")
                .replaceAll("&pound;", "£")
                .replaceAll("&cent;", "￠");
    }

//    /**
//     * 用户名正则判断
//     */
//    public static boolean isUserName(String id) {
//        if (isEmpty(id))
//            return false;
//        // 字母开头，由字母，数字和下划线组成的长度为2到16的字符串
//        Pattern p = Pattern.compile("^[a-zA-Z0-9_-]{2,16}$");
//        Matcher m = p.matcher(id);
//        return m.find();
//    }

    /**
     * 密码正则判断
     */
    public static boolean isPassWord(String password) {
        if (isEmpty(password))
            return false;
        // 就是以大小写字母开头,由大小写字母,数字和下划线组成的长度为6到18的字符串
        Pattern p = Pattern.compile("^[A-Z][a-z0-9_]{6,18}$");
//        Pattern p = Pattern.compile("^[A-Z]\\w{8,}");
        Matcher m = p.matcher(password);
        return m.find();
    }

//    /**
//     * 银行卡号正则判断
//     */
//    public static boolean isBandCode(String bankCode) {
//        if (isEmpty(bankCode))
//            return false;
//        // 16或19位，都是数字
//        Pattern p = Pattern.compile("^\\d{16}$|^\\d{19}$");
//        Matcher m = p.matcher(bankCode);
//        return m.find();
//    }

//    /**
//     * 从Raw中读取
//     */
//    public static String getFromRaw(Context mContext, int resId) {
//        String Result = "";
//        try {
//            InputStreamReader inputReader = new InputStreamReader(mContext.getResources().openRawResource(resId));
//            BufferedReader bufReader = new BufferedReader(inputReader);
//            String line = "";
//            while ((line = bufReader.readLine()) != null)
//                Result += line;
//        } catch (Exception e) {
//        } finally {
//            return Result;
//        }
//    }

    /**
     * 从Assets中读取
     */
    public static String getFromAssets(Context mContext, String fileName) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(mContext.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                Result += line;
            }
        } catch (Exception e) {
        } finally {
            return Result;
        }
    }

//    /**
//     * 判断是否包含字符串
//     */
//    public static boolean isContains(String s1, String s2) {
//        return s1.contains(s2);
//    }

//    /**
//     * 金额正则判断
//     */
//    public static boolean isMoney(String money) {
//        // ^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$   自己找
//        // ^([0-9]+|[0-9]{1,3}(,[0-9]{3})*)(.[0-9]{1,2})?$  老艾的
//        Pattern p = Pattern.compile("^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,2})?))$");
//        Matcher m = p.matcher(money);
//        return m.matches();
//    }

//    /**
//     * 判断金额大于百万
//     */
//    public static boolean isMoneyMillion(String money) {
//        Pattern p = Pattern.compile("^((([1-9](\\.\\d+)?))|(([1-9][0-9]{1,5})(\\.\\d+)?)|([1][0]{6}))$");
//        Matcher m = p.matcher(money);
//        return m.matches();
//    }

    /**
     * String --> int
     */
    public static int getInteger(String count) {
        if (isEmpty(count))
            return 0;
        return Integer.parseInt(count);
    }

    /**
     * String --> double
     */
    public static double getDouble(String count) {
        if (isEmpty(count))
            return 0.0;
        return Double.parseDouble(count);
    }

    /**
     * double相加
     */
    public static double addDouble(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * double相减
     */
    public static double subDouble(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * double相乘
     */
    public static double mulDouble(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 精确到小数点后几位(例："0.0"，"0.00")
     */
    public static String getDoublePrecision(String v1, String type) {
        BigDecimal subA = new BigDecimal(v1);
        DecimalFormat df = new DecimalFormat(type);
        return df.format(subA.doubleValue());
    }

    /**
     * 判断不是正确的手机号
     */
    public static boolean isNotMobileNo(String mobile) {
        if (isEmpty(mobile))
            return true;
        /*if (mobile.length() != 11)
            return true;
        if (!"1".equals(mobile.substring(0, 1)))
            return true;
        return false;*/
        return !Pattern.compile("^1[0-9]{10}$").matcher(mobile).find();
    }

//    /**
//     * 秒数 --> 00:00
//     */
//    public static String secondsFormat(int s) {
//        int ss = s % 60;
//        s = s - ss;
//        int mm = s / 60;
//        // return mm + ":" + ss;
//
//        String mmStr = String.valueOf(mm);
//        String ssStr = String.valueOf(ss);
//        if (mmStr.length() < 2) {
//            mmStr = "0" + mmStr;
//        }
//        if (ssStr.length() < 2) {
//            ssStr = "0" + ssStr;
//        }
//        return mmStr + ":" + ssStr;
//    }

    /**
     * InputStream --> String
     */
    public static String inputStream2String(InputStream is) {
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                sb.append(line);
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != isr) {
                    isr.close();
                    isr = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

//    public static String GetWebview(String info, WebView webView) {
//        // 适配手机屏幕,webview
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
//            webView.getSettings().setUseWideViewPort(true);
//            webView.getSettings().setLoadWithOverviewMode(true);
//        } else {
//            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//        }
//        String html = "<html>"
//                + "<head>"
//                + "<style type='text/css'>"
//                + "img,table{width: 100% !important;height: auto !important;}"
//                + "body,div,td,th{font-size: 1em;line-height: 1.3em;}"
//                + "</style>"
//                + "<meta name='viewport' content='width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no'> </head>"
//                + "<body style='padding:0px;margin:0px;'>" + htmlEscapeCharsToString(info)
//                + "</body>"
//                + "</html>";
//        webView.loadDataWithBaseURL("http://wgc.0791jr.com", html, "text/html", "utf-8", null);
//        return null;
//    }

    /**
     * 检测QQ
     */
    public static boolean isQQClientAvailable() {
        final PackageManager packageManager = BaseApp.getAppContext().getPackageManager();
        List<PackageInfo> pi = packageManager.getInstalledPackages(0);
        if (null != pi) {
            for (PackageInfo info : pi) {
                String name = info.packageName;
                if (ConstantUtil.QQ_PACKAGE_NAME.equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断APP是否安装
     *
     * @param context     上下文
     * @param packageName 包名 QQ包名->com.tencent.mobileqq 微信包名->com.tencent.mm
     * @Return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取系统中安装的所有软件信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }


}