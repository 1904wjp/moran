package com.moon.joyce.commons.utils;


import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.functionality.entity.JoyceException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2021/09/04-- 21:26
 * @describe: 时间工具类
 */
public class DateUtils implements Serializable {
    private static final long serialVersionUID = 1345446751056258222L;
    private DateUtils() throws JoyceException {
      throw JoyceExceptionUtils.exception("工具类无法实例化");
    }
    /**
     * 时间转字符串
     * @param pattern
     * @param date
     * @return
     */
    public static String dateForMat(String pattern, Date date){
        if (Objects.isNull(pattern)){
            pattern="dv";
        }
        if (Objects.isNull(date)){
            date=new Date();
        }
        //判断是否为自定义格式
        if (pattern.contains("@p:")&&pattern.trim().length()>3){
            pattern = pattern.substring(3);
        }else {
            switch (pattern){
                //获得年
                case "y":
                    //获得年值
                case "yv":
                    pattern = Constant.DATE_TIME_YEAR;break;
                //获得月
                case "M":
                    pattern =Constant.DATE_TIME_MONTH;break;
                //获得年月值
                case "Mv":
                    pattern = Constant.DATE_TIME_MONTH.replace("-","").trim();break;
                //获得年月日
                case "d":
                    pattern = Constant.DATE_TIME_DAY;break;
                //获得年月日值
                case "dv":
                    pattern = Constant.DATE_TIME_DAY.replace("-","").trim();break;
                //获得年月日时
                case "h":
                    pattern = Constant.DATE_TIME_HOUER;break;
                //获得年月日时值
                case "hv":
                    pattern = Constant.DATE_TIME_HOUER.replace("-","").replace(" ","").replace(":","").trim();break;
                //获得年月日时分
                case "m":
                    pattern = Constant.DATE_TIME_MINUTE;break;
                //获得年月日时分值
                case "mv":
                    pattern = Constant.DATE_TIME_MINUTE.replace("-","").replace(" ","").replace(":","").replace(" ","").replace(":","").trim();break;
                //获得年月日时分秒
                case "s":
                    pattern = Constant.DATE_TIME_SECOND;break;
                //获得年月日时分秒值
                case "sv":
                    pattern = Constant.DATE_TIME_SECOND.replace("-","").replace(" ","").replace(":","").replace(" ","").replace(":","").trim();break;
                //获得年月日
                default:
                    pattern = Constant.DATE_TIME_DAY;break;
            }
        }

        SimpleDateFormat dfm =  new SimpleDateFormat(pattern);
        return  dfm.format(date);
    }

    /**
     * 时间转字符串
     * @return
     */
    @Deprecated
    public static String dateForMat(){
        SimpleDateFormat dfm =  new SimpleDateFormat(Constant.DATE_TIME_DAY);
        return  dfm.format(new Date());
    }


    /**
     * 时间的加减乘除
     * @param date
     * @param number
     * @param type
     * @return
     */
    public static long dateAsmd( Date date,Long number,String type) {
        if (StringUtils.isBlank(type)) {
            return -1;
        }
        SimpleDateFormat dfm = new SimpleDateFormat(Constant.DATE_TIME_SECOND);
        String dateValue = dfm.format(date);
        Date dateTime = null;
        try {
            dateTime = dfm.parse(dateValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (type.equals(Constant.ARITHMETIC_ADD)) {
            assert dateTime != null;
            return dateTime.getTime() + number;
        }
        if (type.equals(Constant.ARITHMETIC_SUBTRACT)) {
            assert dateTime != null;
            return dateTime.getTime() - number;
        }
        if (type.equals(Constant.ARITHMETIC_MULTIPLY)) {
            assert dateTime != null;
            return dateTime.getTime() * number;
        }
        if (type.equals(Constant.ARITHMETIC_DIVIDE)) {
            if (number == 0) {
                return -1;
            }
            assert dateTime != null;
            return dateTime.getTime() / number;
        }
        return -1;
    }

    /**
     * 时间的比较
     * @param var1
     * @param var2
     * @param number
     * @return
     */
    public static boolean dateCompare( Date var1,Date var2,Long number) {
        assert var1!=null;
        assert var2!=null;
        assert number!=null;
        SimpleDateFormat dfm = new SimpleDateFormat(Constant.DATE_TIME_SECOND);
        Long dateTime1 = null;
        Long dateTime2 = null;
        try {
            dateTime1 = dfm.parse(dfm.format(var1)).getTime();
            dateTime2 = dfm.parse(dfm.format(var2)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return   (dateTime2-dateTime1)>number;
    }

    /**
     * 时间的比较
     * @param var1
     * @param var2
     * @param number
     * @return
     */
    public static boolean dateCompare( Date var1,Date var2,Long number,String pattern) {
        SimpleDateFormat dfm = new SimpleDateFormat(pattern);
        Long dateTime1 = null;
        Long dateTime2 = null;
        try {
            dateTime1 = dfm.parse(dfm.format(var1)).getTime();
            dateTime2 = dfm.parse(dfm.format(var2)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return   (dateTime2-dateTime1)>number;
    }

    /**
     * 获取事件计算格式
     * @param date
     * @param pattern
     * @return
     */
    public static String showDate(Date date,String pattern){
        String  dateStr=dateForMat(pattern,date);
        String year = dateStr.substring(0,4);
        Long yearNum =Long.parseLong(year);
        int month = Integer.parseInt(dateStr.substring(5,7));
        int day = Integer.parseInt(dateStr.substring(6,8));
        String hour = dateStr.substring(8,10);
        String minute = dateStr.substring(10,12);
        long addtime =date.getTime();
        long today = System.currentTimeMillis();//当前时间的毫秒数
        Date now = new Date(today);
        String  nowStr=dateForMat(pattern,now);
        int  nowDay = Integer.parseInt(nowStr.substring(6,8));
        String result="";
        long l=today-addtime;//当前时间与给定时间差的毫秒数
        long days=l/(24*60*60*1000);//这个时间相差的天数整数，大于1天为前天的时间了，小于24小时则为昨天和今天的时间
        long hours=(l/(60*60*1000)-days*24);//这个时间相差的减去天数的小时数
        long min=((l/(60*1000))-days*24*60-hours*60);//
        long s=(l/1000-days*24*60*60-hours*60*60-min*60);
        if(days > 0){
            if(days>0 && days<2){
                result ="前天"+hour+"点"+minute+"分";
            } else {
                result = yearNum%100+"年"+month+"月 "+day+"日"+hour+"点"+minute+"分";
            }
        }else if(hours > 0 ) {
            if(day!=nowDay){
                result = "昨天"+hour+"点"+minute+"分";
            }else{
                result=hours+"小时 前";
            }
        } else if(min > 0){
            if(min>0 && min<15){
                result="刚刚";
            } else {
                result=min+"分 前";
            }
        }else {
            result=s+"秒 前";
        }
        return result;
    }

}
