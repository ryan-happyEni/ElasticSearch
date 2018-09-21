package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class DateUtil {
	
	/**
	 * 날짜를 원하는 형식의 문자로 변환
	 * @param date : 날짜
	 * @param formatStr : 형식
	 * @return 형식이 적용된 날짜의 문자열
	 */
	public static String getDateStr(Date date, String formatStr){
		String strDate = "";
		try{
			SimpleDateFormat  format = new SimpleDateFormat(formatStr);
			strDate = format.format(date);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}
	
	/**
	 * 날짜를 원하는 형식의 문자로 변환
	 * @param date : 날짜 문자열
	 * @param sourceFormat : 원래 형식
	 * @param targetFormat : 변경하고자 하는 형식
	 * @return 변경하고자 하는 형식이 적용된 날짜 문자열
	 */
	public static String getDate(String date, String sourceFormat, String targetFormat) {
		String strDate = date;
		try{
			SimpleDateFormat  format = new SimpleDateFormat(sourceFormat);
			Date newDate = format.parse(date);

			SimpleDateFormat  newFormat = new SimpleDateFormat(targetFormat);
			strDate = newFormat.format(newDate);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}
	
	/**
	 * 날짜를 원하는 형식의 문자로 변환
	 * @param date : 날짜 문자열
	 * @param sourceFormat : 원래 형식
	 * @param targetFormat : 변경하고자 하는 형식
	 * @param locale : 지역(ko, en)
	 * @return 변경하고자 하는 형식이 적용된 날짜 문자열
	 */
	public static String getDate(String date, String sourceFormat, String targetFormat, String locale) {
		String strDate = date;
		try{
			if(locale.equals("ko")||locale.equals("kor")){
				SimpleDateFormat  format = new SimpleDateFormat(sourceFormat, Locale.KOREAN);
				Date newDate = format.parse(date);

				SimpleDateFormat  newFormat = new SimpleDateFormat(targetFormat, Locale.KOREAN);
				strDate = newFormat.format(newDate);
			}else{
				SimpleDateFormat  format = new SimpleDateFormat(sourceFormat, Locale.ENGLISH);
				Date newDate = format.parse(date);

				SimpleDateFormat  newFormat = new SimpleDateFormat(targetFormat, Locale.ENGLISH);
				strDate = newFormat.format(newDate);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}
	
	/**
	 * 날짜를 원하는 형식의 문자로 변환
	 * @param date : 날짜 문자열
	 * @param sourceFormat : 원래 형식
	 * @param targetFormat : 변경하고자 하는 형식
	 * @param locale : 지역 
	 * @return 변경하고자 하는 형식이 적용된 날짜 문자열
	 */
	public static String getDate(String date, String sourceFormat, String targetFormat, Locale locale) {
		String strDate = date;
		try{
			SimpleDateFormat  format = new SimpleDateFormat(sourceFormat, locale);
			format.setTimeZone(new SimpleTimeZone(9, "GMT"));
			Date newDate = format.parse(date); 

			SimpleDateFormat  newFormat = new SimpleDateFormat(targetFormat);
			strDate = newFormat.format(newDate);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}

	/**
	 * long 형식의 밀리시간을 날짜 형태로 변환
	 * @param time : 밀리시간 
	 * @param pattern : 변경 날짜 형태
	 * @return
	 */
	public static String getDate(long time, String pattern) {
		SimpleDateFormat dataFormat = new SimpleDateFormat (pattern);
		Date date = new Date();
		date.setTime (time);
		return dataFormat.format(date);
	}

	/**
	 * 현재시간을 원하는 형태로 변환
	 * @param pattern : 날짜 형식
	 * @return 현재 날짜 시간을 형식에 맞게 반환
	 */
	public static String getCurrentTime(String pattern) {
		SimpleDateFormat dataFormat = new SimpleDateFormat (pattern);
		Date date = new Date();
		date.setTime ((long) (System.currentTimeMillis ()));
		return dataFormat.format(date);
	}

	/**
	 * 현재시간 대비 10분전
	 * @param pattern
	 * @return
	 */
	public static String getCurrentTime1(String pattern) {
		SimpleDateFormat dataFormat = new SimpleDateFormat (pattern);
		Date date = new Date();

		long yesterDayTime = System.currentTimeMillis () - ( 10 * 60 * 1000);
		date.setTime (yesterDayTime);
		return dataFormat.format(date);
	}

	/**
	 * 현재시간 대비 24시간전
	 * @param pattern
	 * @return
	 */
	public static String getYesterdayTime(String pattern) {
		SimpleDateFormat dataFormat = new SimpleDateFormat (pattern);
		Date date = new Date();

		long yesterDayTime = System.currentTimeMillis () - ( 24 * 60 * 60 * 1000);
		date.setTime (yesterDayTime);
		return dataFormat.format(date);
	}
	
	/**
	 * 현재시간대비 특정기간 이후, 이전 날짜
	 * @param pattern : 날짜 형식
	 * @param timePeriod : 기간(정수형)
	 * @return 기간을 계산한 날짜
	 */
	public static String getDayPlusTime(String pattern,int timePeriod) {
		String day = DateUtil.getCurrentTime(pattern);
		int plus = Integer.valueOf(day.substring(10, day.length()));
		int period = timePeriod/60;
		int month = (plus/period);
		int i = period*month + period;
		
		SimpleDateFormat dataFormat = new SimpleDateFormat ("yyyyMMddHHmm");
		Date date = new Date();
		
		int k = i-plus;
		
		long plusDayTime = System.currentTimeMillis () + ( k * 60 * 1000);
		date.setTime (plusDayTime);
	
		return dataFormat.format(date);
	}
	
	/**
	 * 일수 더하기
	 * @param dateStr : yyyyMMdd(형태의 날짜)
	 * @param amount : 기간
	 * @param formatStr : 반환 날짜 형식
	 * @return
	 */
	public static String addDay(String dateStr, int amount, String formatStr) {
		 Calendar cal = Calendar.getInstance();
		 cal.set(Calendar.YEAR, Integer.parseInt(dateStr.substring(0, 4)));
		 cal.set(Calendar.MONTH, Integer.parseInt(dateStr.substring(4, 6))-1);
		 cal.set(Calendar.DATE, Integer.parseInt(dateStr.substring(6, 8)));
		 cal.add(Calendar.DATE, amount);
	
		return getDateStr(cal.getTime(), formatStr);
	}
	
	/**
	 * 월수 더하기
	 * @param dateStr : yyyyMMdd(형태의 날짜)
	 * @param amount : 기간
	 * @param formatStr : 반환 날짜 형식
	 * @return
	 */
	public static String addMonth(String dateStr, int amount, String formatStr) {
		 Calendar cal = Calendar.getInstance();
		 cal.set(Calendar.YEAR, Integer.parseInt(dateStr.substring(0, 4)));
		 cal.set(Calendar.MONTH, Integer.parseInt(dateStr.substring(4, 6))-1);
		 cal.set(Calendar.DATE, Integer.parseInt(dateStr.substring(6, 8)));
		 cal.add(Calendar.MONTH, amount);
	
		return getDateStr(cal.getTime(), formatStr);
	}
	
	/**
	 * 날짜 문자열을 밀리시간으로 변환
	 * @param time : 날짜
	 * @param pattern : 날짜의 형식
	 * @return
	 */
	public static long getTimeMillis(String time, String pattern) {
		long retTime = 0;
		try{
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date date = format.parse(time);
			retTime = date.getTime();
		}catch (Exception e) {
			
		}

		return retTime;
	}

	/**
	 * 시간 구하기
	 * @param time : 밀리시간
	 * @param pattern : 날짜 형식
	 * @return
	 */
	public static String getTimeMillis(long time, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = new Date();
		date.setTime(time);
		return format.format(date);
	}
	
	/**
	 * 시간 더하기
	 * @param time : 날짜
	 * @param pattern : 날짜의 형식
	 * @param timePeriod : 더할 시간
	 * @return
	 */
	public static String getPlusTimeMillis(String time, String pattern, int timePeriod) {
		try{
			String day = time;
			int plus = Integer.valueOf(day.substring(10, day.length()));
			int period = timePeriod/60;
			int month = (plus/period);
			int i = period*month + period;
			
			SimpleDateFormat dataFormat = new SimpleDateFormat ("yyyyMMddHHmm");
			Date date = new Date();
			
			int k = i-plus;
			
			long plusDayTime = System.currentTimeMillis () + ( k * 60 * 1000);
			date.setTime (plusDayTime);
		
			return dataFormat.format(date);
		}catch (Exception e) {
			System.out.println(e);
		}

		return "";
	}
	
	public static void processTime(long start, long end, String prefix) {
		String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
		SimpleDateFormat dataFormat = new SimpleDateFormat(pattern);
		Date date = new Date();

		date.setTime(start);
		String startTime = dataFormat.format(date);

		date.setTime(end);
		String endTime = dataFormat.format(date);

		System.out.println(prefix + "  start time = " + startTime);
		System.out.println(prefix + "    end time = " + endTime);
		System.out.println(prefix + "process time = " + (end - start));
	}
	
	/**
	 * 날짜 비교
	 * @param start_date : 시작일(yyyyMMdd)
	 * @param end_date : 종료일(yyyyMMdd)
	 * @return
	 */
	public static long getDateDiff(String start_date, String end_date){
		Calendar date1 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();
		
		try{
			SimpleDateFormat  format = new SimpleDateFormat("yyyyMMdd");
			Date newDate = format.parse(start_date);
			Date newDate2 = format.parse(end_date);
			date1.setTime(newDate);
			date2.setTime(newDate2);
		}catch(Exception e){
			e.printStackTrace();
		}
    
		long Bring = (date2.getTimeInMillis()-date1.getTimeInMillis());
		long days = (Bring / (60*60*24*1000));

		return days;
	}
	
	/**
	 * 기간에 해당하는 날짜 목록 구하기
	 * @param start_date : 시작일(yyyyMMdd)
	 * @param end_date : 종료일(yyyyMMdd)
	 * @return
	 */
	public static List<String> getDateList(String start_date, String end_date, String formatStr){
		List<String> dateList = new ArrayList<String>();
		long dateDiff = getDateDiff(start_date, end_date);
  
		if(dateDiff>0){ 
			for(int i=0; i<=dateDiff; i++){
				dateList.add(addDay(start_date, i, formatStr));
			}
		}else{
			for(int i=(int)dateDiff; i<=0; i++){
				dateList.add(addDay(start_date, i, formatStr));
			}
		}
		return dateList;
	}

    /**
     * 현재의 월에 주어진 Period 만큼 더한 월을 반환한다.
     * 만일 현재월이 4월이고, getAddedMonth("M", 3)을 호출한다면 7을 반환한다.
     *
     * 반대로 더 적은 월을 알기 원한다면 Period에 음수값을 넘겨주면된다.
     * getAddedMonth("M", -3)을 호출한다면 1를 반환한다.
     */
    public String getAddMonths(int period, String format) throws Exception {
        return getAddedDate("M", period, format);
    }

    /**
     * 날짜 더하기
     * @param period : 기간
     * @param format : 날짜 형식
     * @return
     * @throws Exception
     */
    public String getAddDates(int period, String format) throws Exception {
        return getAddedDate("D", period, format);
    }

    /**
     * 현재의 날짜에 주어진 Period 만큼 더한 날을 반환한다.
     * 만일 현재 날짜가 20일이고, getAddedDate("D", 3)을 호출한다면 23을 반환한다.
     *
     * 반대로 더 적은 날을 알기 원한다면 Period에 음수값을 넘겨주면된다.
     * getAddedDate("D", -3)을 호출한다면 17을 반환한다.
     */
    public String getAddedDate(String unit, int period, String format) throws Exception {
        Calendar calendar = java.util.Calendar.getInstance();

        if (unit.equals("M")) {
            calendar.add(java.util.Calendar.MONTH, period);
        } else if (unit.equals("D")) {
            calendar.add(java.util.Calendar.DAY_OF_MONTH, period);
        } else {
            throw new Exception("Parameter value is not valuable.\n[Possible value : M or D]");
        }

        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);

        String strMonth = month < 10 ? "0" + month : "" + month;
        String strDay = day < 10 ? "0" + day : "" + day; 
 
        return getDate(calendar.get(Calendar.YEAR) +""+ strMonth +""+ strDay, format, format);
    }
	
    /**
     * 시간 경과 체크
     * @param time : 시간
     * @param timePeriod : 경과시간(초단위)
     * @param formatStr : 날짜형식
     * @return 경과되면 true , 미경과시 false
     */
	public static boolean isPast(String time, int timePeriod, String formatStr) {
		boolean mode =false;
		try{ 
			SimpleDateFormat  format = new SimpleDateFormat(formatStr);
			Date date = format.parse(time);
			long currentTime = System.currentTimeMillis ();

			/*
			System.out.println("flag time : "+date.getTime());
			System.out.println("current time : "+currentTime);
			System.out.println("current time : "+((currentTime-date.getTime())/1000));
			*/
			
			if(((currentTime-date.getTime())/1000)>timePeriod){
				mode = true;
			}
		}catch (Exception e) {
			System.out.println(e);
		}

		return mode;
	}

	/**
	 * 윤년 체크
	 * @param i
	 * @return
	 */
    public static boolean isLeapYear(int i){
        if(i % 4 != 0)
            return false;
        if(i % 400 == 0)
            return true;
        else
            return i % 100 != 0;
    }

    /**
     * 월의 마지막 일자 얻기
     * @param year : 연도
     * @param month : 월
     * @return
     */
    public static int lastDate(int year, int month){
        int ai[] = {
            0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 
            31, 30, 31
        };
        if(month > 12 || month < 0)
        	month = 0;
        if(month == 2 && isLeapYear(year))
            return ai[month] + 1;
        else
            return ai[month];
    }
}
