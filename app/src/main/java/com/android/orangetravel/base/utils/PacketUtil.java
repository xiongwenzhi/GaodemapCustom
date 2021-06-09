package com.android.orangetravel.base.utils;

import android.os.Build;
import android.text.TextUtils;

import com.android.orangetravel.base.utils.log.LogUtil;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 拼接请求报文的工具类
 *
 * @author yangfei
 */
public final class PacketUtil {

    private PacketUtil() {
        // 这个类不能实例化
    }

    public static String getRequestData(String packetNo, Object params) {
        String result = "";
        JSONObject rows = new JSONObject();
        try {
            // 用户ID
            String userId = (String) SPUtil.get(ConstantUtil.USER_ID, "");
            // 当前时间
            String date = String.valueOf(System.currentTimeMillis());
            // 报文编号 + 用户ID + 当前时间 + key, 然后用MD5加密 = Token
            String token = packetNo + userId + date + ConstantUtil.KEY;
            token = MD5Util.newInstance().getkeyBeanofStr(token);
            rows.put("token", token);
            // 设备ID
            String deviceId = "";
            deviceId = getUUID();
//            TelephonyManager tm = (TelephonyManager) BaseApp.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
//            if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M)) {
//                deviceId = tm.getDeviceId();
//            } else if (PackageManager.PERMISSION_GRANTED ==
//                    ContextCompat.checkSelfPermission(BaseApp.getAppContext(), PermissionConstant.READ_PHONE_STATE)) {
//                deviceId = tm.getDeviceId();
//            }
            rows.put("deviceId", deviceId);
            // 报文编号
            rows.put("pack_no", packetNo);
            // 请求时间
            rows.put("date", date);
            // 用户ID
            rows.put("user_id", userId);

            // data为空时,添加空的JOSNObject
            if (null == params) {
                rows.put("data", new JSONObject());
            } else {
                if (params instanceof String) {
                    rows.put("data", new JSONObject((String) params));
                } else if (params instanceof Map) {
                    rows.put("data", new JSONObject((Map) params));
                } else {
                    rows.put("data", params);
                }
            }

            result = CharsetUtil.toUTF_8(rows.toString());
            LogUtil.e("请求报文 " + result);
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 一般获取数据用的
     *
     * @param params
     * @return
     */
    public static JsonObject getNewRequestData(Map<String, String> params) {
        String result = "";
        JsonObject rows = new JsonObject();
        try {
            // 用户ID
            String token = (String) SPUtil.get(ConstantUtil.USER_ID, "btw");
//            token = CharsetUtil.toUTF_8(token.toString());
            // 当前时间
            String date = String.valueOf(System.currentTimeMillis());
            if (params == null) {
                rows.addProperty("token", TextUtils.isEmpty(token) ? "btw" : token);
            } else {
                if (params.containsKey("notoken")) { //如果不用token加密
                    rows.addProperty("token", "");
                } else {
                    rows.addProperty("token", TextUtils.isEmpty(token) ? "btw" : token);
                }
            }
            // 请求时间
            rows.addProperty("time", date);
            String key;
            if (params == null) {
                key = token + "btw" + date;
            } else {
                // key
                if (params.containsKey("notoken")) { //如果包含notoken则不用token加密
                    key = "btw" + date;
                } else {
                    key = token + "btw" + date;
                }
            }
            rows.addProperty("key", MD5Util.MD5Encode(key, "utf8"));
            if (params != null) {
                for (String mapkey : params.keySet()) {
                    rows.addProperty(mapkey, params.get(mapkey));
                }
            }
            LogUtil.e("请求报文 " + rows);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    /**
     * 一般获取数据用的(带加密参数的)
     *
     * @param params
     * @return
     */
    public static JsonObject getNewRequestData(Map<String, String> params, String encryption) {
        String result = "";
        JsonObject rows = new JsonObject();
        try {
            // 用户ID
            String token = (String) SPUtil.get(ConstantUtil.USER_ID, "");
//            token = CharsetUtil.toUTF_8(token.toString());
            // 当前时间
            String time = String.valueOf(System.currentTimeMillis());
            rows.addProperty("token", token);
            // 请求时间
            rows.addProperty("time", time);
            String key = TextUtils.isEmpty(encryption) ? token : encryption + "btw" + time;
            // key
            rows.addProperty("key", MD5Util.MD5Encode(key, "utf8"));
            if (params != null) {
                for (String mapkey : params.keySet()) {
                    rows.addProperty(mapkey, params.get(mapkey));
                }
            }
            LogUtil.e("请求报文 " + rows);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    /**
     * requestData  +  新的加密方式  (上传数据用的)
     *
     * @param
     * @param params
     * @return
     */
    public static Map<String, Object> getrequestDataData(Map<String, Object> rows, String params) {
        String result = "";
//        Map<String, Object> rows = map;
//        JsonObject rows = new JsonObject();
        try {
            // 用户ID
            String token = (String) SPUtil.get(ConstantUtil.USER_ID, "");
            // 当前时间
            String time = String.valueOf(System.currentTimeMillis());
            rows.put("token", TextUtils.isEmpty(token) ? "btw" : token);
//            if (null != map) {
////                JsonObject object = new JsonObject();
//                Map<String, Object> objectMap = new HashMap<>();
//                for (String mapkey : map.keySet()) {
//                    if (map.get(mapkey) != null) {
//                        if (map.containsKey("out")) { //如果包含out则是要把参数放到requestData外面请求
//                            Map<String, String> out = (Map) map.get("out");
//                            for (String outmapkey : out.keySet()) {
//                                rows.put(outmapkey, out.get(outmapkey).toString());
//                            }
//                        }
//                        objectMap.put(mapkey, map.get(mapkey));
//
//                    }
//                }
//                Gson gson = new Gson();
//                String object = gson.toJson(objectMap);
//                rows.put("requestData", object);
//            }
            // 请求时间
            rows.put("time", time);
            // key
            String key = "";
            if (TextUtils.isEmpty(params)) {
                key = TextUtils.isEmpty(token) ? "btw" + "btw" + time : token + "btw" + time;
            } else {
                key = params + "btw" + time;
            }
            rows.put("key", MD5Util.MD5Encode(key, "utf8"));
            result = CharsetUtil.toUTF_8(rows.toString());
            LogUtil.e("请求报文 " + result);
        } catch (JsonIOException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rows;
    }


    /**
     * requestData  +  新的加密方式  (上传数据用的)
     *
     * @param
     * @param params
     * @return
     */
    public static Map<String, Object> getRequesData(Map<String, Object> map, String params) {
        String result = "";
        Map<String, Object> rows = new HashMap<>();
        try {
            // 用户ID
            String token = (String) SPUtil.get(ConstantUtil.USER_ID, "");
            // 当前时间
            String time = String.valueOf(System.currentTimeMillis());
//            rows.put("token", TextUtils.isEmpty(token) ? "btw" : token);
            if (null != map) {
//                JsonObject object = new JsonObject();
                Map<String, Object> objectMap = new HashMap<>();
                for (String mapkey : map.keySet()) {
                    if (map.get(mapkey) != null) {
                        if (map.containsKey("out")) { //如果包含out则是要把参数放到requestData外面请求
                            Map<String, String> out = (Map) map.get("out");
                            for (String outmapkey : out.keySet()) {
                                rows.put(outmapkey, out.get(outmapkey).toString());
                            }
                        }
                        objectMap.put(mapkey, map.get(mapkey));

                    }
                }
                Gson gson = new Gson();
                Map<String, Object> paramsMap = new HashMap<>();
                if (objectMap.size() > 0) {
                    paramsMap.put("params", objectMap);
                }
                String object = gson.toJson(paramsMap);
//                Gson gson = new Gson();
//                String object = gson.toJson(objectMap);
                rows.put("requestData", object);
            }
            // 请求时间
            rows.put("time", time);
            // key
            String key = "";
            if (TextUtils.isEmpty(params)) {
                key = TextUtils.isEmpty(token) ? "btw" + "btw" + time : token + "btw" + time;
            } else {
                key = params + "btw" + time;
            }
            rows.put("key", MD5Util.MD5Encode(key, "utf8"));
            result = CharsetUtil.toUTF_8(rows.toString());
//            Gson gson = new Gson();
//            valueMap = new HashMap<String, String>();
//            valueMap = gson.fromJson(result, map.getClass());
            LogUtil.e("请求报文 " + result);
        } catch (JsonIOException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rows;
    }


    /**
     * requestData  没有params
     *
     * @param
     * @param params
     * @return
     */
    public static Map<String, Object> getRequesNoParamsData(Map<String, Object> map, String params) {
        String result = "";
        Map<String, Object> rows = new HashMap<>();
        try {
            // 用户ID
            String token = (String) SPUtil.get(ConstantUtil.USER_ID, "");
            // 当前时间
            String time = String.valueOf(System.currentTimeMillis());
//            rows.put("token", TextUtils.isEmpty(token) ? "btw" : token);
            if (null != map) {
//                JsonObject object = new JsonObject();
                Map<String, Object> objectMap = new HashMap<>();
                for (String mapkey : map.keySet()) {
                    if (map.get(mapkey) != null) {
                        if (map.containsKey("out")) { //如果包含out则是要把参数放到requestData外面请求
                            Map<String, String> out = (Map) map.get("out");
                            for (String outmapkey : out.keySet()) {
                                rows.put(outmapkey, out.get(outmapkey).toString());
                            }
                        }
                        objectMap.put(mapkey, map.get(mapkey));

                    }
                }
                Gson gson = new Gson();
//                Map<String, Object> paramsMap = new HashMap<>();
//                if (objectMap.size() > 0) {
//                    paramsMap.put("params", objectMap);
//                }
                String object = gson.toJson(objectMap);
                rows.put("requestData", object);
            }
            // 请求时间
            rows.put("time", time);
            // key
            String key = "";
            if (TextUtils.isEmpty(params)) {
                key = TextUtils.isEmpty(token) ? "btw" + "btw" + time : token + "btw" + time;
            } else {
                key = params + "btw" + time;
            }
            rows.put("key", MD5Util.MD5Encode(key, "utf8"));
            result = CharsetUtil.toUTF_8(rows.toString());
//            Gson gson = new Gson();
//            valueMap = new HashMap<String, String>();
//            valueMap = gson.fromJson(result, map.getClass());
            LogUtil.e("请求报文 " + result);
        } catch (JsonIOException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public static String getUUID() {
        String serial = null;
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serial = Build.getSerial();
            } else {
                serial = Build.SERIAL;
            }
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

}