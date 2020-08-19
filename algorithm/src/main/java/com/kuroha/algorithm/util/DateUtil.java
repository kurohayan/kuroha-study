package com.kuroha.algorithm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 时间工具类
 */
public class DateUtil {

	public static final String default_pattern = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 一天毫秒数
	 */
	public static final long MILLISINDAY = 1000 * 60 * 60 * 24;
	/**
	 * 8小时毫秒数
	 */
	public static final long MILLISIN8H = 1000 * 60 * 60 * 8;
	/**
	 * 16小时毫秒数
	 */
	public static final long MILLISIN16H = 1000 * 60 * 60 * 16;


	/**
	 * 给定日期yyyy/MM/dd添加天数
	 *
	 * @return String
	 * @author SunCheng 2017年6月27日 下午2:50:49
	 */
	public static String addDays2(String date, int days) {
		Date dateStart = stringTODate3(date);

		Calendar cal = Calendar.getInstance();
		cal.setTime(dateStart);
		cal.add(Calendar.DAY_OF_MONTH, days);
		Date dateAfter = cal.getTime();
		return dateToStr6(dateAfter);
	}

	/**
	 * 给定日期yyyy/MM/dd添加天数
	 *
	 * @return String
	 * @author SunCheng 2017年6月27日 下午2:50:49
	 */
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		Date dateAfter = cal.getTime();
		return dateAfter;
	}

	/**
	 * 时间戳加上若干天
	 * @param date
	 * @param days
	 * @return
	 */
	public static Long addDays(Long date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTimeInMillis();
	}

	/**
	 * 给定月份yyyy/MM添加月份
	 *
	 * @return String
	 * @author SunCheng 2017年6月27日 下午2:52:59
	 */
	public static String addMonth(String date, int months) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		Date dateStart = sdf.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateStart);
		cal.add(Calendar.MONTH, months);
		Date dateAfter = cal.getTime();
		return sdf.format(dateAfter);
	}


	/**
	 * 根据时间和格式返回时间字符串
	 *
	 * @return DATE<br>
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}


	/**
	 * 得到毫秒表示的系统当前时间
	 *
	 * @return String
	 * @author huangtao 2014-11-9 下午4:07:22
	 */
	public static String getNowTime() {
		long lSysTime2 = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(lSysTime2);
		return sdf.format(date);
	}

	/**
	 * 得到毫秒表示的系统当前时间 yyyy-MM-dd
	 *
	 * @return String
	 * @author huangtao 2014-11-9 下午4:07:22
	 */
	public static String getNowTime2() {
		long lSysTime2 = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(lSysTime2);
		return sdf.format(date);
	}

	/**
	 * 格式化日期 yyyy-MM-dd HH:MM:SS
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getFormatDate(Date date, String pattern) {
		if (pattern == null) {
			pattern = default_pattern;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 将日期增加num，发生在field字段上，然后转化为指定的日期格式
	 *
	 * @param date
	 * @param
	 * @param field
	 * @param num
	 * @return
	 * @throws ParseException
	 */
	public static Date makeDiffDate(Date date, int field, int num) throws ParseException {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(field, calendar.get(field) + num);
		return calendar.getTime();
	}



	/**
	 * @author: fei5280 @Description: 计算两个时间相隔的天数 @Date: 2016年11月4日
	 * 下午8:04:28 @param smdate @param bdate @return @throws
	 * ParseException @throws
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算两个时间戳相隔的天数
	 *
	 * @return int
	 * @author SunCheng 2017年6月26日 下午7:39:31
	 */
	public static int daysBetweenTime(long aTime, long bTime)
			throws ParseException {
		String aDate = longToString(aTime);
		String bDate = longToString(bTime);

		return daysBetween(aDate, bDate);
	}

	/**
	 * 计算两个时间戳相隔的月份
	 *
	 * @return int
	 * @author SunCheng 2017年6月27日 下午2:14:22
	 */
	public static int monthsBetweenTime(long aTime, long bTime)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date dt1 = new Date(aTime);
		Date dt2 = new Date(bTime);
		String str1 = sdf.format(dt1);
		String str2 = sdf.format(dt2);
		Calendar bef = Calendar.getInstance();
		Calendar aft = Calendar.getInstance();
		bef.setTime(sdf.parse(str1));
		aft.setTime(sdf.parse(str2));
		int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
		int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
		return Math.abs(month + result);
	}


	/**
	 * 两个时间间隔的毫秒数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long timeBetween(Date date1, Date date2) {
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long betweenDays = time2 - time1;
		if (betweenDays < 0) {
			betweenDays = -betweenDays;
		}
		return betweenDays;
	}


	/**
	 * @param str
	 *            格式"2012-11-12 08:08:08"
	 * @return
	 * @Time Nov 19, 2012 4:14:27 PM
	 * @return Date
	 * @author YangJingBo
	 * @Description 将字符型的时间转换成Date型
	 */
	public static Date stringTODate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * yyyy/MM/dd转Date
	 *
	 * @return Date
	 * @author SunCheng 2017年6月26日 下午8:05:11
	 */
	public static Date stringTODate3(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @param d
	 * @return
	 * @Time Nov 19, 2012 4:16:20 PM
	 * @return String
	 * @author YangJingBo
	 * @Description 将Date型转换成"2012-11-12 08:08:08"格式的Date型
	 */
	public static String dateToStr(Date d) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(d);
	}

	/**
	 * 计算两个时间的时间差 以日期为单位，可以返回负数
	 *
	 * @param time1
	 * @param time2
	 * @return long
	 * @author huangtao 2015-3-2 上午11:40:36
	 */
	public static long getTimeDifference2(Date time1, Date time2) {
		String t1 = DateUtil.dateToStr(time1).substring(0, 10) + " 00:00:00";
		String t2 = DateUtil.dateToStr(time2).substring(0, 10) + " 00:00:00";

		time1 = DateUtil.stringTODate(t1);
		time2 = DateUtil.stringTODate(t2);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time1);
		long timeCalender1 = calendar.getTimeInMillis();
		calendar.setTime(time2);
		long timeCalender2 = calendar.getTimeInMillis();
		long between_days = (timeCalender2 - timeCalender1) / (1000 * 3600 * 24);
		return between_days;
	}

	/**
	 * Date转yyyy/MM/dd
	 *
	 * @return String
	 * @author SunCheng 2017年6月26日 下午8:06:21
	 */
	public static String dateToStr6(Date d) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		return format.format(d);
	}

	/**
	 * long转string, 返回yyyy-MM-dd
	 *
	 * @param
	 * @return
	 */
	public static String longToString(Long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date(date);
		String sDateTime = sdf.format(dt);
		return sDateTime;
	}

	/**
	 * long转string, 返回yyyy年MM月dd日
	 *
	 * @param
	 * @return
	 */
	public static String longToString2(Long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Date dt = new Date(date);
		String sDateTime = sdf.format(dt);
		return sDateTime;
	}

	/**
	 * 计算两个时间之间的跨度
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long compareTime(String startTime,String endTime) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return getTimeDifference2(simpleDateFormat.parse(startTime),simpleDateFormat.parse(endTime));
	}

	/**
	 * 获取当天是当月的第几天
	 * @return
	 */
	public static String getNowDayInMonth() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		int day = c.get(Calendar.DAY_OF_MONTH);
		return String.valueOf(day);
	}

	/**
	 *
	 * @return 当前yyyyMMdd格式时间 20171110
	 */
	public static String getNowTimeWithYMD() {
		long lSysTime2 = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date(lSysTime2);
		String formatCreateTime = sdf.format(date);
		return formatCreateTime;
	}

	/**
	 * long转string, 返回yyyy/MM/dd
	 *
	 * @return String
	 * @author SunCheng 2017年6月26日 下午7:54:21
	 */
	public static String longToString4(Long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date dt = new Date(date);
		String sDateTime = sdf.format(dt);
		return sDateTime;
	}

	/**
	 * long转string, 返回yyyy/MM/dd HH:mm:ss
	 *
	 * @param
	 * @return
	 */
	public static String longToString5(Long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(new Date(date));
	}


	/**
	 * yyyy-MM-dd转化为long
	 *
	 * @param
	 * @return
	 * @throws ParseException
	 */
	public static long stringToLong4(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dt;
			dt = sdf.parse(date);
			return dt.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取北京时间昨天和今天交接点的时间戳（注： 时间戳为0时，表示北京时间1970年1月1日8点整）
	 *
	 * @return long
	 */
	public static long getYesterdayLastTime() {
		long cur = System.currentTimeMillis();
		// 计算时间戳经过天数的毫秒数
		long day = cur / MILLISINDAY * MILLISINDAY;
		// 计算时间戳今天经过的毫秒数
		long remainder = cur % MILLISINDAY;
		/**
		 * 如果时间戳今天经过了16H 则北京时间超过了24点，需要加上16H表示北京时间0点整 否则减8小时表示0点整
		 */
		return remainder > MILLISIN16H ? day + MILLISIN16H : day - MILLISIN8H;

	}

	/**
	 * 计算两个时间范围 返回默认格式1天
	 *
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static String getTimeScopeDay(Date beginTime, Date endTime) {
		String scope = "";
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		int compareTo = (int) ((end.getTimeInMillis() - begin.getTimeInMillis()) / 1000L);

		if (compareTo < 0) {
			compareTo *= -1;
		}
		if (compareTo == 0) {
			return scope;
		}
		int totalSS = compareTo;
		int day = totalSS / (24 * 60 * 60);
		if (day > 0) {
			scope += day + "";
		}
		return scope;
	}

	public static String getNowTime3() {
		long lSysTime2 = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = new Date(lSysTime2);
		return sdf.format(date);
	}

	public static Map<String,String> getNowTimeWeChat() {
		Map<String,String> map = new HashMap<>(3);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		map.put("start",sdf.format(new Date()));
		calendar.add(Calendar.MINUTE,(int)Const.WECHAT_OUT_TIME);
		map.put("end",sdf.format(calendar.getTime()));
		return map;
	}
	/**
	 * 获取当前时间前n天的毫秒值
	 *
	 * @return long
	 * @author SunCheng 2017年6月26日 上午10:55:39
	 */
	public static long getBeforeDaysLongTime(int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, -days);
		Date d = c.getTime();
		long time = d.getTime();
		return time;
	}

	public static Long getAfterDayTime(){
		Long now = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH,1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		Long afterDay = calendar.getTimeInMillis();
		return (afterDay-now)/1000;
	}

	public static String getAfterDay2(Integer day){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH,-day);
		return formatDate(calendar.getTime(), "yyyy-MM-dd");
	}

	public static String getAfterDay(Integer day){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH,-day);
		return formatDate(calendar.getTime(), "yyyyMMdd");
	}

	public static String getNowTimeWithYMDHMSMI() {
		long lSysTime2 = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date(lSysTime2);
		String formatCreateTime = sdf.format(date);
		return formatCreateTime;
	}

	/**
	 * 获取上个月第一天
	 * @return
	 */
	public static Date getLastMonthFirstDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 获取上个月最后一天
	 * @return
	 */
	public static Date getLastMonthLastDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 获取参数当月的第一天零点
	 * @return
	 */
	public static Date getFirstSecondThatMonth(Long time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(time));
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar.getTime();
	}

	/**
	 * 获取参数当月的最后一天最后一秒
	 * @return
	 */
	public static Date getLastSecondThatMonth(Long time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(time));
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.SECOND,-1);
		return calendar.getTime();
	}

	/**
	 * date类型转化为20180305类型
	 * @return
	 */
	public static String DateToyyyyMMdd(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	/**
	 * 获取当前时间到本月最后一秒的间隔
	 */
	public static long getIntervalNowAndLastSecondThisMonth(){
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.MONTH,1);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		calendar.add(Calendar.SECOND,-1);
		return timeBetween(now,calendar.getTime());
	}
	/**
	 * 获取当前时间到本日最后一秒的间隔
	 */
	public static long getTomorrowTime(){
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH,1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		calendar.set(Calendar.SECOND,0);
		return timeBetween(now,calendar.getTime());
	}

	/**
	 * 获取当前时间前n天的Date
	 */
	public static Date getBeforeDaysLongTime(Date date,int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -days);
		Date d = c.getTime();
		return d;
	}

	/**
	 * 获取当前时间与目标时间的差值的绝对值，毫秒
	 */
	public static Long getInterval(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		Calendar n = Calendar.getInstance();
		Long interval = n.getTimeInMillis()-c.getTimeInMillis();
		return interval > 0? interval:interval*-1;
	}

	public static Date getAfterDaysFirstTime(Date date,int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		calendar.add(Calendar.DAY_OF_MONTH,days);
		return calendar.getTime();
	}
	public static long getAfterDaysTime(Date date,int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH,days);
		return calendar.getTimeInMillis();
	}

	public static String getAfterMinuteTime(int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE,i);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		return format.format(calendar.getTime());
	}

	/**
	 * 获得指定时间
	 * @param day
	 * @param df
	 * @return
	 */
	public static String getTime(int day,String df) {
		SimpleDateFormat sdf = new SimpleDateFormat(df);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取离指定增加时间的时间
	 *
	 * @return
	 */
	public static Date getAddTimeDate(int value, TimeUnit timeUnit) {
		Calendar calendar = Calendar.getInstance();
		if (timeUnit == TimeUnit.DAYS) {
			calendar.add(Calendar.DAY_OF_MONTH, value);
		}
		if (timeUnit == TimeUnit.HOURS) {
			calendar.add(Calendar.HOUR_OF_DAY, value);
		}
		if (timeUnit == TimeUnit.MINUTES) {
			calendar.add(Calendar.MINUTE, value);
		}
		if (timeUnit == TimeUnit.SECONDS) {
			calendar.add(Calendar.SECOND, value);
		}
		return calendar.getTime();
	}

	/**
	 * 时间添加指定毫秒数
	 * @param date
	 * @param remainTime
	 * @return
	 */
	public static Date addMillisecond(Date date, Long remainTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MILLISECOND,remainTime.intValue());
		return calendar.getTime();
	}
}
