package com.android.orangetravel.base.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 缓存工具类
 *
 * @author Only You
 */
public final class CacheUtil {

    private CacheUtil() {
        // 这个类不能实例化
    }

    /**
     * JSON数据 --> 缓存到本地
     */
    public static void saveJsonData(Context mContext, String url, String jsonStr) {
        String urlMD5 = MD5Util.newInstance().getkeyBeanofStr(url);
        File file = new File(FileUtil.getExternalCacheDir(mContext), urlMD5);

        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(System.currentTimeMillis() + "");
            bw.newLine();
            bw.write(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bw)
                    bw.close();
                if (null != fos)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 缓存本地数据 --> JSON数据
     */
    public static String getJsonData(Context mContext, String url) {
        String urlMD5 = MD5Util.newInstance().getkeyBeanofStr(url);
        File file = new File(FileUtil.getExternalCacheDir(mContext), urlMD5);

        StringBuffer sb = new StringBuffer();
        FileReader fr = null;
        BufferedReader br = null;
        if (file.exists()) {
            try {
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                br.readLine();
                String temp;
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != br)
                        br.close();
                    if (null != fr)
                        fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return sb.toString();
            }
        }
        return sb.toString();
    }

}