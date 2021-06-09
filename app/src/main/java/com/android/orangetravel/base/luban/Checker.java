package com.android.orangetravel.base.luban;

import com.android.orangetravel.base.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查数据
 */
public class Checker {

    private static List<String> format = new ArrayList<>();
    private static final String JPG = "jpg";
    private static final String JPEG = "jpeg";
    private static final String PNG = "png";
    private static final String WEBP = "webp";
    private static final String GIF = "gif";

    static {
        format.add(JPG);
        format.add(JPEG);
        format.add(PNG);
        format.add(WEBP);
        format.add(GIF);
    }

    /**
     * 是否是图片
     */
    public static boolean isImage(String path) {
        if (StringUtil.isEmpty(path)) {
            return false;
        }
        String suffix = path.substring(path.lastIndexOf(".") + 1, path.length()).toLowerCase();
        return format.contains(suffix);
    }

    /**
     * 是否是jpg
     */
    public static boolean isJPG(String path) {
        if (StringUtil.isEmpty(path)) {
            return false;
        }
        String suffix = path.substring(path.lastIndexOf("."), path.length()).toLowerCase();
        return suffix.contains(JPG) || suffix.contains(JPEG);
    }

    /**
     * 获取后缀名
     */
    public static String checkSuffix(String path) {
        if (StringUtil.isEmpty(path)) {
            return ".jpg";
        }
        return path.substring(path.lastIndexOf("."), path.length());
    }

}