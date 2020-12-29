package com.jdxl.seedcourse.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期工具类
 *
 */
//@Slf4j
public class DateUtils

{
    /**
     * 默认的日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYYMM = "yyyy-MM";
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYYMMDDHH = "yyyyMMddHH";
    public static final String DATE_FORMAT_YYYYMMDD_HHMM = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_HHMMSS_SSS = "HHmmssSSS";
    public static final String DATE_FORMAT_YYMMDDHH = "yyMMddHH";
    public static final String DATE_FORMAT_YYMMDD = "yyMMdd";
    public static final String DATE_FORMAT_YYYYMMDD_HHMMSS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final int FMT_DATE_YYYY = 0;
    public static final int FMT_DATE_YYYYMMDD = 1;
    public static final int FMT_DATE_YYYYMMDD_HHMMSS = 2;
    public static final int FMT_DATE_HHMMSS = 3;
    public static final int FMT_DATE_HHMM = 4;
    public static final int FMT_DATE_SPECIAL = 5;
    /**
     * 日期转换格式：MM-dd
     */
    public static final int FMT_DATE_MMDD = 6;
    public static final int FMT_DATE_YYYYMMDDHHMM = 7;
    public static final int FMT_DATE_MMDD_HHMM = 8;
    public static final int FMT_DATE_MMMDDD = 9;
    public static final int FMT_DATE_YYYYMMDDHHMM_NEW = 10;
    public static final int FMT_DATE_YYYY年MM月DD日 = 11;
    public static final int FMT_DATE_YYYYMMDDHHMMSS = 12;
    public static final int FMT_DATE_YYMMDD = 13;
    public static final int FMT_DATE_YYMMDDHH = 14;
    public static final int FMT_DATE_MMDD_HHMM_CH = 15;
    public static final int FMT_DATE_MMdd = 16;
    public static final int FMT_DATE_YYYY年MM月DD日HH时SS分 = 17;
    public static final int FMT_DATE_MMDD_HHMMSS = 18;

    public static final int FMT_DATE_YYYYMMDD_DOT = 19; //add by luming 2013.09.04
    public static final int FMT_DATE_MMDD_HH_CH = 20;
    public static final int FMT_DATE_DDMMYYYY = 21;
    public static final int FMT_DATE_DDMM_HHMMSS = 22;
    public static final int FMT_DATE_DDMMYYYY_HHMMSS = 23;
    public static final int FMT_DATE_DDMMYYYY_HHMMSSSSS = 24;
    public static final int FMT_DATE_DDMM_HHMMSSSSS = 25;
    public static final int FMT_DATE_YYYYMMDDHHMMSSSSS = 26;


    /**
     * 静态常量值 用于获取 某一个日期的 年 月 日 时 分 秒 标识
     **/
    public static final int GET_TIME_OF_YEAR = 100;// 获得 日期的年份
    public static final int GET_TIME_OF_MONTH = 200;// 获得 日期的月份
    public static final int GET_TIME_OF_DAY = 300;// 获取 日期的天
    public static final int GET_TIME_IF_HOUR = 400;// 获取日期的小时
    public static final int GET_TIME_OF_MINUTE = 500;
    public static final int GET_TIME_OF_SECOND = 600;


    public static String[] formatTab;

    static {
        formatTab = new String[24];
        formatTab[FMT_DATE_YYYY] = "yyyy";
        formatTab[FMT_DATE_YYYYMMDD] = "yyyy-MM-dd";
        formatTab[FMT_DATE_YYYYMMDD_HHMMSS] = "yyyy-MM-dd HH:mm:ss";
        formatTab[FMT_DATE_HHMMSS] = "HH:mm:ss";
        formatTab[FMT_DATE_HHMM] = "HH:mm";
        formatTab[FMT_DATE_SPECIAL] = "yyyyMMdd";
        formatTab[FMT_DATE_MMDD] = "MM-dd";
        formatTab[FMT_DATE_YYYYMMDDHHMM] = "yyyy-MM-dd HH:mm";
        formatTab[FMT_DATE_MMDD_HHMM] = "MM-dd HH:mm";
        formatTab[FMT_DATE_MMMDDD] = "MM月dd日";
        formatTab[FMT_DATE_YYYYMMDDHHMM_NEW] = "yyyyMMddHHmm";
        formatTab[FMT_DATE_YYYY年MM月DD日] = "yyyy年MM月dd日";
        formatTab[FMT_DATE_YYYYMMDDHHMMSS] = "yyyyMMddHHmmss";
        formatTab[FMT_DATE_YYMMDD] = "yyMMdd";
        formatTab[FMT_DATE_YYMMDDHH] = "yyyyMMddHH";
        formatTab[FMT_DATE_MMDD_HHMM_CH] = "MM月dd日HH时mm分";
        formatTab[FMT_DATE_MMDD_HH_CH] = "MM月dd日HH点";
        formatTab[FMT_DATE_MMdd] = "MMdd";
        formatTab[FMT_DATE_DDMMYYYY] = "dd/MM/yyyy";
        formatTab[FMT_DATE_DDMM_HHMMSS] = "dd/MM HH:mm:ss";
        formatTab[FMT_DATE_DDMMYYYY_HHMMSS] = "dd/MM/yyyy HH:mm:ss";
    }

