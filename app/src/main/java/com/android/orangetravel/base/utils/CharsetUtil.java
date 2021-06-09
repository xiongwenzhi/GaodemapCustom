package com.android.orangetravel.base.utils;

import java.io.UnsupportedEncodingException;

/**
 * 转换字符编码的工具类
 *
 * @author yangfei
 */
public final class CharsetUtil {

    private CharsetUtil() {
        // 这个类不能实例化
    }

    /**
     * 将字符编码转换成UTF-8
     */
    public static String toUTF_8(String str) throws UnsupportedEncodingException {
        return changeCharset(str, "UTF-8");// 8位 UCS 转换格式
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换的字符串
     * @param newCharset 目标编码
     */
    public static String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。与系统相关，中文windows默认为GB2312
            byte[] bs = str.getBytes();
            return new String(bs, newCharset);// 用新的字符编码生成字符串
        }
        return "";
    }

//    /**
//     * 将字符编码转换成US-ASCII码
//     */
//    public static String toASCII(String str) throws UnsupportedEncodingException {
//        return changeCharset(str, "US-ASCII");// 7位ASCII字符，也叫作ISO646-US，Unicode字符集的基本拉丁块
//    }

//    /**
//     * 将字符编码转换成ISO-8859-1
//     */
//    public static String toISO_8859_1(String str) throws UnsupportedEncodingException {
//        return changeCharset(str, "ISO-8859-1");// ISO拉丁字母表 No.1，也叫做ISO-LATIN-1
//    }

//    /**
//     * 将字符编码转换成UTF-16BE
//     */
//    public static String toUTF_16BE(String str) throws UnsupportedEncodingException {
//        return changeCharset(str, "UTF-16BE");// 16位 UCS 转换格式，Big Endian(最低地址存放高位字节)字节顺序
//    }

//    /**
//     * 将字符编码转换成UTF-16LE
//     */
//    public static String toUTF_16LE(String str) throws UnsupportedEncodingException {
//        return changeCharset(str, "UTF-16LE");// 16位 UCS 转换格式，Litter Endian(最高地址存放地位字节)字节顺序
//    }

//    /**
//     * 将字符编码转换成UTF-16
//     */
//    public static String toUTF_16(String str) throws UnsupportedEncodingException {
//        return changeCharset(str, "UTF-16");// 16位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识
//    }

//    /**
//     * 将字符编码转换成GBK
//     */
//    public static String toGBK(String str) throws UnsupportedEncodingException {
//        return changeCharset(str, "GBK");// 中文超大字符集
//    }

//    /**
//     * 将字符编码转换成GB2312
//     */
//    public static String toGB2312(String str) throws UnsupportedEncodingException {
//        return changeCharset(str, "GB2312");
//    }

//    /**
//     * 字符串编码转换的实现方法
//     *
//     * @param str        待转换的字符串
//     * @param oldCharset 源字符集
//     * @param newCharset 目标字符集
//     */
//    public static String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException {
//        if (str != null) {
//            // 用源字符编码解码字符串
//            byte[] bs = str.getBytes(oldCharset);
//            return new String(bs, newCharset);
//        }
//        return null;
//    }

}