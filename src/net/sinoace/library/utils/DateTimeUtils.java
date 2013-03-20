package net.sinoace.library.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <h2>日期时间工具类，提供一些有关日期时间的便捷方法</h2>
 * @author panpf
 */

public class DateTimeUtils {
	/* **************************************获取DateFormat********************************* */
	/**
	 * 根据给定的样式以及给定的环境，获取一个日期格式化器
	 * @param dateStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
	 * @param locale 给定的环境
	 * @return 日期格式化器
	 */
	public static DateFormat getDateFormat(int dateStyle, Locale locale){
		return DateFormat.getDateInstance(dateStyle, locale);
	}
	
	/**
	 * 根据给定的样式以及默认的环境，获取一个日期格式化器
	 * @param dateStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
	 * @return 日期格式化器
	 */
	public static DateFormat getDateFormat(int dateStyle){
		return DateFormat.getDateInstance(dateStyle, Locale.getDefault());
	}
	
	/**
	 * 根据默认的样式以及给定的环境，获取一个日期格式化器
	 * @param locale 给定的环境
	 * @return 日期格式化器
	 */
	public static DateFormat getDateFormat(Locale locale){
		return DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
	}
	
	/**
	 * 根据默认的样式以及默认的环境，获取一个日期格式化器
	 * @return 日期格式化器
	 */
	public static DateFormat getDateFormat(){
		return DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());
	}
	
	/**
	 * 根据默认的自定义格式，获取一个日期格式化器
	 * @return 日期格式化器
	 */
	public static DateFormat getDateFormatByDefultCustomFormat(){
		return getDateTimeFormatByCustomFormat("yyyy:MM:dd");
	}
	
	
	/* **************************************获取TimeFormat********************************* */
	/**
	 * 根据给定的样式以及给定的环境，获取一个时间格式化器
	 * @param timeStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
	 * @param locale 给定的环境
	 * @return 时间格式化器
	 */
	public static DateFormat getTimeFormat(int timeStyle, Locale locale){
		return DateFormat.getTimeInstance(timeStyle, locale);
	}
	
	/**
	 * 根据给定的样式以及默认的环境，获取一个时间格式化器
	 * @param timeStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
	 * @return 时间格式化器
	 */
	public static DateFormat getTimeFormat(int timeStyle){
		return DateFormat.getTimeInstance(timeStyle, Locale.getDefault());
	}
	
	/**
	 * 根据默认的样式以及给定的环境，获取一个时间格式化器
	 * @param locale 给定的环境
	 * @return 时间格式化器
	 */
	public static DateFormat getTimeFormat(Locale locale){
		return DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
	}
	
	/**
	 * 根据默认的样式以及默认的环境，获取一个时间格式化器
	 * @return 时间格式化器
	 */
	public static DateFormat getTimeFormat(){
		return DateFormat.getTimeInstance(DateFormat.DEFAULT, Locale.getDefault());
	}
	
	/**
	 * 根据默认的自定义格式，获取一个时间格式化器
	 * @return 时间格式化器
	 */
	public static DateFormat getTimeFormatByDefultCustomFormat(){
		return getDateTimeFormatByCustomFormat("hh:mm:ss");
	}
	
	/**
	 * 根据默认的24小时制的自定义格式，获取一个时间格式化器
	 * @return 时间格式化器
	 */
	public static DateFormat getTimeFormatByDefultCustomFormatAnd24Hour(){
		return getDateTimeFormatByCustomFormat("HH:mm:ss");
	}
	
	
	/* **************************************获取DateTimeFormat********************************* */
	/**
	 * 根据给定的日期样式、时间样式以及环境，获取一个日期时间格式化器
	 * @param dateStyle 给定的日期样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
	 * @param timeStyle 给定的时间样式
	 * @param locale 给定的环境
	 * @return 日期时间格式化器
	 */
	public static DateFormat getDateTimeFormat(int dateStyle, int timeStyle, Locale locale){
		return DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
	}
	
	/**
	 * 根据给定的日期样式、时间样式以及默认的环境，获取一个日期时间格式化器
	 * @param dateStyle 给定的日期样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
	 * @param timeStyle 给定的时间样式
	 * @return 日期时间格式化器
	 */
	public static DateFormat getDateTimeFormat(int dateStyle, int timeStyle){
		return DateFormat.getDateTimeInstance(dateStyle, timeStyle, Locale.getDefault());
	}
	
	/**
	 * 根据默认的日期样式、时间样式以及给定的环境，获取一个日期时间格式化器
	 * @param locale 给定的环境
	 * @return 日期时间格式化器
	 */
	public static DateFormat getDateTimeFormat(Locale locale){
		return DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale);
	}
	
	/**
	 * 根据默认的日期样式、时间样式以及默认的环境，获取一个日期时间格式化器
	 * @return 日期时间格式化器
	 */
	public static DateFormat getDateTimeFormat(){
		return DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.getDefault());
	}
	
	/**
	 * 根据给定的自定义格式，获取一个日期时间格式化器
	 * @param customFormat 给定的自定义格式，例如："yyyy-MM-dd hh:mm:ss"
	 * @return 日期时间格式化器
	 */
	public static DateFormat getDateTimeFormatByCustomFormat(String customFormat){
		return new SimpleDateFormat(customFormat);
	}
	
	/**
	 * 根据默认的自定义格式，获取一个日期时间格式化器
	 * @return 日期时间格式化器
	 */
	public static DateFormat getDateTimeFormatByDefultCustomFormat(){
		return getDateTimeFormatByCustomFormat("yyyy:MM:dd hh:mm:ss");
	}
	
	/**
	 * 根据默认的自定义格式，获取一个24小时制的日期时间格式化器
	 * @return 日期时间格式化器
	 */
	public static DateFormat getDateTimeFormatByDefultCustomFormatAnd24Hour(){
		return getDateTimeFormatByCustomFormat("yyyy:MM:dd HH:mm:ss");
	}
	
	/* **************************************获取其它具体的Format********************************* */
	/**
	 * 获取年份格式化器
	 * @return 年份格式化器
	 */
	public static DateFormat getYearFormat(){
		return getDateTimeFormatByCustomFormat("yyyy");
	}
	
	/**
	 * 获取月份格式化器
	 * @return 月份格式化器
	 */
	public static DateFormat getMonthFormat(){
		return getDateTimeFormatByCustomFormat("MM");
	}
	
	/**
	 * 获取日份格式化器
	 * @return 日份格式化器
	 */
	public static DateFormat getDayFormat(){
		return getDateTimeFormatByCustomFormat("dd");
	}
	
	public static DateFormat getWeekFormat(){
		return getDateTimeFormatByCustomFormat("E");
	}
	
	/**
	 * 获取小时格式化器
	 * @return 小时格式化器
	 */
	public static DateFormat getHourFormat(){
		return getDateTimeFormatByCustomFormat("hh");
	}
	
	/**
	 * 获取24小时制的小时格式化器
	 * @return 24小时格式化器
	 */
	public static DateFormat getHourFormatBy24Hour(){
		return getDateTimeFormatByCustomFormat("HH");
	}
	
	/**
	 * 获取分钟格式化器
	 * @return 分钟格式化器
	 */
	public static DateFormat getMinuteFormat(){
		return getDateTimeFormatByCustomFormat("mm");
	}
	
	/**
	 * 获取秒格式化器
	 * @return 秒格式化器
	 */
	public static DateFormat getSecondFormat(){
		return getDateTimeFormatByCustomFormat("ss");
	}
	
	
	/* **************************************获取日期时间********************************* */
	/**
	 * 根据给定的Date对象以及给定的格式化器获取日期时间
	 * @param date 给定的Date对象
	 * @param format 给定的格式化器
	 * @return 日期时间
	 */
	public static String getDateTimeByDateAndFormat(Date date, DateFormat format){
		return format.format(date);
	}
	
	/**
	 * 根据给定的时间毫秒值以及给定的格式化器获取日期时间
	 * @param timeMillis 给定的时间毫秒值，请注意时间毫秒与普通毫秒的区别
	 * @param format 给定的格式化器
	 * @return 日期时间
	 */
	public static String getDateTimeByTimeMillisAndFormat(long timeMillis, DateFormat format){
		return getDateTimeByDateAndFormat(new Date(timeMillis), format);
	}
	
	/**
	 * 根据给定的Date对象以及给定的自定义格式获取日期时间 
	 * @param date 给定的Date对象
	 * @param customFormat 给定的自定义格式
	 * @return 日期时间 
	 */
	public static String getDateTimeByDateAndCustomFormat(Date date, String customFormat){
		return getDateTimeByDateAndFormat(date, getDateTimeFormatByCustomFormat(customFormat));
	}
	
	/**
	 * 根据给定的时间毫秒值以及给定的自定义格式获取日期时间
	 * @param timeMillis 给定的时间毫秒值，请注意时间毫秒与普通毫秒的区别
	 * @param customFormat 给定的自定义格式
	 * @return 日期时间
	 */
	public static String getDateTimeByTimeMillisAndCustomFormat(long timeMillis, String customFormat){
		return getDateTimeByTimeMillisAndFormat(timeMillis, getDateTimeFormatByCustomFormat(customFormat));
	}
	
	/**
	 * 根据给定的格式化器，获取当前的日期时间
	 * @param fromat 给定的格式化器
	 * @return 当前的日期时间
	 */
	public static String getCurrentDateTimeByFormat(DateFormat fromat){
		return getDateTimeByTimeMillisAndFormat(System.currentTimeMillis(), fromat);
	}
	
	/**
	 * 根据给定的自定义的格式，获取当前的日期时间
	 * @param customFormat 给定的自定义的格式，例如："yyyy-MM-dd hh:mm:ss"
	 * @return 当前的日期时间
	 */
	public static String getCurrentDateTimeByCustomFormat(String customFormat){
		return getCurrentDateTimeByFormat(getDateTimeFormatByCustomFormat(customFormat));
	}
	
	/**
	 * 获取默认的自定义格式，获取当前的日期时间，格式为："yyyy-MM-dd hh:mm:ss"
	 * @return 当前的日期时间
	 */
	public static String getCurrentDateTimeByDefultCustomFormat(){
		return getCurrentDateTimeByCustomFormat("yyyy-MM-dd hh:mm:ss");
	}
	
	/**
	 * 获取默认的自定义格式，获取24小时制的当前的日期时间，格式为："yyyy-MM-dd hh:mm:ss"
	 * @return 当前的日期时间
	 */
	public static String getCurrentDateTimeByDefultCustomFormatAnd24Hour(){
		return getCurrentDateTimeByCustomFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 根据给定的日期样式、时间样式以及环境，获取当前的日期时间
	 * @param dateStyle 给定的日期样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
	 * @param timeStyle 给定的时间样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
	 * @param locale 给定的环境
	 * @return 当前的日期时间的字符串表示形式
	 */
	public static String getCurrentDateTime(int dateStyle, int timeStyle, Locale locale){
		return getCurrentDateTimeByFormat(getDateTimeFormat(dateStyle, timeStyle, locale));
	}
	
	/**
	 * 根据给定的日期样式、时间样式以及默认的环境，获取当前的日期时间
	 * @param dateStyle 给定的日期样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
	 * @param timeStyle 给定的时间样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
	 * @return 当前的日期时间
	 */
	public static String getCurrentDateTime(int dateStyle, int timeStyle){
		return getCurrentDateTimeByFormat(getDateTimeFormat(dateStyle, timeStyle));
	}
	
	/**
	 * 根据默认的日期样式、时间样式以及给定的环境，获取当前的日期时间
	 * @param locale 给定的环境
	 * @return 当前的日期时间的字符串表示形式
	 */
	public static String getCurrentDateTime(Locale locale){
		return getCurrentDateTimeByFormat(getDateTimeFormat(locale));
	}
	
	/**
	 * 根据默认的日期样式、默认的时间样式以及当前默认的环境，获取当前的日期时间
	 * @return 当前的日期时间
	 */
	public static String getCurrentDateTime(){
		return getCurrentDateTimeByFormat(getDateTimeFormat());
	}
	
	/* **************************************获取日期********************************* */
	/**
	 * 根据给定的样式以及给定的环境，获取当前的日期
	 * @param dateStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
	 * @param locale 给定的环境
	 * @return 当前的日期
	 */
	public static String getCurrentDate(int dateStyle, Locale locale){
		return getCurrentDateTimeByFormat(getDateFormat(dateStyle, locale));
	}
	
	/**
	 * 根据给定的样式以及默认的环境，获取当前的日期
	 * @param dateStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
	 * @return 当前的日期
	 */
	public static String getCurrentDate(int dateStyle){
		return getCurrentDateTimeByFormat(getDateFormat(dateStyle));
	}
	
	/**
	 * 根据默认的样式以及给定的环境，获取当前的日期
	 * @param locale 给定的环境
	 * @return 当前的日期
	 */
	public static String getCurrentDate(Locale locale){
		return getCurrentDateTimeByFormat(getDateFormat(locale));
	}
	
	/**
	 * 根据默认的样式以及默认的环境，获取当前的日期
	 * @return 当前的日期
	 */
	public static String getCurrentDate(){
		return getCurrentDateTimeByFormat(getDateFormat());
	}
	
	/**
	 * 根据默认的自定义格式的获取当前的日期，格式为"yyyy-MM-dd"
	 * @return 当前的日期
	 */
	public static String getCurrentDateByDefultCustomFormat(){
		return getCurrentDateTimeByCustomFormat("yyyy-MM-dd");
	}
	
	
	/* **************************************获取时间********************************* */
	/**
	 * 根据给定的样式以及给定的环境，获取当前的时间
	 * @param timeStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
	 * @param locale 给定的环境
	 * @return 当前的时间
	 */
	public static String getCurrentTime(int timeStyle, Locale locale){
		return getCurrentDateTimeByFormat(getTimeFormat(timeStyle, locale));
	}
	
	/**
	 * 根据给定的样式以及默认的环境，获取当前的时间
	 * @param timeStyle 给定的样式，取值范围为DateFormat中的静态字段DEFAULT或SHORT或LONG或MIDIUM或FULL
	 * @return 当前的时间
	 */
	public static String getCurrentTime(int timeStyle){
		return getCurrentDateTimeByFormat(getTimeFormat(timeStyle));
	}
	
	/**
	 * 根据默认的样式以及给定的环境，获取当前的时间
	 * @param locale 给定的环境
	 * @return 当前的时间
	 */
	public static String getCurrentTime(Locale locale){
		return getCurrentDateTimeByFormat(getTimeFormat(locale));
	}
	
	/**
	 * 根据默认的样式以及默认的环境，获取当前的时间
	 * @return 当前的时间
	 */
	public static String getCurrentTime(){
		return getCurrentDateTimeByFormat(getTimeFormat());
	}
	
	/**
	 * 根据默认的自定义格式，获取当前的时间，格式为"hh:mm:ssd"
	 * @return 当前的时间
	 */
	public static String getCurrentTimeByDefultCustomFormat(){
		return getCurrentDateTimeByCustomFormat("hh:mm:ss");
	}
	
	/**
	 * 根据默认的自定义格式，获取24小时制的当前时间，格式为"hh:mm:ssd"
	 * @return 当前的时间
	 */
	public static String getCurrentTimeByDefultCustomFormatAnd24Hour(){
		return getCurrentDateTimeByCustomFormat("HH:mm:ss");
	}
	
	/* **************************************获取年********************************* */
	/**
	 * 获取给定的日期中的年份
	 * @param date 给定的日期
	 * @return 年份
	 */
	public static int getYear(Date date){
		return Integer.valueOf(getYearFormat().format(date));
	}
	
	/**
	 * 获取给定时间的毫秒值中的年份
	 * @param timeMillis 给定时间的毫秒值
	 * @return 年份
	 */
	public static int getYear(long timeMillis){
		return getYear(new Date(timeMillis));
	}
	
	/**
	 * 获取当前的年份
	 * @return 当前的年份
	 */
	public static int getCurrentYear(){
		return getYear(System.currentTimeMillis());
	}
	
	/* **************************************获取月********************************* */
	/**
	 * 获取给定的日期中的月份
	 * @param date 给定的日期
	 * @return 月份
	 */
	public static int getMonth(Date date){
		return Integer.valueOf(getMonthFormat().format(date));
	}
	
	/**
	 * 获取给定时间的毫秒值中的月份
	 * @param timeMillis 给定时间的毫秒值
	 * @return 月份
	 */
	public static int getMonth(long timeMillis){
		return getMonth(new Date(timeMillis));
	}
	
	/**
	 * 获取当前的月份
	 * @return 当前的月份
	 */
	public static int getCurrentMonth(){
		return getMonth(System.currentTimeMillis());
	}
	
	/* **************************************获取日********************************* */
	/**
	 * 获取给定的日期中的日份
	 * @param date 给定的日期
	 * @return 日份
	 */
	public static int getDay(Date date){
		return Integer.valueOf(getDayFormat().format(date));
	}
	
	/**
	 * 获取给定时间的毫秒值中的日份
	 * @param timeMillis 给定时间的毫秒值
	 * @return 日份
	 */
	public static int getDay(long timeMillis){
		return getDay(new Date(timeMillis));
	}
	
	/**
	 * 获取当前的日份
	 * @return 当前的日份
	 */
	public static int getCurrentDay(){
		return getDay(System.currentTimeMillis());
	}
	
	/* **************************************获取小时********************************* */
	/**
	 * 获取给定的日期中的小时
	 * @param date 给定的日期
	 * @return 小时
	 */
	public static int getHour(Date date){
		return Integer.valueOf(getHourFormat().format(date));
	}
	
	/**
	 * 获取给定的时间的毫秒值中的小时
	 * @param timeMIllis 给定的时间的毫秒值
	 * @return 小时
	 */
	public static int getHour(long timeMIllis){
		return getHour(new Date(timeMIllis));
	}
	
	/**
	 * 获取当前的小时
	 * @return 当前的小时
	 */
	public static int getCurrentHour(){
		return getHour(System.currentTimeMillis());
	}
	
	/**
	 * 获取给定的日期中的24小时制的小时
	 * @param date 给定的日期
	 * @return 小时
	 */
	public static int getHourBy24Hour(Date date){
		return Integer.valueOf(getHourFormatBy24Hour().format(date));
	}
	
	/**
	 * 获取给定的时间的毫秒值中的小时
	 * @param timeMillis 给定的时间的毫秒值
	 * @return 小时
	 */
	public static int getHourBy24Hour(long timeMillis){
		return getHourBy24Hour(new Date(timeMillis));
	}
	
	/**
	 * 获取当前的小时
	 * @return 当前的小时
	 */
	public static int getCurrentHourBy24Hour(){
		return getHourBy24Hour(System.currentTimeMillis());
	}
	
	/* **************************************获取分********************************* */
	/**
	 * 获取给定的日期中的分钟
	 * @param date 给定的日期
	 * @return 分钟
	 */
	public static int getMinute(Date date){
		return Integer.valueOf(getMinuteFormat().format(date));
	}
	
	/**
	 * 获取给定的时间的毫秒值中的分钟
	 * @param timeMillis 给定的时间的毫秒值
	 * @return 分钟
	 */
	public static int getMinute(long timeMillis){
		return getMinute(new Date(timeMillis));
	}
	
	/**
	 * 获取当前的分钟
	 * @return 当前的分钟
	 */
	public static int getCurrentMinute(){
		return getMinute(System.currentTimeMillis());
	}
	
	/* **************************************获取秒********************************* */
	/**
	 * 获取给定的日期中的秒
	 * @param date 给定的日期
	 * @return 秒
	 */
	public static int getSecond(Date date){
		return Integer.valueOf(getSecondFormat().format(date));
	}
	
	/**
	 * 获取给定的时间的毫秒值中的秒
	 * @param timeMillis 给定的时间的毫秒值
	 * @return 秒
	 */
	public static int getSecond(long timeMillis){
		return getSecond(new Date(timeMillis));
	}
	
	/**
	 * 获取当前的秒
	 * @return 当前的秒
	 */
	public static int getCurrentSecond(){
		return getSecond(System.currentTimeMillis());
	}
	
	
	/* **************************************其它********************************* */
	/**
	 * 将字符串型的日期当中的'-'替换成相应的'年'、'月'、'日'
	 * @param strDate 字符串型的日期，例如：2010-01-01或2010-01
	 * @return 2010年01月01日或2010年01月
	 */
	public static String converDate(String strDate){
		StringBuffer sb = new StringBuffer();
		char[] chars = strDate.toCharArray();
		for(int w = 0; w < strDate.length(); w++){
			sb.append(chars[w]);
			if(w+1 == 4){
				sb.append('年');
				w++;
			}else if(w+1 == 7){
				sb.append('月');
				w++;
			}else if(w+1 == 10){
				sb.append('日');
				w++;
			}
		}
		return sb.toString();
	}
	
	/**
	 * 将字符串型的时间当中的':'替换成相应的'时'、'分'、'秒'
	 * @param time 字符串型的时间
	 * @return 
	 */
	public static String converTime(String time){
		StringBuffer sb = new StringBuffer();
		char[] chars = time.toCharArray();
		for(int w = 0; w < time.length(); w++){
			sb.append(chars[w]);
			if(w+1 == 2){
				sb.append('时');
				w++;
			}else if(w+1 == 5){
				sb.append('分');
				w++;
			}else if(w+1 == 8){
				sb.append('秒');
				w++;
			}
		}
		return sb.toString();
	}
	
	/**
	 * 获取从当前的年份开始向前的年份
	 * @param number 获取的个数
	 * @return 从当前的年份开始向前的年份
	 */
	public static String[] getBygoneYear(int number){
		String[] years = new String[number];
		int currentYear = Integer.valueOf(getCurrentYear());
		for(int w = 0; w < number; w++){
			years[w] = String.valueOf(currentYear-w);
		}
		return years;
	}
	
	/**
	 * 将给定的毫秒时间转换为有缺失的天时分秒
	 * @param milliSecondTime 给定的毫秒时间
	 * @param dayStr 天
	 * @param hourStr 时
	 * @param minuteStr 分
	 * @param secondStr 秒
	 * @param milliSecondStr 毫秒
	 * @param full 当小时不足两位时是否用0补充（分钟、秒、毫秒（3位）也一样）。例如：02:35:06:025（补充）；2:35:6:25（不补充）
	 * @param handleDay 是否处理到天。例如：2天16小时5分33秒（处理）；64小时5分33秒（不处理）
	 * @param handleMilliSecond，是否处理到毫秒。例如：02:35:06:325（处理）；02:35:06（不处理）
	 * @return 有缺失的时分秒
	 */
	public static String milliSecondToIncompleteHourMinuteSecond(long milliSecondTime, String dayStr, String hourStr, String minuteStr, String secondStr, String milliSecondStr, boolean full, boolean handleDay, boolean handleMilliSecond){
		int milliSecondSurplus = (int) (milliSecondTime%1000);
		long second = milliSecondTime/1000;
		int secondSurplus  = (int) (second%60);
		long minute = second/60;
		int minuteSurplus = (int) (minute%60);
		long hour = minute/60;
		int hourSurplus = (int) (hour%24);
		long day = hour/24;
		
		StringBuffer stringBuffer = new StringBuffer();
		
		//如果需要处理天
		if(handleDay){
			//如果天数大于0就拼接天
			if(day > 0){
				stringBuffer.append(day);
				if(dayStr != null){
					stringBuffer.append(dayStr);
				}
			}
			
			//拼接小时
			if(hourSurplus > 0){
				stringBuffer.append(full?IntegerUtils.fillZero(hourSurplus, 2):hourSurplus);
				if(hourStr != null){
					stringBuffer.append(hourStr);
				}
			}
		}else{
			//拼接小时
			if(hourSurplus > 0){
				stringBuffer.append(full?IntegerUtils.fillZero(hour, 2):hour);
				if(hourStr != null){
					stringBuffer.append(hourStr);
				}
			}
		}
		
		//拼接分钟
		if(minuteSurplus > 0){
			stringBuffer.append(full?IntegerUtils.fillZero(minuteSurplus, 2):minuteSurplus);
			if(minuteStr != null){
				stringBuffer.append(minuteStr);
			}
		}
		
		//拼接秒
		if(secondSurplus > 0){
			stringBuffer.append(full?IntegerUtils.fillZero(secondSurplus, 2):secondSurplus);
			if(secondStr != null){
				stringBuffer.append(secondStr);
			}
		}
		
		//如果需要处理毫秒
		if(handleMilliSecond){
			if(secondSurplus > 0){
				stringBuffer.append(full?IntegerUtils.fillZero(milliSecondSurplus, 3):milliSecondSurplus);
				if(milliSecondStr != null){
					stringBuffer.append(milliSecondStr);
				}
			}
		}
		
		return stringBuffer.toString();
	}
	
	/**
	 * 将给定的毫秒时间转换为完整的天时分秒
	 * @param milliSecondTime 给定的毫秒时间
	 * @param dayStr 天
	 * @param hourStr 时
	 * @param minuteStr 分
	 * @param secondStr 秒
	 * @param milliSecondStr 毫秒
	 * @param full 当小时不足两位时是否用0补充（分钟、秒、毫秒（3位）也一样）。例如：02:35:06:025（补充）；2:35:6:25（不补充）
	 * @param handleDay 是否处理到天。例如：2天16小时5分33秒（处理）；64小时5分33秒（不处理）
	 * @param handleMilliSecond，是否处理到毫秒。例如：02:35:06:325（处理）；02:35:06（不处理）
	 * @return 完整的时分秒
	 */
	public static String milliSecondToFullHourMinuteSecond(long milliSecondTime, String dayStr, String hourStr, String minuteStr, String secondStr, String milliSecondStr, boolean full, boolean handleDay, boolean handleMilliSecond){
		int milliSecondSurplus = (int) (milliSecondTime%1000);
		long second = milliSecondTime/1000;
		int secondSurplus  = (int) (second%60);
		long minute = second/60;
		int minuteSurplus = (int) (minute%60);
		long hour = minute/60;
		int hourSurplus = (int) (hour%24);
		long day = hour/24;
		
		StringBuffer stringBuffer = new StringBuffer();
		
		//如果需要处理天
		if(handleDay){
			//如果天数大于0就拼接天
			if(day > 0){
				stringBuffer.append(day);
				if(dayStr != null){
					stringBuffer.append(dayStr);
				}
			}
			
			//拼接小时
			stringBuffer.append(full?IntegerUtils.fillZero(hourSurplus, 2):hourSurplus);
			if(hourStr != null){
				stringBuffer.append(hourStr);
			}
		}else{
			//拼接小时
			stringBuffer.append(full?IntegerUtils.fillZero(hour, 2):hour);
			if(hourStr != null){
				stringBuffer.append(hourStr);
			}
		}
		
		//拼接分钟
		stringBuffer.append(full?IntegerUtils.fillZero(minuteSurplus, 2):minuteSurplus);
		if(minuteStr != null){
			stringBuffer.append(minuteStr);
		}
		
		//拼接秒
		stringBuffer.append(full?IntegerUtils.fillZero(secondSurplus, 2):secondSurplus);
		if(secondStr != null){
			stringBuffer.append(secondStr);
		}
		
		//如果需要处理毫秒
		if(handleMilliSecond){
			stringBuffer.append(full?IntegerUtils.fillZero(milliSecondSurplus, 3):milliSecondSurplus);
			if(milliSecondStr != null){
				stringBuffer.append(milliSecondStr);
			}
		}
		
		return stringBuffer.toString();
	}
	
	/**
	 * 将给定的毫秒时间转换为以':'分割的时分秒
	 * @param milliSecond 毫秒给定的毫秒时间
	 * @return 以':'分割的时分秒
	 */
	public static String milliSecondToHourMinuteSecond(long milliSecond){
		return milliSecondToFullHourMinuteSecond(milliSecond, null, ":", ":", null, null, true, false, false);
	}
	
	/**
	 * 将给定的小时按24小时制往后推移
	 * @param hour 给定的小时
	 * @param addNumber 往后推移小时数
	 * @return 例如：23点 向后推移5个小时结果是4点
	 */
	public static int hourAddBy24Hour(int hour, int addNumber){
		return hour+addNumber%24;
	}
	
	/**
	 * 将给定的小时按12小时制往后推移
	 * @param hour 给定的小时
	 * @param addNumber 往后推移小时数
	 * @return 例如：11点 向后推移5个小时结果是4点
	 */
	public static int hourAddBy12Hour(int hour, int addNumber){
		return hour+addNumber%12;
	}
	
	/**
	 * 获取当前的年、月（从1开始）、日、时（12小时制）、分
	 * @return 按顺序排放的int数组
	 */
	public static int[] getCurrentTimes(){
		Date date = new Date(System.currentTimeMillis());
		return new int[]{
			Integer.valueOf(DateTimeUtils.getYearFormat().format(date)), 
			Integer.valueOf(DateTimeUtils.getMonthFormat().format(date)), 
			Integer.valueOf(DateTimeUtils.getDayFormat().format(date)), 
			Integer.valueOf(DateTimeUtils.getHourFormat().format(date)), 
			Integer.valueOf(DateTimeUtils.getMinuteFormat().format(date))
		};
	}
	
	/**
	 * 获取当前的年、月（从1开始）、日、时（24小时制）、分
	 * @return 按顺序排放的int数组
	 */
	public static int[] getCurrentTimesBy24Hour(){
		Date date = new Date(System.currentTimeMillis());
		return new int[]{
			Integer.valueOf(DateTimeUtils.getYearFormat().format(date)), 
			Integer.valueOf(DateTimeUtils.getMonthFormat().format(date)), 
			Integer.valueOf(DateTimeUtils.getDayFormat().format(date)), 
			Integer.valueOf(DateTimeUtils.getHourFormatBy24Hour().format(date)), 
			Integer.valueOf(DateTimeUtils.getMinuteFormat().format(date))
		};
	}
}