    /**
     * 获得一个date 类型的 某个 特殊的 内容
     * <p>
     * 比如 返回 时间的 年 、月、日、时、分、秒
     *
     * @param date
     * @param flag
     * @return
     */
    public static String getPartTime(Date date, int flag) {
        if (date == null) {
            return "";
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int result = 0;
        switch (flag) {
            case GET_TIME_OF_YEAR:
                result = cal.get(Calendar.YEAR);
                break;

            case GET_TIME_OF_MONTH:
                result = cal.get(Calendar.MONTH) + 1;
                break;

            case GET_TIME_OF_DAY:
                result = cal.get(Calendar.DAY_OF_MONTH);
                break;

            case GET_TIME_IF_HOUR:
                result = cal.get(Calendar.HOUR_OF_DAY);
                break;

            case GET_TIME_OF_MINUTE:
                result = cal.get(Calendar.MINUTE);
                break;

            case GET_TIME_OF_SECOND:
                result = cal.get(Calendar.SECOND);
                break;

            default:// 注意默认返回一个时间的年份
                result = cal.get(Calendar.YEAR);
                break;

        }
        return String.valueOf(result);
    }

    public static String formatNowTime(int nFmt) {
        Calendar cal = Calendar.getInstance();

        return formatDate(cal.getTime(), nFmt);
    }

    public static String formatDate(Date date, int nFmt) {
        SimpleDateFormat fmtDate = new SimpleDateFormat();
        switch (nFmt) {
            case FMT_DATE_YYYY:
                fmtDate.applyLocalizedPattern("yyyy");
                break;
            case FMT_DATE_YYYYMMDD:
                fmtDate.applyPattern("yyyy-MM-dd");
                break;
            case FMT_DATE_YYYYMMDD_HHMMSS:
                fmtDate.applyPattern("yyyy-MM-dd HH:mm:ss");
                break;
            case FMT_DATE_HHMM:
                fmtDate.applyPattern("HH:mm");
                break;
            case FMT_DATE_HHMMSS:
                fmtDate.applyPattern("HH:mm:ss");
                break;
            case FMT_DATE_SPECIAL:
                fmtDate.applyPattern("yyyyMMdd");
                break;
            case FMT_DATE_MMDD:
                fmtDate.applyPattern("MM-dd");
                break;
            case FMT_DATE_MMdd:
                fmtDate.applyPattern("MMdd");
                break;
            case FMT_DATE_YYYYMMDDHHMM:
                fmtDate.applyPattern("yyyy-MM-dd HH:mm");
                break;
            case FMT_DATE_MMDD_HHMM:
                fmtDate.applyPattern("MM-dd HH:mm");
                break;
            case FMT_DATE_MMMDDD:
                fmtDate.applyPattern("MM月dd日");
                break;
            case DateUtils.FMT_DATE_YYYYMMDDHHMM_NEW:
                fmtDate.applyPattern("yyyyMMddHHmm");
                break;
            case DateUtils.FMT_DATE_YYYY年MM月DD日:
                fmtDate.applyPattern("yyyy年MM月dd日");
                break;
            case DateUtils.FMT_DATE_YYYYMMDDHHMMSS:
                fmtDate.applyPattern("yyyyMMddHHmmss");
                break;
            case FMT_DATE_YYMMDD:
                fmtDate.applyPattern("yyMMdd");
                break;
            case FMT_DATE_YYMMDDHH:
                fmtDate.applyPattern("yyyyMMddHH");
                break;
            case FMT_DATE_MMDD_HHMM_CH:
                fmtDate.applyPattern("MM月dd日HH时mm分");
                break;
            case FMT_DATE_MMDD_HH_CH:
                fmtDate.applyPattern("MM月dd日HH点");
                break;
            case DateUtils.FMT_DATE_YYYY年MM月DD日HH时SS分:
                fmtDate.applyPattern("yyyy年MM月dd日HH时mm分");
                break;
            case DateUtils.FMT_DATE_MMDD_HHMMSS:
                fmtDate.applyPattern("MM-dd HH:mm:ss");
                break;
            case DateUtils.FMT_DATE_YYYYMMDD_DOT:
                fmtDate.applyPattern("yyyy.MM.dd");
                break;
            case DateUtils.FMT_DATE_DDMMYYYY:
                fmtDate.applyPattern("dd/MM/yyyy");
                break;
            case DateUtils.FMT_DATE_DDMM_HHMMSS:
                fmtDate.applyPattern("dd/MM HH:mm:ss");
                break;
            case DateUtils.FMT_DATE_DDMMYYYY_HHMMSS:
                fmtDate.applyPattern("dd/MM/yyyy HH:mm:ss");
                break;
            case DateUtils.FMT_DATE_DDMMYYYY_HHMMSSSSS:
                fmtDate.applyPattern("dd/MM/yyyy HH:mm:ss.SSS");
                break;
            case DateUtils.FMT_DATE_DDMM_HHMMSSSSS:
                fmtDate.applyPattern("dd/MM HH:mm:ss.SSS");
                break;
            case DateUtils.FMT_DATE_YYYYMMDDHHMMSSSSS:
                fmtDate.applyPattern("yyyyMMddHHmmssSSS");
                break;
        }
        return fmtDate.format(date);
    }

    public static String[] parseStringToDate (String time) {
        Timestamp ts = new Timestamp(Long.parseLong(time));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = df.format(ts);
        String date = simpleDateFormat.format(ts);
        String[] array = new String[2];
        array[0] = str;
        array[1] = date;
        return array;
    }

    public static String[] parseStringToAfricaDate (String time, int fmtYear, int fmt) {
        Timestamp ts = new Timestamp(Long.parseLong(time));
        String str = formatDate(ts,fmtYear);
        String date = formatDate(ts,fmt);
        String[] array = new String[2];
        array[0] = str;
        array[1] = date;
        return array;
    }

    public static String[] parseDateString (Timestamp time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = df.format(time);
        String date = simpleDateFormat.format(time);
        String[] array = new String[2];
        array[0] = str;
        array[1] = date;
        return array;
    }

    public static String[] parseDateStringAfrica (Timestamp time, int fmtYear, int fmt) {
        String str = formatDate(time,fmtYear);
        String date = formatDate(time,fmt);
        String[] array = new String[2];
        array[0] = str;
        array[1] = date;
        return array;
    }

    public static Timestamp parseUtilDate(String strDate, int nFmtDate) {
        if (strDate == null || strDate.trim().length() == 0)
            return null;
        SimpleDateFormat fmtDate = null;
        switch (nFmtDate) {
            case DateUtils.FMT_DATE_SPECIAL:
                fmtDate = new SimpleDateFormat("yyyyMMdd");
                break;
            case DateUtils.FMT_DATE_YYYYMMDD:
                fmtDate = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case DateUtils.FMT_DATE_YYYYMMDD_HHMMSS:
                fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            case DateUtils.FMT_DATE_HHMM:
                fmtDate = new SimpleDateFormat("HH:mm");
                break;
            case DateUtils.FMT_DATE_HHMMSS:
                fmtDate = new SimpleDateFormat("HH:mm:ss");
                break;
            case DateUtils.FMT_DATE_YYYYMMDDHHMMSS:
                fmtDate = new SimpleDateFormat("yyyyMMddHHmmss");
                break;
            case DateUtils.FMT_DATE_YYYYMMDDHHMM:
                fmtDate = new SimpleDateFormat("yyyyMMddHHmm");
                break;
        }
        try {
            return new Timestamp(fmtDate.parse(strDate).getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    //获取目标日期是周几(type=true返回汉字，type=false返回数字)
    public static String getTargetWeek(Timestamp target, Boolean type) {
        Map<Integer, String> result = initWeekMap();
        Calendar c = Calendar.getInstance();
        Date d = new Date(target.getTime());
        c.setTime(d);
        Integer week = c.get(Calendar.DAY_OF_WEEK);
        if (type) {
            return result.get(week);
        } else {
            return week.toString();
        }
    }

    public static Timestamp getToday(Timestamp ts) {
        try {
            String dateStr = formatDate(ts, FMT_DATE_YYYYMMDD);
            SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
            return new Timestamp(fmtDate.parse(dateStr).getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将日期按照指定的天数增加或者减少，并转换为需要的日期格式
     *
     * @param sourceFormat 原始日期格式
     * @param date2Get     需要转换成的日期
     * @param days         需要转换的格式
     * @return date2Get 成功：转换后的日期，失败：null
     */

    public static Date getIntervalDateFormat(String date2Get, String sourceFormat, int days) {
        try {
            SimpleDateFormat sorceFmt = new SimpleDateFormat(sourceFormat);
            Date date = new Date(sorceFmt.parse(date2Get).getTime() + days * 86400000L); // 一天的时间24*3600*1000
            return date;
        } catch (ParseException e) {
            // log.warn("invalid date2Get :" + date2Get);
            return null;
        }
    }

    /**
     * 按默认日期格式 格式化日期
     *
     * @param target
     * @return 格式化后的日期字符串，如果传入的日期对象为NULL，返回空字符串
     */
    public static String formatDate(Date target) {
        return formatDate(target, DEFAULT_DATE_FORMAT);
    }

    /**
     * 获得当前时间的n天前或后
     *
     * @param origin
     * @param intervals
     * @return
     */
    public static Date getIntervalDate(Date origin, long intervals) {
        return new Date(origin.getTime() + intervals * 86400000L);
    }

    /**
     * 获得当前时间的n秒前或后
     *
     * @param origin
     * @param seconds
     * @return
     */
    public static Date getIntervalSeconds(Date origin, long seconds) {
        return new Date(origin.getTime() + seconds * 1000L);
    }

    /**
     * 获得指定时间间隔的timestamp
     *
     * @param ts
     * @param minutes
     * @return
     */
    public static Timestamp getIntervalTimestamp(Timestamp ts, int minutes) {
        if (minutes == 0) {
            return ts;
        }
        return new Timestamp(ts.getTime() + minutes * 60 * 1000L);
    }

    /**
     * 获得指定时间间隔的timestamp
     *
     * @param ts
     * @param minutes
     * @return
     */
    public static Timestamp getIntervalMinutes(Timestamp ts, int minutes) {
        if (minutes == 0) {
            return ts;
        }
        return new Timestamp(ts.getTime() + minutes * 60 * 1000L);
    }

    /**
     * 获得指定时间间隔的timestamp
     *
     * @param ts
     * @param seconds
     * @return
     */
    public static Timestamp getIntervalTimestampBySeconds(Timestamp ts, long seconds) {
        return new Timestamp(ts.getTime() + seconds * 1000L);
    }

    public static Timestamp getIntervalTimestampByDays(Timestamp ts, long days) {
        return getIntervalTimestampBySeconds(ts, days * 86400L);
    }

    @SuppressWarnings("deprecation")
    public static Date transToQueryDate(Date date) {
        Calendar c = new GregorianCalendar();// 新建日期对象
        c.set(date.getYear() + 1900, date.getMonth(), date.getDate(), 0, 0, 0);
        return c.getTime();
    }

    /**
     * 返回两个时间间隔的分钟数
     *
     * @param from
     * @param to
     * @return
     */
    public static long getDiffMinutes(Timestamp from, Timestamp to) {
        return (to.getTime() - from.getTime()) / (60 * 1000);
    }

    /**
     * 返回两个时间间隔的小时数
     *
     * @param from
     * @param to
     * @return
     */
    public static long getDiffHour(Timestamp from, Timestamp to) {
        return (to.getTime() - from.getTime()) / (3600 * 1000);
    }

    /**
     * 返回两个时间间隔的秒数
     *
     * @param from
     * @param to
     * @return
     */
    public static long getDiffSeconds(Timestamp from, Timestamp to) {
        return (to.getTime() - from.getTime()) / 1000;
    }

    /**
     * 获得两个时间之内的毫秒数
     *
     * @param from
     * @param to
     * @return
     */
    public static long getDiffMsecs(Timestamp from, Timestamp to) {
        return to.getTime() - from.getTime();
    }

    /**
     * 获得两个时间之间的天数
     *
     * @param from
     * @param to
     * @return
     */
    public static int getDiffDays(Timestamp from, Timestamp to) {
        return (int) (getDiffMinutes(from, to) / 1440);
    }



    public static boolean isBetween(Date date, Date start, Date end) {
        boolean isBetween = date.compareTo(start) >= 0 && date.getTime() <= (end.getTime() + 999);
        return isBetween;
    }

    public static boolean isToday(Date start) {
        long beginOfToday = getBeginOfToday().getTime();
        long endOfToday = getEndOfToday().getTime();
        // log.debug("begin of today:" + getBeginOfToday());
        // log.debug("end of today:" + getEndOfToday());
        boolean result = start.getTime() >= beginOfToday && start.getTime() <= endOfToday;
        // log.debug("beginOfToday:{}, endOfToday:{}, start:{}, result:{}", beginOfToday , endOfToday , start.getTime(), result);
        return result;
    }

    public static Timestamp getBeginOfToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = form.format(cal.getTime()) + " 00:00:00";
        Date date = null;
        try {
            date = form.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static Timestamp getBeginOfOneDay(Timestamp times) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = form.format(times.getTime()) + " 00:00:00";
        Date date = null;
        try {
            date = form.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static Timestamp getBeginOfYesterday() {
        return getIntervalTimestamp(getBeginOfToday(), -24 * 60);
    }

    public static Timestamp getBeginOfCurrentMonth() {
        String firstDayOfCurrentMonth = getCurrentMonth() + "01";
        return formatString(firstDayOfCurrentMonth, FMT_DATE_SPECIAL);
    }

    public static Timestamp getBeginOfLastMonth() {
        String firstDayOfCurrentMonth = getLastMonth() + "01";
        return formatString(firstDayOfCurrentMonth, FMT_DATE_SPECIAL);
    }

    public static Timestamp getEndOfToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = form.format(cal.getTime()) + " 23:59:59";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static Timestamp getEndOfDay(String day) {
        String dateStr = day + " 23:59:59";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static boolean isYesterday(Date start) {
        Date today = getBeginOfToday();
        Date yesterday = getIntervalDate(today, -1);
        return start.getTime() >= yesterday.getTime() && start.getTime() < today.getTime();
    }

    /**
     * 按自定义日期格式格式化日期
     *
     * @param target
     * @param format
     * @return 格式化后的日期字符串，如果传入的日期对象为NULL，返回空字符串
     */
    public static String formatDate(Date target, String format) {
        if (target == null) {
            return "";
        }
        return new SimpleDateFormat(format).format(target);
    }

    public static String formatStr2Str(String source, String srcFormat, String destFormat) throws Exception {
        if (source == null) {
            return "";
        }
        SimpleDateFormat df1 = new SimpleDateFormat(srcFormat);
        SimpleDateFormat df2 = new SimpleDateFormat(destFormat);
        return df2.format(df1.parse(source));
    }

    public static String formatTime(Timestamp target) {
        if (target == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(target);
    }

    public static String formatTime(Timestamp target, String format) {
        if (target == null) {
            return "";
        }
        return new SimpleDateFormat(format).format(target);
    }

    public static Timestamp formatString(String target, String format) {

        if (StringUtils.isBlank(target)) {
            return null;
        }
        Date date;
        try {
            date = new SimpleDateFormat(format).parse(target);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    public static Timestamp formatString(String target, int format) {

        if (StringUtils.isBlank(target)) {
            return null;
        }
        Date date;
        try {
            date = new SimpleDateFormat(formatTab[format]).parse(target);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @param firstDate
     * @param nextDate
     * @return
     * @author wangmeng
     * 比较两个时间，if: firstDate before nextDate return true;
     * else: return true;
     */
    public static boolean compareDate(Date firstDate, Date nextDate) {
        if (firstDate.before(nextDate)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将字符串格式化为日期对象
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Timestamp formatToTimestamp(String dateStr, String format) {
        try {
            SimpleDateFormat sorceFmt = new SimpleDateFormat(format);
            return new Timestamp(sorceFmt.parse(dateStr).getTime()); // 一天的时间24*3600*1000
        } catch (ParseException e) {
            // log.warn("invalid date2Get :" + dateStr);
            return null;
        }
    }

    //获取某个时间的前后几个月的时间。
    public static Date getIntervalMonth(Date date, int i) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + i);

        return calendar.getTime();
    }

    public static String getIntervalDateStr(long time, long i, String format) {
        return formatTime(new Timestamp(time + 1000 * 60 * 60 * 24 * i), format);
    }

    /**
     * 将字符串格式化为日期对象
     *
     * @param date
     * @param format
     * @return 如果date为空或格式不标准，返回NULL，否则返回对应的日期对象
     */
    public static Date formatToDate(String date, String format) {
        try {
            if (StringUtils.isBlank(date)) {
                return null;
            }

            SimpleDateFormat sorceFmt = new SimpleDateFormat(format);
            return new Date(sorceFmt.parse(date).getTime());
        } catch (ParseException e) {
            // log.warn("invalid date :" + date);
            return null;
        }
    }

    public static String getCurrentMonth() {
        return getCurrentMonth("yyyyMM");
    }

    public static String getCurrentDay() {
        return getCurrentDay("yyyyMMdd");
    }

    public static String getCurrentMonth(String format) {
        return formatDate(new Date(), format);
    }

    public static String getCurrentDay(String format) {
        return formatDate(new Date(), format);
    }

    public static String getMonth(Timestamp time) {
        return new SimpleDateFormat("yyyyMM").format(time);
    }

    public static String getDate(Timestamp time) {
        return new SimpleDateFormat("yyyyMMdd").format(time);
    }

    public static String getDate(Timestamp time, String format) {
        return new SimpleDateFormat(format).format(time);
    }

    public static String getCurrentDate() {
        return formatTime(getCurrentTimestamp(), DATE_FORMAT_YYYYMMDD_HHMMSS);
    }

    public static String getHour(Timestamp time) {
        return new SimpleDateFormat("HH").format(time);
    }

    public static String getLastMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return new SimpleDateFormat("yyyyMM").format(new Timestamp(c.getTimeInMillis()));
    }

    public static String getNextMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        return new SimpleDateFormat("yyyyMM").format(new Timestamp(c.getTimeInMillis()));
    }

    public static String getNextMonth(Timestamp time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time.getTime());
        c.add(Calendar.MONTH, 1);
        return new SimpleDateFormat("yyyyMM").format(new Timestamp(c.getTimeInMillis()));
    }

    public static Timestamp convertMonthStr2Time(String sourceMonthStr) {
        try {
            SimpleDateFormat fmtDate = new SimpleDateFormat("yyyyMM");
            return new Timestamp(fmtDate.parse(sourceMonthStr).getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getLastMonth(Timestamp time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time.getTime());
        c.add(Calendar.MONTH, -1);
        return new SimpleDateFormat("yyyyMM").format(new Timestamp(c.getTimeInMillis()));
    }

    private static Map<Integer, String> initWeekMap() {
        Map<Integer, String> weekMap = new HashMap<Integer, String>();
        weekMap.put(1, "星期日");
        weekMap.put(2, "星期一");
        weekMap.put(3, "星期二");
        weekMap.put(4, "星期三");
        weekMap.put(5, "星期四");
        weekMap.put(6, "星期五");
        weekMap.put(7, "星期六");

        return weekMap;
    }

    public static String getDisplayStartTime(Timestamp starttime) {//显示格式  02-10 周五09:00
        Map<Integer, String> result = initWeekMap();
        Date d = new Date(starttime.getTime());
        String date = DateUtils.formatDate(d, DateUtils.FMT_DATE_MMdd);
        String time = DateUtils.formatDate(d, DateUtils.FMT_DATE_HHMM);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d);
        String week = result.get((c1.get(Calendar.DAY_OF_WEEK)));
        StringBuffer sb = new StringBuffer();
        sb.append(date.substring(0, 2)).append("-").append(date.subSequence(2, 4)).append(" ").append(week).append(" ")
                .append(time);

        return sb.toString();
    }

    //获取这个周一的日期
    public static String getMonday(Timestamp current) {
        Calendar c = Calendar.getInstance();
        c.setTime(current);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return new SimpleDateFormat(DATE_FORMAT_YYYYMMDD).format(c.getTime());
    }

    public static String getMondayStr(String day){
        Timestamp current =  parseUtilDate(day,FMT_DATE_SPECIAL);
        return getMonday(current);
    }

    //根据开始时间d1计算d2属于第几周，周一到周日算一周，非自然周
    public static int getWeekth(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        int dayOfWeek = c1.get(Calendar.DAY_OF_WEEK);
        Timestamp t1 = new Timestamp(d1.getTime());
        Timestamp t2 = new Timestamp(d2.getTime());
        int diffdays = DateUtils.getDiffDays(t1, t2);
//		System.err.println("dayOfWeek:" + dayOfWeek + ",diffdays：" + diffdays);

        if (diffdays <= (8 - dayOfWeek)) {
            return 1;
        } else {
            int result = (diffdays + dayOfWeek - 7) / 7;
            return result + 2;
        }
    }

    public static String getWeek(Date date) {
        return new SimpleDateFormat("E").format(date);
    }

    //获得当前天是当年的第几周 by luming
    public static int getWeekOfYearOfCurrentDay() {
        return getWeekOfYear(new Date());
    }

    public static int getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        //calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static String convertToDateString(String dateString) {
        if (StringUtils.isBlank(dateString) || dateString.length() != 6)
            return "";
        StringBuffer Buffer = new StringBuffer("20");
        Buffer.append(dateString.subSequence(0, 2));
        Buffer.append("-");
        Buffer.append(dateString.subSequence(2, 4));
        Buffer.append("-");
        Buffer.append(dateString.subSequence(4, 6));
        return Buffer.toString();
    }

    /**
     * 获取精确到分钟的当前时间戳Timestamp
     *
     * @return
     */
    public static Timestamp getMMTimestampOfCurr() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        return new Timestamp(cal.getTimeInMillis());
    }

    //几天几小时几秒
    public static String getRestTimeDesc(long seconds) {
        long restDay = 0;
        long restHour = 0;
        long restMinute = 0;
        long second = 0;
        restDay = (seconds / (24 * 3600));
        restHour = (seconds - restDay * 24 * 3600) / 3600;
        restMinute = (seconds - restDay * 24 * 3600 - restHour * 3600) / 60;
        second = (second - (second / 60)) * 60;
        return restDay + "天" + restHour + "小时" + restMinute + "分" + second + "秒";

    }

    /**
     * 获取今天、明天、后天
     *
     * @param time
     * @return
     */
    public static String getTodayTomorrowAndAfterTomorrow(Timestamp time) {
        if (time == null) {
            return null;
        }
        Timestamp current = getCurrentTimestamp();
        Timestamp tomorrow = getIntervalTimestampByDays(current, 1);
        Timestamp afterTomorrow = getIntervalTimestampByDays(current, 2);
        try {
            if (formatTime(time, "yyyyMMdd").equals(formatTime(current, "yyyyMMdd"))) {
                return "今天";
            } else if (formatTime(time, "yyyyMMdd").equals(formatTime(tomorrow, "yyyyMMdd"))) {
                return "明天";
            } else if (formatTime(time, "yyyyMMdd").equals(formatTime(afterTomorrow, "yyyyMMdd"))) {
                return "后天";
            }
        } catch (Exception e) {
            // log.error("getTodayTomorrowAndAfterTomorrow格式转换错误，time:{}, e:", time, e);
        }
        return null;
    }

    public static String getTodayOrYesterday(Timestamp time) {
        if (time == null) {
            return null;
        }
        Timestamp current = getCurrentTimestamp();
        Timestamp yesterday = getIntervalTimestampByDays(current, -1);
        try {
            if (formatTime(time, "yyyyMMdd").equals(formatTime(current, "yyyyMMdd"))) {
                return "今天";
            } else if (formatTime(time, "yyyyMMdd").equals(formatTime(yesterday, "yyyyMMdd"))) {
                return "昨天";
            }
        } catch (Exception e) {
            // log.error("getTodayOrYesterday格式转换错误，time:{} , e:",time , e);
        }
        return null;
    }

    public static Timestamp getEndOfOneDay(Timestamp times) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = form.format(times.getTime()) + " 23:59:59";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    /**
     * @return ：Timestamp
     * @throws
     * @author: zhuxuli
     * @Description: 获取当前天凌晨，六点等时间的Timestamp
     */
    public static Timestamp getNowDayTimeStamps(int hour, int second, int minute, int millisecond) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.MILLISECOND, millisecond);
        return new Timestamp(cal.getTimeInMillis());
    }

    /**
     * 是否在月末及月初临界区
     * 比如before为15分钟，after为15分钟，判断时间time在不在月末最后beforeMonth分钟到下月初前afterMonth分钟。
     */
    public static boolean ifInCriticalTime(Date time, int beforeMonth, int afterMonth) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        Calendar begin = Calendar.getInstance();
        begin.setTime(time);
        if (currentDay != 1) {
            begin.add(Calendar.MONTH, 1);
            begin.set(Calendar.DAY_OF_MONTH, 1); //取下个月1号
        }
        begin.set(Calendar.HOUR_OF_DAY, 0);
        begin.set(Calendar.MINUTE, 0);
        begin.set(Calendar.SECOND, 0);
        begin.set(Calendar.MILLISECOND, 0);
        Calendar end = (Calendar) begin.clone();
        begin.add(Calendar.MINUTE, -beforeMonth);
        end.add(Calendar.MINUTE, afterMonth);

        if (time.getTime() >= begin.getTimeInMillis() && time.getTime() <= end.getTimeInMillis()) {
            return true;
        }
        return false;
    }

    /**
     * @param start
     * @param end
     * @return boolean
     * @Description 判断时间 是否在指定的范围内
     * @add by xlzhu
     */
    public static boolean ifInterTimeRange(final String start, final String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startTime = sdf.parse(start);//开始时间
            Date endTime = sdf.parse(end); //结束时间
            Date nowTime = new Date();//当前系统时间
            boolean tag = nowTime.after(startTime) && nowTime.before(endTime);
            // log.info("xlzhu tag is " + tag);
            return tag;
        } catch (Exception e) {
            return false;
        }

    }

    public static long getUtcTimestampMs() {
        long localTimeInMillis=System.currentTimeMillis();
        /** long时间转换成Calendar */
        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        long utcTimeInMillis = calendar.getTimeInMillis();
        return utcTimeInMillis;
    }

    public static String getDateFormatYyyymmdd(String channelId) {
        Date currentTime = getDateFormat(channelId);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static Date getDateFormat(String channelId) {
        Date currentTime = new Date();
        if ("ke".equals(channelId)) {
            long diffTime = 3 * 60 * 60 * 1000;
            currentTime = new Date(currentTime.getTime() + diffTime);
        }
        if ("zm".equals(channelId)) {
            long diffTime = 2 * 60 * 60 * 1000;
            currentTime = new Date(currentTime.getTime() + diffTime);
        }
        if ("ug".equals(channelId)) {
            long diffTime = 3 * 60 * 60 * 1000;
            currentTime = new Date(currentTime.getTime() + diffTime);
        }
        if ("tz".equals(channelId)) {
            long diffTime = 3 * 60 * 60 * 1000;
            currentTime = new Date(currentTime.getTime() + diffTime);
        }
        if ("ng".equals(channelId)) {
            long diffTime = 1 * 60 * 60 * 1000;
            currentTime = new Date(currentTime.getTime() + diffTime);
        }
        if ("za".equals(channelId)) {
            long diffTime = 2 * 60 * 60 * 1000;
            currentTime = new Date(currentTime.getTime() + diffTime);
        }
        return currentTime;
    }


    public static String getChannelTime(String channel) {
        String zone = "GMT+0:00";
        switch(channel){
            case "ke":
                zone = "GMT+5:00";//;
                break;
            case "zm":
                zone = "GMT+3:00";//;
                break;
        }
        TimeZone timeZone = TimeZone.getTimeZone(zone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(new Date());
    }

    public static Map<String,Object> getLocaleTime(String channel){

        String zone = "GMT+0:00";
        switch(channel){
            case "ke":
                zone = "GMT+3:00";//;
                break;
            case "zm":
                zone = "GMT+2:00";//;
                break;
            case "ug":
                zone = "GMT+3:00";//;
                break;
            case "tz":
                zone = "GMT+3:00";//;
                break;
            case "ng":
                zone = "GMT+1:00";//;
                break;
            case "za":
                zone = "GMT+2:00";//;
                break;
        }

        Date now = new Date();
        Calendar calTimeZone = Calendar.getInstance(TimeZone.getTimeZone(zone));
        calTimeZone.setTime(now);
        Calendar cal = Calendar.getInstance();
        cal.set(calTimeZone.get(Calendar.YEAR), calTimeZone.get(Calendar.MONTH),
                calTimeZone.get(Calendar.DAY_OF_MONTH), calTimeZone.get(Calendar.HOUR_OF_DAY),
                calTimeZone.get(Calendar.MINUTE), calTimeZone.get(Calendar.SECOND));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(cal.getTime());
        Map<String,Object> dateMap = Maps.newConcurrentMap();
        dateMap.put("date",date);
        dateMap.put("dateNow",new Timestamp(cal.getTime().getTime()));

        dateMap.put("weekOfYear",cal.get(Calendar.WEEK_OF_YEAR));
        dateMap.put("year",String.valueOf(cal.getWeekYear()));


        cal.setFirstDayOfWeek(Calendar.MONDAY);
        dateMap.put("dayofWeek",cal.get(Calendar.DAY_OF_WEEK));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);


        dateMap.put("monday",new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_HHMMSS).format(cal.getTime()));
        dateMap.put("firstday",new SimpleDateFormat(DATE_FORMAT_YYYYMMDD).format(cal.getTime()));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dateMap.put("sunday",new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_HHMMSS).format(cal.getTime()));
        return dateMap;
    }

    /**
     * 获得指定日期的后一天
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay ,int count){
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day+count);

        String dayAfter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
        return dayAfter;
    }

    public static String getSpecifiedDayAfter(String specifiedDay){
        return getSpecifiedDayAfter(specifiedDay,1);
    }


    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    public static Timestamp getBeginOfToday(String channel) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = form.format(cal.getTime()) + " 00:00:00";
        Date date = null;
        try {
            date = form.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static Timestamp getEndOfToday(String channel) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = form.format(cal.getTime()) + " 23:59:59";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static Date nowDate() {
        return new Date();
    }

    /**
     * 时间转换
     * <p>
     * 转换形如: 1d/1m/1y
     * 返回: 当前时间加上 1d/1m/1y 的时间戳
     * </p>
     *
     * @param time
     * @return
     */
    public static Long plus(String time) {

        Pattern p = Pattern.compile("(\\d+)(d|m|y)");
        Matcher m = p.matcher(time);
        if (m.find()) {
            Integer quantity = Integer.valueOf(m.group(1));
            String timeUnit = m.group(2);

            LocalDateTime localDateTime = LocalDateTime.now();

            switch (timeUnit) {
                case "d":
                    localDateTime = localDateTime.plusDays(quantity);
                    break;
                case "m":
                    localDateTime = localDateTime.plusMonths(quantity);
                    break;
                case "y":
                    localDateTime = localDateTime.plusYears(quantity);
                    break;
                default:
                    // log.warn("time convert fail:{}", quantity + timeUnit);
            }

            return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }

        return null;
    }

    public static void main(String[] args) throws Exception {

//        Random rand = new Random();
//        for(int i=0; i<5; i++) {
//            System.out.println(rand.nextInt(10 - 10 + 1) + 10);
//        }

        // int year=c.get(Calendar.YEAR);//获取年份
        // int month=c.get(Calendar.MONTH);//获取月份
        // int day=c.get(Calendar.DATE);//获取日期
        // int minute=c.get(Calendar.MINUTE);//分
        // int hour=c.get(Calendar.HOUR);//小时
        // int second=c.get(Calendar.SECOND);//秒
        /*	Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-02-28 23:45:00");
            System.out.println(ifInCriticalTime(date, 15, 15));*/
//        int count=0;
//        String dateTmp = "20181231";
//        String date = "20190106";
//        for(int i=0;i<7;i++){
//            count++;
//            if(dateTmp.equals(date)){
//                break;
//            }
//            dateTmp = DateUtils.getSpecifiedDayAfter(dateTmp);
//        Integer b = 3;
////        }
//        float a  = (float) b;

//        System.out.println("+23489897".replace("+234",""));
        System.out.println(getLocaleTime("ke"));

    }
}