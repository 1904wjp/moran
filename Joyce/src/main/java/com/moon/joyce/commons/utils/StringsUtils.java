package com.moon.joyce.commons.utils;


import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.functionality.entity.JoyceException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Xing Dao Rong
 * @date 2021/9/17 14:00
 * @desc 字符串工具类
 */
public class StringsUtils  implements Serializable {
    private static final long serialVersionUID = 3375173776417938676L;

    private StringsUtils() throws JoyceException {
        throw JoyceExceptionUtils.exception("工具类无法实例化");
    }
    //字符串转集合
    public static List<String> StrToList(String str){
        String[] strs ={};
        if (str.contains(",")){
            strs = str.split(",");
        }else {
            strs = new String[]{str};
        }
        return  Arrays.asList(strs);
    }
    //集合是否含有某个字符串
    public static boolean listIsContainsStr(String str,List<String> list){
        for (String string : list) {
            if(StringUtils.isBlank(string)) {
                continue;
            }
            if (string.equals(str)) {
                return true;
            }
        }
        return  false;
    }


    //两个集合的和集or差集
    public static List<String> differOrIntersList(List<String> list1,List<String> list2,boolean flag){
        List<String> l1 = list1.size() >= list2.size() ? list1:list2;
        List<String> l2 = l1==list1 ? list2:list1;
        if (flag){
           return l1.stream().filter(itme -> !l2.contains(itme)).collect(Collectors.toList());
        }
          return  l1.stream().filter(l2::contains).collect(Collectors.toList());
    }


    /**
     * 下划线转转驼峰
     * @param str
     * @return
     */
    public static String getUp(String str){
        if (!str.contains("_")){
            return str;
        }
        Pattern linePattern = Pattern.compile("_(\\w)");
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 修改方法
     * @param strs
     * @param tableName
     */
    private static void updateMethod(String[] strs,String tableName){
        String temp =" UPDATE "+tableName+"\n" +
                "        <trim prefix=\"SET \"  suffixOverrides=\",\">\n";
        for (String s : strs) {
            if (StringUtils.isBlank(s))continue;
            temp = temp+" <if test=\""+getUp(s)+"!=null and "+getUp(s)+"!=''\"> "+s+"=#{"+getUp(s)+"},</if>\n";
        }
        temp = temp+"</trim>";
        System.out.println(temp);
    }

    /**
     * 增添方法
     * @param strs
     * @param tableName
     */
    private static void insertMethod(String[] strs,String tableName){
        String temp =" INSERT "+tableName+"\n" +
                "         <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n";
        for (String s : strs) {
            if (StringUtils.isBlank(s))continue;
            temp = temp+" <if test=\""+getUp(s)+"!=null and "+getUp(s)+"!=''\"> "+s+",</if>\n";
        }
        temp = temp+"</trim>\n<trim prefix=\"VALUE(\" suffix=\")\" suffixOverrides=\",\">\n";
        for (String s : strs) {
            if (StringUtils.isBlank(s))continue;
            temp = temp+" <if test=\""+getUp(s)+"!=null and "+getUp(s)+"!=''\">#{"+getUp(s)+"},</if>\n";
        }
        temp = temp+"</trim>";
        System.out.println(temp);
    }

    /**
     * sql改成java类型
     * @param str
     * @return
     */
    public static String changeType(String str){
        Matcher mat = Pattern.compile("(?<=\\()(\\S+)(?=\\))").matcher(str);//此处是中文输入的()
        while (mat.find()) {
            str =  str.replace("("+mat.group()+")","");
        }
        return str;
    }

    /**
     * 获取实体类
     * @param str
     * @return
     */
    public static String getClass(String str ){
        str = getUp(str);
        String[] var1 = {"bigint","varchar","datetime","char","int","double"};
        String[] var2 = {"Long","String","Date","String","Integer","Double"};
        for (int i = 0; i < var1.length; i++) {
            str = str.replace(var1[i],var2[i]);
        }
        str= changeType(str).replace("/;","/\n");
        return str;
    }

    /**
     * 获取名字
     * @param tableName
     * @return
     */
    public static  String getClassName(String tableName){
        return StringsUtils.getUp(tableName).substring(0,1).toUpperCase()+ StringsUtils.getUp(tableName).substring(1);
    }

    /**
     * 转换类型
     * @param type
     * @return
     */
    public static String changeType2(String type){
        String[] var1 = {"bigint","varchar","datetime","char","int","text","decimal","date","tinyint"};
        String[] var2 = {"Long","String","Date","String","Integer","String","BigDecimal","Date","Integer"};
        for (int i = 0; i < var1.length; i++) {
            if (type.equals(var1[i])){
                return var2[i];
            }
        }
        return "String";
    }

    /**
     * 拼接字符
     * @param prefix
     * @param strs
     * @param suffix
     * @param suffix
     * @param g
     * @return
     */
    public static String appendStr(String prefix,List<String> strs ,String suffix,String g){
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder prefixBuilder = stringBuilder.append(prefix);
        if (strs.isEmpty()){
            return prefixBuilder.append(suffix).toString();
        }
        for (int i = 0; i < strs.size(); i++) {
            if (StringUtils.isNoneEmpty(strs.get(i))){
                prefixBuilder.append(strs.get(i));
            }
            if (StringUtils.isNoneEmpty(g)&&i!=strs.size()-1){
                prefixBuilder.append(g);
            }
        }
        return prefixBuilder.append(suffix).toString();
    }

    /**
     * 截取文件名
     * @param fileName
     * @return
     */
    public static String substringFileName(String fileName){
        if (fileName.length()>6){
            fileName = fileName.substring(fileName.length()-6);
        }
        return fileName;
    }
    /**
     * 字符串转时间
     * @return
     */
    public static Date StringToDate(String dateStr,String pattern){
        SimpleDateFormat dfm =  new SimpleDateFormat(pattern);
        Date parse = null;
        try {
            parse = dfm.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  parse;
    }
    /**
     * 字符串转时间
     * @return
     */
    public static Date StringToDate(String dateStr){
        SimpleDateFormat dfm =  new SimpleDateFormat(Constant.DATE_TIME_DAY);
        Date parse = null;
        try {
            parse = dfm.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  parse;
    }


}
