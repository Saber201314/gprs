package com.shlr.gprs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 * 
 */

public class TimeUtls {
	
	private TimeUtls(){
		throw new RuntimeException(" TimeUtls cannot be instantiated");
	}
	/**
	 *                          HH:mm    15:44
     *                         h:mm a    3:44 下午
     *                        HH:mm z    15:44 CST
     *                        HH:mm Z    15:44 +0800
     *                     HH:mm zzzz    15:44 中国标准时间
     *                       HH:mm:ss    15:44:40
     *                     yyyy-MM-dd    2016-08-12
     *               yyyy-MM-dd HH:mm    2016-08-12 15:44
     *            yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     *       yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
     *  EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
     *       yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     *   yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     *                         K:mm a    3:44 下午
     *               EEE, MMM d, ''yy    星期五, 八月 12, '16
     *          hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
     *   yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     *     EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     *                  yyMMddHHmmssZ    160812154440+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
     * </pre>
     * 注意：SimpleDateFormat不是线程安全的，线程安全需用{@code ThreadLocal<SimpleDateFormat>}
     */
	
	
	/**
	 *  yyyy-MM-dd HH:mm:ss === 2016-08-12 15:44:40
	 */
	public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * Date按Pattern转时间字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String date2StrByPattern(Date date,String pattern){
		return new SimpleDateFormat(pattern).format(date);
	}
	/**
	 * Date按默认Pattern转时间字符串
	 * @param date
	 * @return
	 */
	public static String date2StrByDefault(Date date){
		return new SimpleDateFormat(DEFAULT_PATTERN).format(date);
	}
	
	
	public static Date timeStr2Date(String timeStr,String pattern){
		try {
			return new SimpleDateFormat(pattern).parse(timeStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public static Date timeStr2DateByDefault(String timeStr){
		try {
			return new SimpleDateFormat(DEFAULT_PATTERN).parse(timeStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
