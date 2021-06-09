package com.android.orangetravel.base.utils;


import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间转换工具类
 *
 * @author yangfei
 */
public final class DateUtil {

    private DateUtil() {
        // 这个类不能实例化
    }

    public static final String FORMAT_TO_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_TO_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_TO_HOUR = "yyyy-MM-dd HH";
    public static final String FORMAT_TO_DAY = "yyyy-MM-dd";
    public static final String FORMAT_TO_DAY_TV = "yyyy年MM月dd日";
    public static final String FORMAT_TO_DAYANDRHOUER = "MM-dd HH:mm";
    public static final String FORMAT_TO_MONTHDAY = "MM/dd";

    /**
     * 时间戳 --> yyyy-MM-dd HH:mm:ss
     */
    public static String timestampToStr(@NonNull String timestamp, @NonNull String format) {
        if (StringUtil.isEmpty(timestamp)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(timestamp + "000")));
    }

    /**
     * Date --> yyyy-MM-dd HH:mm:ss
     */
    public static String date2Strtime(Date mDate, String format) {
        if (null == mDate) {
            return "";
        }
        if (StringUtil.isEmpty(format))
            format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(mDate);
    }

    /**
     * yyyy-MM-dd HH:mm:ss --> 时间戳
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //获取格式化的时间  秒转小时
    public static String getDate(Integer date) {
        if (date < 60) {
            return date + "秒";
        } else if (date > 60 && date < 3600) {
            int m = date / 60;
            int s = date % 60;
//            return m + "分" + s + "秒";
            return m + "分钟";
        } else {
            int h = date / 3600;
            int m = (date % 3600) / 60;
            int s = (date % 3600) % 60;
//            return h + "小时" + m + "分" + s + "秒";
            return h + "小时" + m + "分钟";
        }

    }

    /**
     * 时间戳 --> 几分钟前,几天前,几个月前
     */
    public static String timeStampFormat(String timeStamp) {
        if (StringUtil.isEmpty(timeStamp)) {
            return "未知";
        }
        long currentTimeStamp = System.currentTimeMillis() / 1000;// 当前的时间戳
        long pastTimeStamp = Long.parseLong(timeStamp);// 过去的时间戳
        long poor = currentTimeStamp - pastTimeStamp;// 相差的时间(秒)
        // 单位秒
        long second = 1;// 秒
        long minute = second * 60;// 分钟
        long hour = minute * 60;// 小时
        long day = hour * 24;// 天
        long week = day * 7;// 周
        long month = day * 30;// 月

        if (poor < minute)
            return poor / second + "秒前";
        else if (poor < hour) {
            return poor / minute + "分钟前";
        } else if (poor < day) {
            return poor / hour + "小时前";
        } else if (poor < week) {
            return poor / day + "天前";
        } else if (poor < month) {
            return poor / week + "周前";
        } else {
            return poor / month + "个月前";
        }
    }

    /**
     * 取得当前时间戳(精确到秒)
     */
    public static String currentTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 取得当前时间戳(精确到毫秒)
     */
    public static String currentTimeStamp2() {
        return String.valueOf(System.currentTimeMillis());
    }

//    /**
//     * 计算两个日期型的时间相差多少时间
//     *
//     * @param
//     * @return
//     */
//    @SuppressLint("SimpleDateFormat")
//    public static String twoDateDistance(String endDates) {
//        endDates = timeStamp2Date(endDates, "yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = dfs.format(new Date());
//        Date startDate = null;
//        try {
//            startDate = dfs.parse(date);
//            Date endDate = dfs.parse(endDates);
//            if (startDate == null || endDate == null) {
//                return null;
//            }
//            long timeLong = (endDate.getTime() - startDate.getTime()) / -1000;// 单位毫秒，除以1000转换成秒
//            // 单位秒
//            long month = 60 * 60 * 24 * 7 * 4;// 月
//            long week = 60 * 60 * 24 * 7;// 周
//            long day = 60 * 60 * 24;// 天
//            long hour = 60 * 60;// 小时
//            long minute = 60; // 分钟
//            long second = 1; // 秒
//
//            if (timeLong < minute)
//                return timeLong / second + "秒前";
//            else if (timeLong < hour) {
//                timeLong = timeLong / minute;
//                return timeLong + "分钟前";
//            } else if (timeLong < day) {
//                timeLong = timeLong / hour;
//                return timeLong + "小时前";
//            } else if (timeLong < week) {
//                timeLong = timeLong / day;
//                return timeLong + "天前";
//            } else if (timeLong < month) {
//                timeLong = timeLong / week;
//                return timeLong + "周前";
//            } else {
//                // 如果超过几周则显示日期
//                String str = new String(endDates);
//                String date1[] = str.split(" ");
//                return date1[0];
//            }
//        } catch (ParseException e) {//
//            e.printStackTrace();
//        }
//        return dfs.format(startDate);
//
//    }

