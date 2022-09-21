package com.moon.joyce.commons.utils;


import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.functionality.entity.doma.JoyceException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Xing Dao Rong
 * @date 2021/9/17 14:00
 * @desc 字符串工具类
 */
public class StringsUtils  extends StringUtils implements Serializable {
    private static final long serialVersionUID = 3375173776417938676L;

    private StringsUtils() throws JoyceException {
        throw JoyceExceptionUtils.exception("工具类无法实例化");
    }

    /**
     * 字符串转集合
     * @param str
     * @return
     */
    public static List<String> strToList(String str){
        String[] strs ={};
        if (str.contains(",")){
            strs = str.split(",");
        }else {
            strs = new String[]{str};
        }
        return  Arrays.asList(strs);
    }

    /**
     * 集合转字符
     * @param list
     * @return
     */
    public static String listToStr(List list){
        ListsUtils listsUtils = new ListsUtils<>();
        return listsUtils.listToStr(list);
    }

    /**
     * 集合是否含有某个字符串
     * @param str
     * @param list
     * @return
     */
    public static boolean listIsContainsStr(String str,List<String> list){
        Set<String> set = new HashSet<>(list);
        return  set.contains(str);
    }



    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        if (!str.contains("_")){
            return str;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append("_");
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
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
     * 驼峰法转下划线
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camelToUnderline(String line){
        if(line==null||"".equals(line)){
            return "";
        }
        char[] chars = line.toCharArray();
        int rs = 0;
        for (char aChar : chars) {
            if (65<=aChar && aChar<=90){
                rs = 1;
                break;
            }
        }
        if (rs==0){
            return line;
        }
        line=String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end()==line.length()?"":"_");
        }
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


    /**拼接字符
     * @param strs
     * @param g
     * @return
     */
    public static String appendStr(List<String> strs,String g){
        return appendStr("",strs,"",g);
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
    public static Date stringToDate(String dateStr, String pattern){
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
    public static Date stringToDate(String dateStr){
        SimpleDateFormat dfm =  new SimpleDateFormat(Constant.DATE_TIME_DAY);
        Date parse = null;
        try {
            parse = dfm.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  parse;
    }

    /**
     * 随机获取数字字符串
     * @param len
     * @return
     */
    public static String randNumber(int len){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int rs = (int)(Math.random()*9);
            builder.append(rs);
        }
        return builder.toString();
    }

    /**
     * 字符串集合转其他
     * @param list
     * @return
     */
    public static  List<Long> strListToOther(List<String> list){
        List<Long> longs = new ArrayList<>();
        for (String aLong : list) {
            longs.add(Long.valueOf(aLong));
        }
            return   longs ;
    }

    /**
     * 字符匹配多个
     * @param strs
     * @return
     */
    public static boolean equals(String... strs){
       if (strs.length<2){
           throw new JoyceException("长度大于等于2");
       }
        boolean flag = false;
        for (int i = 1; i < strs.length; i++) {
            if (strs[0].equals(strs[i])){
                flag = true;
                break;
            }
        }
        return flag;
    }
    /**
     * 字符匹配多个
     * @param strs
     * @return
     */
    public static boolean equalsIgnoreCase(String... strs){
        if (strs.length<2){
            throw new JoyceException("长度大于等于2");
        }
        boolean flag = false;
        for (int i = 1; i < strs.length; i++) {
            if (strs[0].equalsIgnoreCase(strs[i])){
                flag = true;
                break;
            }
        }
        return flag;
    }
}
