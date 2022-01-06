package com.moon.joyce.commons.utils;


import com.baomidou.mybatisplus.generator.config.IFileCreate;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.functionality.entity.JoyceException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            if(org.apache.commons.lang3.StringUtils.isBlank(string)) {
                continue;
            }
            if (string.equals(str)) {
                return true;
            }
        }
        return  false;
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
        String[] var1 = {"bigint","varchar","datetime","char","int"};
        String[] var2 = {"Long","String","Date","String","Integer"};
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
        String[] var1 = {"bigint","varchar","datetime","char","int","text","decimal"};
        String[] var2 = {"Long","String","Date","String","Integer","String","BigDecimal"};
        for (int i = 0; i < var1.length; i++) {
            if (type.equals(var1[i])){
                return var2[i];
            }
        }
        return null;
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
        int count =0;
        for (int i = 0; i < strs.size(); i++) {
            ++count;
            if (StringUtils.isNoneEmpty(strs.get(i))){
                prefixBuilder.append(strs.get(i));
            }
            if (StringUtils.isNoneEmpty(g)&&count!=strs.size()){
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