//    public static boolean timeCompare(String startTime, String endTime) {
//        try {
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            Date startDate = df.parse(startTime);
//            Date endDate = df.parse(endTime);
//            long diff = endDate.getTime() - startDate.getTime();
//            long min = diff / (1000 * 60);
//            if (min < 1)
//                return false;
//            else
//                return true;
//        } catch (Exception e) {
//        }
//        return true;
//    }

//    /**
//     * 比较日期
//     **/
//    public static boolean dateCompare(String startTime, String endTime) {
//        try {
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            Date startDate = df.parse(startTime);
//            Date endDate = df.parse(endTime);
//            long diff = endDate.getTime() - startDate.getTime();
//            long min = diff / (1000 * 60);
//            if (min < 0)
//                return false;
//            else
//                return true;
//        } catch (Exception e) {
//        }
//        return true;
//    }

//    /**
//     * 比较日期是不是三天以内包括今天
//     **/
//    public static boolean dateCompares(String startTime, String endTime) {
//        try {
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            Date startDate = df.parse(startTime);
//            Date endDate = df.parse(endTime);
//            long diff = endDate.getTime() - startDate.getTime();
//            long day = diff / (1000 * 60 * 60 * 24);
//            if (day <= 3)
//                return false;
//            else
//                return true;
//        } catch (Exception e) {
//        }
//        return true;
//    }

//    /**
//     * 时间戳转换成日期格式字符串
//     *
//     * @param seconds 精确到秒的字符串
//     * @return
//     */
//    public static String timeStamp2Date(String seconds) {
//        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
//            return "";
//        }
//        String format = "yyyy-MM-dd HH:mm:ss";
//        SimpleDateFormat sdf = new SimpleDateFormat(format);
//        return sdf.format(new Date(Long.valueOf(seconds + "000")));
//    }

//    /**
//     * 得到当前的时间
//     *
//     * @return 例如：2015-01-06 22:56
//     */
//    public static String getStringDate() {
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateString = formatter.format(currentTime);
//        return dateString;
//    }

//    /**
//     * 得到当前的年月日
//     *
//     * @return 例如：2015-01-06 22:56
//     */
//    public static String getDate() {
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = formatter.format(currentTime);
//        return dateString;
//    }

//    /**
//     * 得到当前的时间
//     *
//     * @param format
//     * @return
//     */
//    public static String getStringDate(String format) {
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat(format);
//        String dateString = formatter.format(currentTime);
//        return dateString;
//    }

//    /**
//     * 计算时间差
//     *
//     * @param startTime
//     * @param endTime
//     * @param format
//     * @return
//     */
//    public static String TimeDifference(String startTime, String endTime, String format) {
//        String str = "";
//        try {
//            SimpleDateFormat sd = new SimpleDateFormat(format);
//            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//            long nh = 1000 * 60 * 60;// 一小时的毫秒数
//            long nm = 1000 * 60;// 一分钟的毫秒数
//            long ns = 1000;// 一秒钟的毫秒数long diff;try {
//            // 获得两个时间的毫秒时间差异
//            long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
//            long day = diff / nd;// 计算差多少天
//            long hour = diff % nd / nh;// 计算差多少小时
//            long min = diff % nd % nh / nm;// 计算差多少分钟
//            long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果
//            if (day > 0) {
//                str += day + "天";
//            }
//            if (hour > 0) {
//                str += hour + "小时";
//            }
//            if (min > 0) {
//                str += min + "分钟";
//            }
//            if (sec > 0) {
//                str += sec + "秒";
//            }
//        } catch (Exception e) {
//        }
//        return str;
//    }

//    /**
//     * 计算时间差
//     *
//     * @param startTime
//     * @param endTime
//     * @return
//     */
//    public static String TimeDifference(String startTime, String endTime) {
//        String str = "剩余";
//        try {
//            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//            long nh = 1000 * 60 * 60;// 一小时的毫秒数
//            long nm = 1000 * 60;// 一分钟的毫秒数
//            long ns = 1000;// 一秒钟的毫秒数
//            // 获得两个时间的毫秒时间差异
//            Date start = new Date();
//            Date end = new Date();
//            start = sd.parse(timeStamp2Date(startTime, ""));
//            end = sd.parse(timeStamp2Date(endTime, ""));
//            long diff = end.getTime() - start.getTime();
//            long day = diff / nd;// 计算差多少天
//            long hour = diff % nd / nh;// 计算差多少小时
//            long min = diff % nd % nh / nm;// 计算差多少分钟
//            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
//            if (day > 0) {
//                str += day + "天";
//            }
//            if (hour > 0) {
//                str += hour + "小时";
//            }
//            if (min > 0) {
//                str += min + "分钟";
//            }
//            if (sec > 0) {
//                str += sec + "秒";
//            }
//            if (diff < 0) {
//                str = "已过期";
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }

//    /**
//     * 结束日期是否大于开始日期
//     *
//     * @param startTime 开始时间 为时间戳类型
//     * @param endTime   结束时间 为时间戳类型
//     * @return true 表示为真
//     */
//    public static boolean TimeComparison(String startTime, String endTime) {
//        boolean mAfter = true;
//        try {
//            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//            long nh = 1000 * 60 * 60;// 一小时的毫秒数
//            long nm = 1000 * 60;// 一分钟的毫秒数
//            long ns = 1000;// 一秒钟的毫秒数
//            // 获得两个时间的毫秒时间差异
//            Date start = new Date();
//            Date end = new Date();
//            start = sd.parse(timeStamp2Date(startTime, ""));
//            end = sd.parse(timeStamp2Date(endTime, ""));
//            long diff = end.getTime() - start.getTime();
//            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
//            if (sec < 0) {
//                mAfter = false;
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return mAfter;
//    }

//    public static String TimeDifferenceDay(String startTime, String endTime) {
//        String str = "";
//        try {
//            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd:mm:ss");
//            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//            long nh = 1000 * 60 * 60;// 一小时的毫秒数
//            // 获得两个时间的毫秒时间差异
//            long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
//            long day = diff / nd;// 计算差多少天
//            long hour = diff % nd / nh;// 计算差多少小时
//            if (day > 0) {
//                str += day + "天";
//            }
//            if (hour > 0) {
//                str += hour + "小时";
//            }
//            if (diff < 0) {
//                str = "已过期";
//            }
//        } catch (Exception e) {
//        }
//        return str;
//    }

//    /**
//     * 获取现在时间
//     *
//     * @return 返回短时间字符串格式yyyy-MM-dd
//     */
//    public static String getStringDateShort() {
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = formatter.format(currentTime);
//        return dateString;
//    }

//    /**
//     * 获取时间 小时:分;秒 HH:mm:ss
//     *
//     * @return
//     */
//    public static String getTimeShort() {
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//        Date currentTime = new Date();
//        String dateString = formatter.format(currentTime);
//        return dateString;
//    }

//    /**
//     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
//     *
//     * @param strDate
//     * @return
//     */
//    public static Date strToDateLong(String strDate) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        ParsePosition pos = new ParsePosition(0);
//        Date strtodate = formatter.parse(strDate, pos);
//        return strtodate;
//    }

//    /**
//     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
//     *
//     * @param dateDate
//     * @return
//     */
//    public static String dateToStrLong(Date dateDate) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateString = formatter.format(dateDate);
//        return dateString;
//    }

//    /**
//     * 将短时间格式时间转换为字符串 yyyy-MM-dd
//     *
//     * @param dateDate
//     * @return
//     */
//    public static String dateToStr(Date dateDate) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = formatter.format(dateDate);
//        return dateString;
//    }

//    /**
//     * 将短时间格式字符串转换为时间 yyyy-MM-dd
//     *
//     * @param strDate
//     * @return
//     */
//    public static Date strToDate(String strDate) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        ParsePosition pos = new ParsePosition(0);
//        Date strtodate = formatter.parse(strDate, pos);
//        return strtodate;
//    }

//    /**
//     * 得到现在时间
//     *
//     * @return
//     */
//    public static Date getNow() {
//        Date currentTime = new Date();
//        return currentTime;
//    }

//    /**
//     * 提取一个月中的最后一天
//     *
//     * @param day
//     * @return
//     */
//    public static Date getLastDate(long day) {
//        Date date = new Date();
//        long date_3_hm = date.getTime() - 3600000 * 34 * day;
//        Date date_3_hm_date = new Date(date_3_hm);
//        return date_3_hm_date;
//    }

//    /**
//     * 得到现在时间
//     *
//     * @return 字符串 yyyyMMdd HHmmss
//     */
//    public static String getStringToday() {
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
//        String dateString = formatter.format(currentTime);
//        return dateString;
//    }

//    /**
//     * 得到现在小时
//     */
//    public static String getHour() {
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateString = formatter.format(currentTime);
//        String hour;
//        hour = dateString.substring(11, 13);
//        return hour;
//    }

//    /**
//     * 得到现在分钟
//     *
//     * @return
//     */
//    public static String getTime() {
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateString = formatter.format(currentTime);
//        String min;
//        min = dateString.substring(14, 16);
//        return min;
//    }

//    /**
//     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
//     *
//     * @param sformat yyyyMMddhhmmss
//     * @return
//     */
//    public static String getUserDate(String sformat) {
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
//        String dateString = formatter.format(currentTime);
//        return dateString;
//    }

//    /**
//     * @param strDate 当前时间
//     * @param pattern 时间格式
//     * @return
//     */
//    public static Date strToDate(String strDate, String pattern) {
//        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//        ParsePosition pos = new ParsePosition(0);
//        Date strtodate = formatter.parse(strDate, pos);
//        return strtodate;
//    }

//    /**
//     * @param strDate 时间
//     * @param pattern 时间格式
//     * @return
//     */
//    public static String getDate(String strDate, String pattern) {
//        Date currentTime = strToDate(strDate, pattern);
//        SimpleDateFormat format1 = new SimpleDateFormat(pattern);
//        String dateString = format1.format(currentTime);
//        return dateString;
//    }

    //    /**
//     * 获取时间差
//     */
//    public static String getTimePoor(String startTime, String endTime, String format) {
//        String str = "";
//        try {
//            SimpleDateFormat sd = new SimpleDateFormat(format);// yyyy-MM-dd HH:mm:ss
//            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//            long nh = 1000 * 60 * 60;// 一小时的毫秒数
//            long nm = 1000 * 60;// 一分钟的毫秒数
//            long ns = 1000;// 一秒钟的毫秒数
//            // 获得两个时间的毫秒时间差异
//            Date start = new Date();
//            Date end = new Date();
//            start = sd.parse(timeStamp2Date(startTime, format));
//            end = sd.parse(timeStamp2Date(endTime, format));
//            long diff = end.getTime() - start.getTime();
//            long day = diff / nd;// 计算差多少天
//            long hour = diff % nd / nh;// 计算差多少小时
//            long min = diff % nd % nh / nm;// 计算差多少分钟
//            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
//            if (day > 0) {
//                str += day + "天";
//            }
//            if (hour > 0) {
//                str += hour + "小时";
//            }
//            if (min > 0) {
//                str += min + "分钟";
//            }
//            if (sec > 0) {
//                str += sec + "秒";
//            }
//            if (diff < 0) {
//                str = "已过期";
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }
//根据秒数转化为时分秒   00:00:00
    public static String getTime(int second) {
        if (second < 10) {
            return "00:0" + second;
        }
        if (second < 60) {
            return "00:" + second;
        }
        if (second < 3600) {
            int minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                }
                return "0" + minute + ":" + second;
            }
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
        int hour = second / 3600;
        int minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour + minute + ":0" + second;
            }
            return "0" + hour + minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour + minute + ":0" + second;
        }
        return hour + minute + ":" + second;
    }

    /**
     * 根据提供的年月获取该月份的最后一天
     *
     * @param year
     * @param monthOfYear
     * @return
     * @Description:
     * @Author: gyz
     * @Since: 2017-1-9下午2:29:38
     */
    public static Date getSupportEndDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return lastDate;
    }
}